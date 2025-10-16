package seedu.address.model.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPayments.FEB_25;
import static seedu.address.testutil.TypicalPayments.JAN_24_600;
import static seedu.address.testutil.TypicalPayments.JAN_24_800;
import static seedu.address.testutil.TypicalPayments.JAN_25;
import static seedu.address.testutil.TypicalPayments.MAR_25_ALICE;
import static seedu.address.testutil.TypicalPayments.MAR_25_BOB;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.student.Student;
import seedu.address.testutil.PaymentBuilder;
import seedu.address.testutil.StudentBuilder;



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
        assertTrue(JAN_25.equals(JAN_25));

        // same values -> true
        Payment copyJan25 = new PaymentBuilder(JAN_25).build();
        assertTrue(JAN_25.equals(copyJan25));

        // null -> false
        assertFalse(JAN_25.equals(null));

        // different type -> false
        assertFalse(JAN_25.equals("not a payment"));

        // different YearMonth -> false
        assertFalse(JAN_25.equals(FEB_25));

        // different amount -> false
        Payment diffAmount = new PaymentBuilder(JAN_25).withTotalAmount(700f).build();
        assertFalse(JAN_25.equals(diffAmount));

        // different paid status (should still be equal if isPaid ignored)
        Payment paidVariant = new PaymentBuilder(JAN_25).withIsPaid(false).build();
        assertTrue(JAN_25.equals(paidVariant));
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        Payment a = new PaymentBuilder().build();
        Payment b = new PaymentBuilder(a).build();
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hashCode_excludesIsPaid() {
        Payment paidVariant = new PaymentBuilder(JAN_25).withIsPaid(false).build();
        assertEquals(paidVariant.hashCode(), JAN_25.hashCode());
    }

    @Test
    public void hashCode_differs() {
        // different year month -> different hash codes
        assertNotEquals(FEB_25.hashCode(), JAN_25.hashCode());

        // different amount -> different hash codes
        assertNotEquals(JAN_24_600.hashCode(), JAN_24_800.hashCode());
    }
}
