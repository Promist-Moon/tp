package seedu.address.model.payment;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the total amount in $ a student owes per month to a tutor.
 * Guarantees: immutable; is valid as declared in {@link #isValidTotalAmount(float)}
 */
public class TotalAmount {

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
