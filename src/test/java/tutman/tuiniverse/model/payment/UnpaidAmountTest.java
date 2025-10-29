package tutman.tuiniverse.model.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UnpaidAmountTest {

    @Test
    public void constructor_invalidUnpaidAmount_throwsIllegalArgumentException() {
        float invalidUnpaidAmount = -100;
        assertThrows(IllegalArgumentException.class, () -> new UnpaidAmount(invalidUnpaidAmount));
    }

    @Test
    public void toString_validFloat_formatsToTwoDecimalPlaces() {
        UnpaidAmount amount = new UnpaidAmount(123.4567f);
        assertEquals("123.46", amount.toString());
    }

    @Test
    public void toString_threeDpFloat_roundsDownCorrectly() {
        UnpaidAmount amount = new UnpaidAmount(123.451f);
        assertEquals("123.45", amount.toString());
    }

    @Test
    public void toString_zero_formatsCorrectly() {
        UnpaidAmount amount = new UnpaidAmount(0f);
        assertEquals("0.00", amount.toString());
    }

    @Test
    public void toString_trailingZeros_preserved() {
        UnpaidAmount amount = new UnpaidAmount(50.5f);
        assertEquals("50.50", amount.toString());
    }

    @Test
    public void equals() {
        UnpaidAmount unpaidAmount = new UnpaidAmount(666);

        // same values -> returns true
        assertTrue(unpaidAmount.equals(new UnpaidAmount(666)));

        // same object -> returns true
        assertTrue(unpaidAmount.equals(unpaidAmount));

        // different types -> returns false
        assertFalse(unpaidAmount.equals("666"));

        // different values -> returns false
        assertFalse(unpaidAmount.equals(new UnpaidAmount(665)));
    }
}
