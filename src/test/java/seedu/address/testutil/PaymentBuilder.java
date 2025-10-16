package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.YearMonth;

import seedu.address.model.payment.Payment;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.person.student.Student;

/**
 * A utility class to help with building Payment objects.
 */
public class PaymentBuilder {

    public static final Student DEFAULT_STUDENT = ALICE;
    public static final String DEFAULT_YEARMONTH = "2025-10";
    public static final float DEFAULT_AMOUNT = 600f;
    public static final boolean DEFAULT_ISPAID = false;

    private Student student;
    private YearMonth yearMonth;
    private TotalAmount totalAmount;
    private boolean isPaid;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public PaymentBuilder() {
        student = DEFAULT_STUDENT;
        yearMonth = YearMonth.parse(DEFAULT_YEARMONTH);
        totalAmount = new TotalAmount(DEFAULT_AMOUNT);
        isPaid = DEFAULT_ISPAID;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code paymentToCopy}.
     */
    public PaymentBuilder(Payment paymentToCopy) {
        student = paymentToCopy.getStudent();
        yearMonth = paymentToCopy.getYearMonth();
        totalAmount = paymentToCopy.getTotalAmount();
        isPaid = paymentToCopy.isPaid();
    }

    /**
     * Sets the {@code Student} of the {@code Payment} that we are building.
     */
    public PaymentBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    /**
     * Sets the {@code YearMonth} of the {@code Payment} that we are building.
     */
    public PaymentBuilder withYearMonth(String yearMonth) {
        this.yearMonth = YearMonth.parse(yearMonth);
        return this;
    }

    /**
     * Sets the {@code totalAmount} of the {@code Payment} that we are building.
     */
    public PaymentBuilder withTotalAmount(float totalAmount) {
        this.totalAmount = new TotalAmount(totalAmount);
        return this;
    }

    /**
     * Sets the {@code totalAmount} of the {@link Payment} using a String.
     */
    public PaymentBuilder withTotalAmount(String amount) {
        float parsed = Float.parseFloat(amount);
        this.totalAmount = new TotalAmount(parsed);
        return this;
    }

    /**
     * Sets the {@code isPaid} of the {@code Payment} that we are building.
     */
    public PaymentBuilder withIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public Payment build() {
        return new Payment(student, yearMonth, totalAmount);
    }
}
