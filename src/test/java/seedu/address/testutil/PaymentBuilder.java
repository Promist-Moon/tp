package seedu.address.testutil;

import java.time.YearMonth;

import seedu.address.model.payment.Payment;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.payment.UnpaidAmount;

/**
 * A utility class to help with building Payment objects.
 */
public class PaymentBuilder {

    public static final String DEFAULT_YEARMONTH = "2025-10";
    public static final float DEFAULT_TOTAL = 600f;
    public static final float DEFAULT_UNPAID = 600f;

    private YearMonth yearMonth;
    private TotalAmount totalAmount;
    private UnpaidAmount unpaidAmount;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public PaymentBuilder() {
        yearMonth = YearMonth.parse(DEFAULT_YEARMONTH);
        totalAmount = new TotalAmount(DEFAULT_TOTAL);
        unpaidAmount = new UnpaidAmount(DEFAULT_UNPAID);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code paymentToCopy}.
     */
    public PaymentBuilder(Payment paymentToCopy) {
        yearMonth = paymentToCopy.getYearMonth();
        totalAmount = paymentToCopy.getTotalAmount();
        unpaidAmount = paymentToCopy.getUnpaidAmount();
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
     * Sets the {@code unpaidAmount} of the {@code Payment} that we are building.
     */
    public PaymentBuilder withUnpaidAmount(float unpaidAmount) {
        this.unpaidAmount = new UnpaidAmount(unpaidAmount);
        return this;
    }

    /**
     * Sets the {@code unpaidAmount} of the {@link Payment} using a String.
     */
    public PaymentBuilder withUnpaidAmount(String amount) {
        float parsed = Float.parseFloat(amount);
        this.unpaidAmount = new UnpaidAmount(parsed);
        return this;
    }

    public Payment build() {
        return new Payment(yearMonth, totalAmount, unpaidAmount);
    }
}
