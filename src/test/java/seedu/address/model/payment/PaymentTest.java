package seedu.address.model.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPayments.feb25Paid;
import static seedu.address.testutil.TypicalPayments.feb25Unpaid;
import static seedu.address.testutil.TypicalPayments.jan24Paid600;
import static seedu.address.testutil.TypicalPayments.jan24Unpaid800;
import static seedu.address.testutil.TypicalPayments.jan25Paid;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PaymentBuilder;



public class PaymentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaymentBuilder().withYearMonth(null).build());
        assertThrows(NullPointerException.class, () -> new PaymentBuilder().withTotalAmount(null).build());
    }

    /*
    Testing the payment constructor
     */
    @Test
    public void constructor_validArgs_success() {
        YearMonth ym = YearMonth.of(2025, 10);
        TotalAmount total = new TotalAmount(600f);

        Payment p = new Payment(ym, total);

        assertEquals(ym, p.getYearMonth());
        assertEquals(total, p.getTotalAmount());
        assertFalse(p.isPaid());
    }

    @Test
    public void getYearMonth_returnsCorrectValue() {
        String ymStr = "2024-12";
        YearMonth ym = YearMonth.parse(ymStr);
        Payment payment = new PaymentBuilder().withYearMonth(ymStr).build();
        assertEquals(ym, payment.getYearMonth());
    }

    @Test
    public void getTotalAmount_returnsCorrectValue() {
        float value = 700f;
        TotalAmount total = new TotalAmount(value);
        Payment payment = new PaymentBuilder().withTotalAmount(value).build();
        assertEquals(total, payment.getTotalAmount());
    }

    @Test
    public void getTotalAmountFloat_returnsCorrectFloat() {
        float value = 600f;
        Payment payment = new PaymentBuilder().withTotalAmount(value).build();
        assertEquals(value, payment.getTotalAmountFloat());
    }

    @Test
    public void isPaid_defaultFalse_andTrueAfterMarkingPaid() {
        Payment payment = new PaymentBuilder().build();
        assertFalse(payment.isPaid());
        payment.markPaid();
        assertTrue(payment.isPaid());
    }

    @Test
    public void equals() {
        // same object -> true
        assertTrue(jan25Paid().equals(jan25Paid()));

        // same values -> true
        Payment copyJan25 = new PaymentBuilder(jan25Paid()).build();
        assertTrue(jan25Paid().equals(copyJan25));

        // null -> false
        assertFalse(jan25Paid().equals(null));

        // different type -> false
        assertFalse(jan25Paid().equals("not a payment"));

        // different YearMonth -> false
        assertFalse(jan25Paid().equals(feb25Paid()));

        // different amount -> false
        Payment diffAmount = new PaymentBuilder(jan25Paid()).withTotalAmount(700f).build();
        assertFalse(jan25Paid().equals(diffAmount));

        // different paid status (should still be equal if isPaid ignored)
        Payment paidVariant = new PaymentBuilder(jan25Paid()).withIsPaid(false).build();
        assertTrue(jan25Paid().equals(paidVariant));
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        Payment a = new PaymentBuilder().build();
        Payment b = new PaymentBuilder(a).build();
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hashCode_differs() {
        // different year month -> different hash codes
        assertNotEquals(feb25Unpaid().hashCode(), jan25Paid().hashCode());

        // different amount -> different hash codes
        assertNotEquals(jan24Paid600().hashCode(), jan24Unpaid800().hashCode());
    }
}
