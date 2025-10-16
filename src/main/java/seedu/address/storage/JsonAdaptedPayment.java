package seedu.address.storage;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;
import seedu.address.model.payment.Payment;

/**
 * Jackson-friendly version of {@link Payment}.
 */
public class JsonAdaptedPayment {
    private final JsonAdaptedPerson student;
    private final String yearMonth;
    private final String totalAmount;
    private final boolean isPaid;

    /**
     * Constructs a {@code JsonAdaptedPayment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("student") JsonAdaptedPerson student,
                             @JsonProperty("yearMonth") String yearMonth, @JsonProperty("totalAmount") String totalAmount,
                             @JsonProperty("isPaid") boolean isPaid) {
        this.student = student;
        this.yearMonth = yearMonth;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     */
    public JsonAdaptedPayment(Payment source) {
        student = new JsonAdaptedPerson(source.getStudent());
        yearMonth = source.getYearMonth().toString();
        totalAmount = source.getTotalAmount().toString();
        isPaid = source.isPaid();
    }

    /**
     * Converts this Jackson-friendly adapted Payment object into the model's {@code PaymentList} object.
     *
     */
    public Payment toModelType() throws IllegalValueException {
        final Person person = student.toModelType();
        if (!(person instanceof Student)) {
            throw new IllegalValueException("Payment expected a Student but got: " + person.getClass().getSimpleName());
        }

        Payment p = new Payment(
                (Student) person,
                YearMonth.parse(yearMonth),
                new TotalAmount(Float.parseFloat(totalAmount)));
        if (isPaid) {
            p.markPaid();
        }

        return p;
    }

}

