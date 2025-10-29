package tutman.tuiniverse.model.util.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tutman.tuiniverse.model.payment.Status;
import tutman.tuiniverse.model.person.student.PaymentStatus;

class StatusMapperTest {

    @Test
    @DisplayName("Status.PAID converts to PaymentStatus.PAID")
    void toPaymentStatus_paidStatus_returnsPaid() {
        assertEquals(PaymentStatus.PAID,
                StatusMapper.toPaymentStatus(Status.PAID),
                "Expected PAID to map to PaymentStatus.PAID");
    }

    @Test
    @DisplayName("Status.UNPAID converts to PaymentStatus.UNPAID")
    void toPaymentStatus_unpaidStatus_returnsUnpaid() {
        assertEquals(PaymentStatus.UNPAID,
                StatusMapper.toPaymentStatus(Status.UNPAID),
                "Expected UNPAID to map to PaymentStatus.UNPAID");
    }

    @Test
    @DisplayName("Status.OVERDUE converts to PaymentStatus.OVERDUE")
    void toPaymentStatus_overdueStatus_returnsOverdue() {
        assertEquals(PaymentStatus.OVERDUE,
                StatusMapper.toPaymentStatus(Status.OVERDUE),
                "Expected OVERDUE to map to PaymentStatus.OVERDUE");
    }

    @Test
    @DisplayName("Null input throws NullPointerException")
    void toPaymentStatus_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StatusMapper.toPaymentStatus(null));
    }
}
