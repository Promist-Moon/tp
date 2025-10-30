package tutman.tuiniverse.model.payment;

import static tutman.tuiniverse.commons.util.AppUtil.checkArgument;

/**
 * Represents a monetary amount in $ represented by a payment.
 * Can represent two types of payments: TotalAmount and UnpaidAmount.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(float)}
 */
public abstract class Amount implements Comparable<Amount> {
    public static final String MESSAGE_CONSTRAINTS = "Total amount must be positive";

    protected final float amount;

    /**
     * Constructs a {@code Amount}.
     * @param amt A valid amount.
     */
    public Amount(float amt) {
        checkArgument(isValidAmount(amt), MESSAGE_CONSTRAINTS);
        this.amount = amt;
    }

    /**
     * Checks if the given float is a valid totalAmount.
     */
    public static boolean isValidAmount(float amt) {
        return amt >= 0;
    }

    /**
     * Returns totalAmount as a float.
     */
    public float getAsFloat() {
        return amount;
    }

    @Override
    public int compareTo(Amount other) {
        return Float.compare(this.amount, other.amount);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Amount)) {
            return false;
        }

        Amount otherAmount = (Amount) other;
        return amount == otherAmount.getAsFloat();
    }
}
