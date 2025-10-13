package seedu.address.model.payment;

import java.time.YearMonth;

import seedu.address.model.person.student.Student;

/**
 * Represents a Payment a Student makes in a month/year.
 */
public class Payment {
    private final Student student;
    private YearMonth yearMonth;
    private float amountPerStudent;
    private boolean isPaid;

    /**
     * Constructs a new {@code Payment} object for a specified student.
     * This constructor initializes the payment details including
     * the associated student, total number of hours worked, and the hourly rate.
     *
     * @param student        the student receiving the payment
     * @param amountPerStudent  the total amount due per month by a student
     */
    public Payment(Student student, YearMonth yearMonth, float amountPerStudent) {
        this.student = student;
        this.amountPerStudent = amountPerStudent;
        this.yearMonth = yearMonth;
        this.isPaid = false;
    }

    public YearMonth getYearMonth() {
        return this.yearMonth;
    }

    public float getAmountDue() {
        return this.amountPerStudent;
    }

    public boolean isPaid() {
        return isPaid;
    }

}
