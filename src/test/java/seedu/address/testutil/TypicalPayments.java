package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.payment.Payment;

/**
 * A utility class containing a list of {@code Payment} objects to be used in tests.
 */
public class TypicalPayments {


    public static Payment jan25Paid() {
        return new PaymentBuilder()
                .withYearMonth("2025-01")
                .withTotalAmount(600f)
                .withIsPaid(true)
                .build();
    }

    public static Payment feb25Unpaid() {
        return new PaymentBuilder()
                .withYearMonth("2025-02")
                .withTotalAmount(600f)
                .withIsPaid(false)
                .build();
    }

    public static Payment feb25Paid() {
        return new PaymentBuilder()
                .withYearMonth("2025-02")
                .withTotalAmount(600f)
                .withIsPaid(true)
                .build();
    }

    public static Payment mar25Unpaid() {
        return new PaymentBuilder()
                .withYearMonth("2025-03")
                .withTotalAmount(600f)
                .withIsPaid(false)
                .build();
    }

    public static Payment sep25Unpaid() {
        return new PaymentBuilder()
                .withYearMonth("2025-09")
                .withTotalAmount(600f)
                .withIsPaid(false)
                .build();
    }

    public static Payment jan24Paid600() {
        return new PaymentBuilder()
                .withYearMonth("2024-01")
                .withTotalAmount(600f)
                .withIsPaid(true)
                .build();
    }

    public static Payment jan24Unpaid800() {
        return new PaymentBuilder()
                .withYearMonth("2024-01")
                .withTotalAmount(800f)
                .withIsPaid(false)
                .build();
    }

    public static ArrayList<Payment> sampleArrayList() {
        return new ArrayList<>(List.of(jan25Paid(), feb25Unpaid(), mar25Unpaid()));
    }

    private TypicalPayments() {} // prevents instantiation
}
