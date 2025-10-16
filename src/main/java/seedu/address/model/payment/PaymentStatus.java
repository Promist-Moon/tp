package seedu.address.model.payment;

/**
 * Represents the payment status of a PaymentList which will be used to update student
 * payment status.
 * A payment can be {@code PAID}, {@code UNPAID}, or {@code OVERDUE}.
 *
 * *   {@code PAID} — All payments in the payment list are paid.
 * *   {@code UNPAID} — The most recent payment in payment list is unpaid.
 * *   {@code OVERDUE} — There exists payments prior to most recent payment that are unpaid.
 */
public enum PaymentStatus {
    PAID,
    UNPAID,
    OVERDUE;

    @Override
    public String toString() {
        // Capitalize first letter only, e.g. "Paid"
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
