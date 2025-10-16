package seedu.address.storage;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.payment.Payment;

/**
 * Jackson-friendly version of {@link Payment}.
 */
public class JsonAdaptedPayment {
    private final String yearMonth;
    private final String totalAmount;
    private final boolean isPaid;

    /**
     * Constructs a {@code JsonAdaptedPayment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("yearMonth") String yearMonth, @JsonProperty("totalAmount") String totalAmount,
                             @JsonProperty("isPaid") boolean isPaid) {
        this.yearMonth = yearMonth;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     */
    public JsonAdaptedPayment(Payment source) {
        yearMonth = source.getYearMonth().toString();
        totalAmount = source.getTotalAmount().toString();
        isPaid = source.isPaid();
    }

    /**
     * Converts this Jackson-friendly adapted Payment object into the model's {@code PaymentList} object.
     *
     */
    public Payment toModelType() throws IllegalValueException {
        Payment p = new Payment(
                YearMonth.parse(yearMonth),
                new TotalAmount(Float.parseFloat(totalAmount)));
        if (isPaid) {
            p.markPaid();
        }

        return p;
    }

}

