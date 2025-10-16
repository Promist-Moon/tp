package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import seedu.address.model.payment.Payment;

/**
 * A utility class containing a list of {@code Payment} objects to be used in tests.
 */
public class TypicalPayments {

    public static final Payment JAN_25 = new PaymentBuilder()
            .withYearMonth("2025-01").withTotalAmount(600f)
            .withIsPaid(true).build();
    public static final Payment FEB_25 = new PaymentBuilder()
            .withYearMonth("2025-02").withTotalAmount(600f)
            .withIsPaid(false).build();
    public static final Payment MAR_25_ALICE = new PaymentBuilder()
            .withYearMonth("2025-03").withTotalAmount(600f)
            .withIsPaid(false).build();
    public static final Payment SEP_25 = new PaymentBuilder()
            .withYearMonth("2025-09").withTotalAmount(600f)
            .withIsPaid(false).build();
    public static final Payment MAR_25_BOB = new PaymentBuilder()
            .withYearMonth("2025-03").withTotalAmount(600f)
            .withIsPaid(false).build();
    public static final Payment JAN_24_600 = new PaymentBuilder()
            .withYearMonth("2024-01").withTotalAmount(600f)
            .withIsPaid(true).build();;
    public static final Payment JAN_24_800 = new PaymentBuilder()
            .withYearMonth("2024-01").withTotalAmount(800f)
            .withIsPaid(false).build();

    private TypicalPayments() {} // prevents instantiation
}
