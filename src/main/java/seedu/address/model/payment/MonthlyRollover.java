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

        long monthsElapsed = DateTimeUtil.monthsBetweenInclusive(lastOpened, now);
        if (monthsElapsed <= 0) {
            return; // No rollover needed
        }

        for (int i = 0; i < monthsElapsed; i++) {
            YearMonth rolloverMonth = lastOpened.plusMonths(i + 1);
            rolloverForMonth(rolloverMonth);
        }
    }

    /**
     * Applies rollover logic for a specific month.
     *
     * @param month the {@code YearMonth} to perform rollover for
     */
    private void rolloverForMonth(YearMonth month) {
        for (Person person : model.getFilteredPersonList()) {
            if (!(person instanceof Student)) {
                continue;
            }

            Student student = (Student) person;

            // Calculate the total amount earned for this month from all lessons
            float value = student.getLessonList().getTotalAmountEarned(month);
            TotalAmount totalAmount = new TotalAmount(value);

            // Create a new Payment for this month
            Payment newPayment = new Payment(month, totalAmount);

            // Add or replace the payment in the student's PaymentList
            student.getPayments().addPayment(newPayment);

            // TODO: check implementation of this
            // Update the model to reflect the change
            model.setPerson(student, student);
        }
    }
}
