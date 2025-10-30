package tutman.tuiniverse.storage;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutman.tuiniverse.commons.exceptions.IllegalValueException;
import tutman.tuiniverse.model.payment.Payment;
import tutman.tuiniverse.model.payment.TotalAmount;
import tutman.tuiniverse.model.payment.UnpaidAmount;

/**
 * Jackson-friendly version of {@link Payment}.
 */
public class JsonAdaptedPayment {
    private final String yearMonth;
    private final String totalAmount;
    private final String unpaidAmount;

    /**
     * Constructs a {@code JsonAdaptedPayment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("yearMonth") String yearMonth,
                              @JsonProperty("totalAmount") String totalAmount,
                              @JsonProperty("unpaidAmount") String unpaidAmount) {
        this.yearMonth = yearMonth;
        this.totalAmount = totalAmount;
        this.unpaidAmount = unpaidAmount;
    }

    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     */
    public JsonAdaptedPayment(Payment source) {
        yearMonth = source.getYearMonth().toString();
        totalAmount = source.getTotalAmount().toString();
        unpaidAmount = source.getUnpaidAmount().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Payment object into the model's {@code PaymentList} object.
     *
     */
    public Payment toModelType() throws IllegalValueException {
        Payment p = new Payment(
                YearMonth.parse(yearMonth),
                new TotalAmount(Float.parseFloat(totalAmount)),
                new UnpaidAmount(Float.parseFloat(unpaidAmount)));

        return p;
    }

}

