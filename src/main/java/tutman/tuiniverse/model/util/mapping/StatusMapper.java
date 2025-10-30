package tutman.tuiniverse.model.util.mapping;

import tutman.tuiniverse.model.payment.Status;
import tutman.tuiniverse.model.student.PaymentStatus;

/**
 * A mapper that converts a {@link Status} enum value into a {@link PaymentStatus} enum valye
 */
public final class StatusMapper {
    private StatusMapper() {}

    /**
     * Converts a {@link Status} enum value into a {@link PaymentStatus} enum value
     *
     * @param status a Status value in the payment package.
     * @return a PaymentStatus value from the student package.
     */
    public static PaymentStatus toPaymentStatus(Status status) {
        switch (status) {
        case PAID:
            return PaymentStatus.PAID;
        case UNPAID:
            return PaymentStatus.UNPAID;
        case OVERDUE:
            return PaymentStatus.OVERDUE;
        default:
            throw new IllegalArgumentException("Unknown status: " + status);
        }
    }
}
