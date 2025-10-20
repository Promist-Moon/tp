package seedu.address.model.payment;

import java.time.YearMonth;
import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;
import seedu.address.model.util.DateTimeUtil;

/**
 * Handles the monthly payment rollover logic when the app is opened
 * after one or more months have passed since the last session
 */
public class MonthlyRollover {
    private final Model model;

    /**
     * Creates a new MonthlyRollover instance and associates it with the given Model
     * to access and update data for rollover.
     *
     * @param model the {@code Model} used for accessing and modifying address book data during rollover
     */
    public MonthlyRollover(Model model) {
        Objects.requireNonNull(model);
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
        Objects.requireNonNull(lastOpened, "lastOpened cannot be null");
        Objects.requireNonNull(now, "now cannot be null");

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
            float value = student.getLessonList().getTotalAmountEarned(yearMonth);
            TotalAmount totalAmount = new TotalAmount(value);
            Payment newPayment = new Payment(yearMonth, totalAmount);

            // Copy payments and add the new one
            PaymentList oldPayments = student.getPayments();
            PaymentList copiedPayments = new PaymentList(new java.util.ArrayList<>(oldPayments.getPayments()));
            copiedPayments.addPayment(newPayment);

            // Replace student with a new instance so the model list actually updates
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
