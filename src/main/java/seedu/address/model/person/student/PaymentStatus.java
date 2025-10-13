package seedu.address.model.person.student;

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
    OVERDUE
}
