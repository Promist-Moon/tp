package tutman.tuiniverse.model.payment;

import static tutman.tuiniverse.commons.util.CollectionUtil.requireAllNonNull;

import java.time.YearMonth;
import java.util.Objects;


/**
 * Represents a Payment a Student makes in a month/year.
 */
public class Payment {
    private YearMonth yearMonth;
    private TotalAmount totalAmount;
    private UnpaidAmount unpaidAmount;

    /**
     * Constructs a new {@code Payment} object for a specified student.
     * This constructor initializes the payment details including
     * the associated student, total number of hours worked, and the hourly rate.
     *
     * @param yearMonth      the year and month corresponding to payment
     * @param totalAmount  the total amount due per month by a student
     */
    public Payment(YearMonth yearMonth, TotalAmount totalAmount) {
        requireAllNonNull(yearMonth, totalAmount);
        this.totalAmount = totalAmount;
        this.yearMonth = yearMonth;
        this.unpaidAmount = new UnpaidAmount(getTotalAmountFloat());
    }

    /**
     * Constructs a new {@code Payment} object for a specified student.
     * This constructor includes the boolean isPaid
     *
     * @param yearMonth
     * @param totalAmount
     * @param unpaidAmount
     */
    public Payment(YearMonth yearMonth, TotalAmount totalAmount, UnpaidAmount unpaidAmount) {
        requireAllNonNull(yearMonth, totalAmount);
        this.totalAmount = totalAmount;
        this.yearMonth = yearMonth;
        this.unpaidAmount = unpaidAmount;
    }

    /**
     * Copy constructor that creates a deep copy of another Payment.
     *
     * @param other the Payment to copy from
     */
    public Payment(Payment other) {
        requireAllNonNull(other);
        this.yearMonth = other.yearMonth;
        this.totalAmount = other.totalAmount;
        this.unpaidAmount = other.unpaidAmount;
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

    public UnpaidAmount getUnpaidAmount() {
        return this.unpaidAmount;
    }

    public float getUnpaidAmountFloat() {
        return this.unpaidAmount.getAsFloat();
    }

    public void setTotalAmount(float f) {
        this.totalAmount = new TotalAmount(f);
    }

    public boolean isPaid() {
        return this.unpaidAmount.isZero();
    }

    /**
     * Updates the payment upon {@link PaymentList} listening for changes in student's LessonList.
     *
     * @param newTotalAmount a float representing the new total amount calculated from lessonList.
     */
    public void updatePayment(float newTotalAmount) {
        TotalAmount newTotal = new TotalAmount(newTotalAmount);
        int cmp = newTotal.compareTo(this.totalAmount);

        if (cmp != 0) {
            // use the helper method inside TotalAmount
            this.unpaidAmount = this.totalAmount.calculateNewUnpaidAmount(
                    this.unpaidAmount, newTotal);

            this.totalAmount = newTotal;
        }
    }

    /**
     * Marks current payment as paid
     * If the payment is already marked as paid, this method has no effect.
     */
    public void markPaid() {
        this.unpaidAmount = new UnpaidAmount(0);
    }

    @Override
    public String toString() {
        return String.format("Payment[Month=%s, Amount=%.2f, Paid=%s]",
                yearMonth,
                totalAmount.getAsFloat(),
                isPaid() ? "Paid" : "Unpaid");
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
        return yearMonth.equals(otherPayment.yearMonth)
                && totalAmount.equals(otherPayment.totalAmount); // exclusion of isPaid is intentional
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearMonth, totalAmount); // exclusion of isPaid is intentional
    }

}
