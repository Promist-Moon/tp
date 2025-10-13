package seedu.address.model.payment;

import java.time.YearMonth;

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

}
