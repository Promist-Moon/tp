package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.YearMonth;

import seedu.address.model.payment.Payment;
import seedu.address.model.payment.TotalAmount;

/**
 * A utility class containing a list of {@code Payment} objects to be used in tests.
 */
public class TypicalPayments {

    public static final Payment JAN_25 = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2025, 1)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(true).build();
    public static final Payment FEB_25 = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2025, 2)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(false).build();
    public static final Payment MAR_25_ALICE = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2025, 3)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(false).build();
    public static final Payment SEP_25 = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2025, 9)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(false).build();
    public static final Payment MAR_25_BOB = new PaymentBuilder().withStudent(BOB)
            .withYearMonth(YearMonth.of(2025, 3)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(false).build();
    public static final Payment JAN_24 = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2024, 1)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(true).build();;
    public static final Payment JAN_26 = new PaymentBuilder().withStudent(ALICE)
            .withYearMonth(YearMonth.of(2026, 1)).withTotalAmount(new TotalAmount(600))
            .withIsPaid(false).build();

    private TypicalPayments() {} // prevents instantiation
}
