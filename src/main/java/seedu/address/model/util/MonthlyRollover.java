package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.payment.Payment;
import seedu.address.model.payment.PaymentList;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.payment.exceptions.PaymentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;

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
        for (Person person : model.getAddressBook().getPersonList()) {
            if (!(person instanceof Student)) {
                continue;
            }

            Student student = (Student) person;

            // Compute amount earned for this YearMonth
            TotalAmount amount = student.getTotalAmountByMonth(yearMonth);

            // Skip zero values
            if (amount.getAsFloat() <= 0f) {
                continue;
            }

            PaymentList oldPayments = student.getPayments();

            // Duplicate Handling; returns true if month is present (handled or skipped)
            if (upsert(student, yearMonth, amount)) {
                continue;
            }

            // Happy path: add if absent (idempotent add)
            Payment newPayment = new Payment(yearMonth, amount);
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

    /**
     * Handles the "upsert" policy for an existing month:
     * - If a payment exists for {@code yearMonth} and it is UNPAID and the recomputed amount differs,
     *   overwrite that month's payment with the new computed {@code amount}.
     * - If it exists but is PAID or the amount is unchanged, do nothing.
     * Returns {@code true} if the month existed (handled or skipped), {@code false} if absent.
     */
    private boolean upsert(Student student, YearMonth yearMonth, TotalAmount amount) {
        PaymentList oldPayments = student.getPayments();

        // If month not present, signal caller to proceed with "add if absent"
        if (!oldPayments.containsMonth(yearMonth)) {
            return false;
        }

        try {
            Payment existing = oldPayments.getPaymentByMonth(yearMonth);
            TotalAmount existingAmt = existing.getTotalAmount();
            float newAmt = amount.getAsFloat();
            boolean amountDiffers = Float.compare(existingAmt.getAsFloat(), newAmt) != 0;

            if (amountDiffers && !existing.isPaid()) {
                // Overwrite (idempotent upsert for unpaid months whose computed amount changed)
                PaymentList copied = oldPayments.copy();
                copied.putPaymentForMonth(new Payment(yearMonth, amount));

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
            // Lookup failed despite containsMonth(): log and treat as handled for this student-month
            logger.warning("Rollover lookup failed for " + student.getName() + " " + yearMonth
                    + ": " + e.getMessage());
        }

        return true;
    }
}
