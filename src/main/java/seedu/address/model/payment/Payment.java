package seedu.address.model.payment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.YearMonth;
import java.util.Objects;

import seedu.address.model.person.student.Student;


/**
 * Represents a Payment a Student makes in a month/year.
 */
public class Payment {
    private final Student student;
    private YearMonth yearMonth;
    private TotalAmount totalAmount;
    private boolean isPaid;

    /**
     * Constructs a new {@code Payment} object for a specified student.
     * This constructor initializes the payment details including
     * the associated student, total number of hours worked, and the hourly rate.
     *
     * @param student        the student receiving the payment
     * @param yearMonth      the year and month corresponding to payment
     * @param totalAmount  the total amount due per month by a student
     */
    public Payment(Student student, YearMonth yearMonth, TotalAmount totalAmount) {
        requireAllNonNull(student, yearMonth, totalAmount);
        this.student = student;
        this.totalAmount = totalAmount;
        this.yearMonth = yearMonth;
        this.isPaid = false;
    }

    public Student getStudent() {
        return this.student;
    }

    public YearMonth getYearMonth() {
        return this.yearMonth;
    }

    public TotalAmount getTotalAmount() {
        return this.totalAmount;
    }

    public float getTotalAmountFloat() {
        return this.totalAmount.getAsFloat();
    }

    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Marks current payment as paid
     * If the payment is already marked as paid, this method has no effect.
     */
    public void markPaid() {
        isPaid = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Payment)) {
            return false;
        }

        Payment otherPayment = (Payment) other;
        return student.equals(otherPayment.student)
                && yearMonth.equals(otherPayment.yearMonth)
                && totalAmount.equals(otherPayment.totalAmount); // exclusion of isPaid is intentional
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, yearMonth, totalAmount); // exclusion of isPaid is intentional
    }
}
