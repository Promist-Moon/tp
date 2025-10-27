package seedu.address.model.payment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void isValidAmount() {
        // invalid total amounts
        assertFalse(TotalAmount.isValidAmount(-122)); // negative numbers

        // valid total amounts
        assertTrue(TotalAmount.isValidAmount(0)); // exactly 0
        assertTrue(TotalAmount.isValidAmount(60.00f)); // decimals
        assertTrue(TotalAmount.isValidAmount(91));
        assertTrue(TotalAmount.isValidAmount(911));
        assertTrue(TotalAmount.isValidAmount(93121534));
    }


    @Test
    public void equals_differentSubclass_sameValue_returnsFalse() {
        TotalAmount total = new TotalAmount(15.0f);
        UnpaidAmount unpaid = new UnpaidAmount(15.0f);
        // currently true because your equals() checks only `amount == other.getAsFloat()`
        assertFalse(total.equals(unpaid));
    }
}
