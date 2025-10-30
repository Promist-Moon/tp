package tutman.tuiniverse.testutil.stubs;

import java.time.YearMonth;
import java.util.Collections;

import tutman.tuiniverse.model.lesson.LessonList;
import tutman.tuiniverse.model.payment.Payment;
import tutman.tuiniverse.model.payment.PaymentList;
import tutman.tuiniverse.model.student.Address;
import tutman.tuiniverse.model.student.Email;
import tutman.tuiniverse.model.student.Name;
import tutman.tuiniverse.model.student.Phone;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.model.student.tag.Tag;
import tutman.tuiniverse.testutil.PaymentBuilder;

/**
 * A base Student stub that creates a null lesson and payment list.
 * To be used in ModelManager tests to test listeners.
 */
public class StudentStub extends Student {
    private final float stubTotal;
    private final float stubUnpaid;

    /**
     * Constructs a stub of a Student
     *
     * @param name
     * @param total the total earnings of a student
     * @param unpaid the unpaid amount of a student
     */
    public StudentStub(String name, float total, float unpaid) {
        super(
                new Name(name),
                new Phone("99999999"),
                new Email(name.toLowerCase() + "@ex.com"),
                new Address("stub"),
                Collections.<Tag>emptySet(),
                new LessonList(),
                buildPayments(unpaid)
        );
        this.stubTotal = total;
        this.stubUnpaid = unpaid;
    }

    /**
     * Builds a PaymentList containing a singular payment with the specified unpaid amount.
     *
     * @param unpaid a float to create an UnpaidAmount
     * @return
     */
    private static PaymentList buildPayments(float unpaid) {
        YearMonth month = java.time.YearMonth.now();
        Payment payment = new PaymentBuilder()
                .withYearMonth(month.toString())
                .withTotalAmount(Math.max(unpaid, 0f))
                .withUnpaidAmount(unpaid)
                .build();
        return new PaymentList(payment);
    }

    @Override
    public float getTotalAmountFloat() {
        return stubTotal;
    }
}
