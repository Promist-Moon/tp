package tutman.tuiniverse.model.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TotalAmountTest {

    @Test
    public void constructor_invalidTotalAmount_throwsIllegalArgumentException() {
        float invalidTotalAmount = -100;
        assertThrows(IllegalArgumentException.class, () -> new TotalAmount(invalidTotalAmount));
    }

    @Test
    public void toString_validFloat_formatsToTwoDecimalPlaces() {
        TotalAmount amount = new TotalAmount(123.4567f);
        assertEquals("123.46", amount.toString());
    }

    @Test
    public void toString_threeDpFloat_roundsDownCorrectly() {
        TotalAmount amount = new TotalAmount(123.451f);
        assertEquals("123.45", amount.toString());
    }

    @Test
    public void toString_zero_formatsCorrectly() {
        TotalAmount amount = new TotalAmount(0f);
        assertEquals("0.00", amount.toString());
    }

    @Test
    public void toString_trailingZeros_preserved() {
        TotalAmount amount = new TotalAmount(50.5f);
        assertEquals("50.50", amount.toString());
    }

    @Test
    public void equals() {
        TotalAmount totalAmount = new TotalAmount(666);

        // same values -> returns true
        assertTrue(totalAmount.equals(new TotalAmount(666)));

        // same object -> returns true
        assertTrue(totalAmount.equals(totalAmount));

        // different types -> returns false
        assertFalse(totalAmount.equals("666"));

        // different values -> returns false
        assertFalse(totalAmount.equals(new TotalAmount(665)));
    }
}
