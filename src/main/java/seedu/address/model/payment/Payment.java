package seedu.address.model.payment;

import java.time.YearMonth;

import seedu.address.model.person.student.Student;

/**
 * Represents a Payment a Student makes in a month/year.
 */
public class Payment {
    private final Student student;
    private YearMonth yearMonth;
    private int numberOfHours;
    private int hourlyRate;
    private boolean isPaid;


    /**
     * Constructs a new {@code Payment} object for a specified student.
     * This constructor initializes the payment details including
     * the associated student, total number of hours worked, and the hourly rate.
     *
     * @param student        the student receiving the payment
     * @param numberOfHours  the total number of hours worked
     * @param hourlyRate     the rate of payment per hour
     */
    public Payment(Student student, int numberOfHours, int hourlyRate) {
        this.student = student;
        this.numberOfHours = numberOfHours;
        this.hourlyRate = hourlyRate;
        this.isPaid = false;
    }

    public YearMonth getYearMonth() {
        return this.yearMonth;
    }

    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Calculates fees the student owes the tutor for the month
     * @return product of numberOfHours and hourlyRate
     */
    public int calculateMonthlyRate() {
        return this.numberOfHours * this.hourlyRate;
    }

}
