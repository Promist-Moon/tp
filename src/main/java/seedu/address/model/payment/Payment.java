package seedu.address.model.payment;

import java.time.YearMonth;

import seedu.address.model.person.student.Student;

public class Payment {
    private final Student student;
    private YearMonth yearMonth;
    private int numberOfHours;
    private int hourlyRate;

    public Payment(Student student, int numberOfHours, int hourlyRate) {
        this.student = student;
        this.numberOfHours = numberOfHours;
        this.hourlyRate = hourlyRate;
    }

    public int calculateMonthlyRate() {
        return this.numberOfHours * this.hourlyRate;
    }

}
