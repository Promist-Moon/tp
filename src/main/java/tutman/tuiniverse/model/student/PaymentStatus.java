package tutman.tuiniverse.model.student;

/**
 * Represents the payment status of a student for a specific month.
 * A payment can be {@code PAID}, {@code UNPAID}, or {@code OVERDUE}.
 *
 * *   {@code PAID} — The student has completed the payment for the month.
 * *   {@code UNPAID} — The payment for the month is still outstanding but not yet overdue.
 * *   {@code OVERDUE} — The payment deadline has passed without completion.
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
