package seedu.address.model.payment;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the total amount in $ a student owes per month to a tutor.
 * Guarantees: immutable; is valid as declared in {@link #isValidTotalAmount(float)}
 */
public class TotalAmount implements Comparable<TotalAmount> {

    public static final String MESSAGE_CONSTRAINTS = "Total amount must be positive";

    private final float totalAmount;

    /**
     * Constructs a {@code TotalAmount}.
     * @param amt A valid totalAmount.
     */
    public TotalAmount(float amt) {
        checkArgument(isValidTotalAmount(amt), MESSAGE_CONSTRAINTS);
        this.totalAmount = amt;
    }

    /**
     * Checks if the given float is a valid totalAmount.
     */
    public static boolean isValidTotalAmount(float amt) {
        return amt >= 0;
    }

    /**
     * Returns totalAmount as a float.
     */
    public float getAsFloat() {
        return this.totalAmount;
    }

    /**
     * Checks whether the amount is equal to 0.
     * This is used by unpaidAmount to check whether the amount is paid.
     *
     * @return a boolean on whether the amount is 0.
     */
    public boolean isZero() {
        return this.totalAmount == 0;
    }

    /**
     * Calculates and returns an updated unpaid amount based on a change in the total amount.
     * This method computes the new unpaid amount using the formula:
     * newUnpaid = max(currentUnpaid + newTotal - currentTotal, 0)
     * to ensure that the result is never negative.
     *
     * @param unpaidAmount the current unpaid amount before the total is updated.
     * @param newTotalAmount the new total amount after changes in lessons.
     * @return a new {@code TotalAmount} instance representing the updated unpaid amount.
     */
    public TotalAmount calculateNewUnpaidAmount(TotalAmount unpaidAmount, TotalAmount newTotalAmount) {
        float calculatedUnpaid = unpaidAmount.getAsFloat() + newTotalAmount.getAsFloat() - getAsFloat();
        float newUnpaid = Math.max(calculatedUnpaid, 0);
        return new TotalAmount(newUnpaid);
    }

    @Override
    public int compareTo(TotalAmount other) {
        return Float.compare(this.totalAmount, other.getAsFloat());
    }

    @Override
    public String toString() {
        return String.format("%.2f", totalAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TotalAmount)) {
            return false;
        }

        TotalAmount otherTotalAmount = (TotalAmount) other;
        return totalAmount == otherTotalAmount.getAsFloat();
    }
}
