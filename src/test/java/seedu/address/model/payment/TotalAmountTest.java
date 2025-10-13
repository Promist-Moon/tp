package seedu.address.model.payment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TotalAmountTest {

    @Test
    public void constructor_invalidTotalAmount_throwsIllegalArgumentException() {
        float invalidTotalAmount = -100;
        assertThrows(IllegalArgumentException.class, () -> new TotalAmount(invalidTotalAmount));
    }

    @Test
    public void isValidTotalAmount() {
        // invalid total amounts
        assertFalse(TotalAmount.isValidTotalAmount(-122)); // negative numbers

        // valid total amounts
        assertTrue(TotalAmount.isValidTotalAmount(0)); // exactly 0
        assertTrue(TotalAmount.isValidTotalAmount((float) 60.00)); // decimals
        assertTrue(TotalAmount.isValidTotalAmount(91));
        assertTrue(TotalAmount.isValidTotalAmount(911));
        assertTrue(TotalAmount.isValidTotalAmount(93121534));
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
