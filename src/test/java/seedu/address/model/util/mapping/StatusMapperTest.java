package seedu.address.model.util.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.address.model.payment.Status;
import seedu.address.model.person.student.PaymentStatus;

class StatusMapperTest {

    @Test
    @DisplayName("Status.PAID → PaymentStatus.PAID")
    void toPaymentStatus_PaidStatus_ReturnsPaid() {
        assertEquals(PaymentStatus.PAID,
                StatusMapper.toPaymentStatus(Status.PAID),
                "Expected PAID to map to PaymentStatus.PAID");
    }

    @Test
    @DisplayName("Status.UNPAID → PaymentStatus.UNPAID")
    void toPaymentStatus_UnpaidStatus_ReturnsUnpaid() {
        assertEquals(PaymentStatus.UNPAID,
                StatusMapper.toPaymentStatus(Status.UNPAID),
                "Expected UNPAID to map to PaymentStatus.UNPAID");
    }

    @Test
    @DisplayName("Status.OVERDUE → PaymentStatus.OVERDUE")
    void toPaymentStatus_OverdueStatus_ReturnsOverdue() {
        assertEquals(PaymentStatus.OVERDUE,
                StatusMapper.toPaymentStatus(Status.OVERDUE),
                "Expected OVERDUE to map to PaymentStatus.OVERDUE");
    }

    @Test
    @DisplayName("Null input → throws NullPointerException")
    void toPaymentStatus_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StatusMapper.toPaymentStatus(null));
    }
}
