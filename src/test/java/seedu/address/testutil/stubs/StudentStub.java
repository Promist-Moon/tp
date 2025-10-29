package seedu.address.testutil.stubs;

import java.time.YearMonth;
import java.util.Collections;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.payment.Payment;
import seedu.address.model.payment.PaymentList;
import seedu.address.model.person.student.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.tag.Tag;

public class StudentStub extends Student {
    private final float stubTotal;
    private final float stubUnpaid;

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

    private static PaymentList buildPayments(float unpaid) {
        YearMonth month = java.time.YearMonth.now();
        Payment payment = new seedu.address.testutil.PaymentBuilder()
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

    @Override
    public float getAmountDueFloat() {
        return stubUnpaid;
    }
}
