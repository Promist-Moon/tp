package seedu.address.model.payment;

/**
 * Represents the total amount in $ a student has not paid to a tutor per month.
 */
public class UnpaidAmount extends Amount {

    public UnpaidAmount(float amt) {
        super(amt);
    }

    /**
     * Checks whether the amount is equal to 0.
     * This is used by unpaidAmount to check whether the amount is paid.
     *
     * @return a boolean on whether the amount is 0.
     */
    public boolean isZero() {
        return this.amount == 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpaidAmount)) {
            return false;
        }

        UnpaidAmount otherUnpaidAmount = (UnpaidAmount) other;
        return amount == otherUnpaidAmount.getAsFloat();
    }
}
