package seedu.address.model.payment;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the total amount in $ a student owes per month to a tutor.
 */
public class TotalAmount extends Amount {

    /**
     * Constructs a {@code TotalAmount}.
     * @param amt A valid totalAmount.
     */
    public TotalAmount(float amt) {
        super(amt);
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
    public UnpaidAmount calculateNewUnpaidAmount(UnpaidAmount unpaidAmount, TotalAmount newTotalAmount) {
        float calculatedUnpaid = unpaidAmount.getAsFloat() + newTotalAmount.getAsFloat() - getAsFloat();
        float newUnpaid = Math.max(calculatedUnpaid, 0);
        return new UnpaidAmount(newUnpaid);
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
        return amount == otherTotalAmount.getAsFloat();
    }
}
