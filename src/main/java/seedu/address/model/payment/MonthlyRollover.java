package seedu.address.model.payment;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.payment.exceptions.PaymentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;
import seedu.address.model.util.DateTimeUtil;

/**
 * Handles the monthly payment rollover logic when the app is opened
 * after one or more months have passed since the last session
 */
public class MonthlyRollover {
    private static final Logger logger = LogsCenter.getLogger(MonthlyRollover.class);
    private final Model model;

    /**
     * Creates a new MonthlyRollover instance and associates it with the given Model
     * to access and update data for rollover.
     *
     * @param model the {@code Model} used for accessing and modifying address book data during rollover
     */
    public MonthlyRollover(Model model) {
        requireNonNull(model);
        this.model = model;
    }

    /**
     * Checks how many months have elapsed since the app was last opened,
     * and performs a payment rollover for each elapsed month.
     *
     * @param lastOpened the {@code YearMonth} when the app was last opened
     * @param now the current {@code YearMonth}
     */
    public void compute(YearMonth lastOpened, YearMonth now) {
        requireNonNull(now, "now cannot be null");
        requireNonNull(lastOpened, "lastOpened cannot be null");

        // Defensive programming against corrupted pref or clock rollback
        if (now.isBefore(lastOpened)) {
            throw new IllegalArgumentException("System clock moved backwards: " + lastOpened + " → " + now);
        }

        long monthsElapsed = DateTimeUtil.monthsBetweenInclusive(lastOpened, now);
        if (monthsElapsed <= 0) {
            return; // No rollover needed
        }

        for (int i = 0; i < monthsElapsed; i++) {
            YearMonth yearMonth = lastOpened.plusMonths(i + 1);
            rolloverForMonth(yearMonth);
        }
    }

    /**
     * Applies rollover logic for a specific month.
     *
     * @param yearMonth the {@code YearMonth} to perform rollover for
     */
    private void rolloverForMonth(YearMonth yearMonth) {
        // Iterate over the full backing list to avoid filters hiding persons
        for (Person person : model.getAddressBook().getPersonList()) {
            if (!(person instanceof Student)) {
                continue;
            }

            Student student = (Student) person;

            // Compute amount earned for this YearMonth; skip zero-value months
            float value = student.getLessonList().getTotalAmountEarned(yearMonth);
            if (value <= 0f) {
                continue;
            }
            TotalAmount totalAmount = new TotalAmount(value);

            PaymentList oldPayments = student.getPayments();

            // === Duplicate handling (idempotent by YearMonth) ===
            if (oldPayments.containsMonth(yearMonth)) {
                // Upsert policy: if the existing payment is UNPAID and the amount has changed,
                // replace it to keep data consistent with lesson history.
                try {
                    Payment existing = oldPayments.getPaymentByMonth(yearMonth);
                    float existingAmt = existing.getTotalAmount().getAsFloat();
                    float newAmt = totalAmount.getAsFloat();
                    boolean amountDiffers = Float.compare(existingAmt, newAmt) != 0;

                    if (amountDiffers && !existing.isPaid()) {
                        // upsert: overwrite the month with recomputed amount (only if not yet paid)
                        PaymentList copied = oldPayments.copy();
                        copied.putPaymentForMonth(new Payment(yearMonth, totalAmount));

                        Student editedStudent = new Student(
                                student.getName(),
                                student.getPhone(),
                                student.getEmail(),
                                student.getAddress(),
                                student.getTags(),
                                student.getLessonList(),
                                copied
                        );
                        model.setPerson(student, editedStudent);
                    }
                } catch (PaymentException e) {
                    // Lookup failed despite containsMonth(): log and skip this student-month
                    logger.warning("Rollover lookup failed for " + student.getName() + " " + yearMonth
                            + ": " + e.getMessage());
                    continue;
                }
                continue; // Either handled via upsert or intentionally skipped
            }

            // === Normal path: add if absent (idempotent add) ===
            Payment newPayment = new Payment(yearMonth, totalAmount);
            PaymentList copiedPayments = oldPayments.copy();
            boolean added = copiedPayments.addPaymentIfAbsent(newPayment);
            if (!added) {
                continue;
            }

            Student editedStudent = new Student(
                    student.getName(),
                    student.getPhone(),
                    student.getEmail(),
                    student.getAddress(),
                    student.getTags(),
                    student.getLessonList(),
                    copiedPayments
            );

            model.setPerson(student, editedStudent);
        }
    }
}
