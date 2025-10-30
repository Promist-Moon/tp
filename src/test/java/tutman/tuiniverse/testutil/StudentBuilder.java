package tutman.tuiniverse.testutil;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonList;
import tutman.tuiniverse.model.payment.Payment;
import tutman.tuiniverse.model.payment.PaymentList;
import tutman.tuiniverse.model.payment.TotalAmount;
import tutman.tuiniverse.model.payment.UnpaidAmount;
import tutman.tuiniverse.model.student.Address;
import tutman.tuiniverse.model.student.Email;
import tutman.tuiniverse.model.student.Name;
import tutman.tuiniverse.model.student.Phone;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.model.student.tag.Tag;
import tutman.tuiniverse.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects for testing.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Aaron Tan";
    public static final String DEFAULT_PHONE = "99978799";
    public static final String DEFAULT_EMAIL = "aarontan@example.com";
    public static final String DEFAULT_ADDRESS = "666, NUS School of Computing, #08-111";
    public static final Lesson DEFAULT_LESSON = new LessonBuilder()
            .withSubject("Math")
            .withLevel("5")
            .withDay("7")
            .withRate("100")
            .withLessonTime("21:30", "23:59")
            .build();
    public static final Payment DEFAULT_PAYMENT = new Payment(
            YearMonth.parse("2025-10"),
            new TotalAmount(400f),
            new UnpaidAmount(0f)
    );

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private LessonList ll = new LessonList();
    private PaymentList pl = new PaymentList();

    /**
     * Creates a {@code StudentBuilder} with default details. This student is not in the typical addressbook.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        ll.addLesson(DEFAULT_LESSON);
        pl.addPayment(DEFAULT_PAYMENT);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        tags = new HashSet<>(studentToCopy.getTags());
        ll = studentToCopy.getLessonList();
        pl = studentToCopy.getPayments();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     *
     * @param name The name to set.
     * @return This {@code StudentBuilder} instance for chaining.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     *
     * @param phone The phone to set.
     * @return This {@code StudentBuilder} instance for chaining.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     *
     * @param email The email to set.
     * @return This {@code StudentBuilder} instance for chaining.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Lessonlist} of the {@code Student} that we are building.
     */
    public StudentBuilder withLessonList(LessonList ll) {
        this.ll = new LessonList(ll.getLessons());
        return this;
    }

    /**
     * Sets the {@code PaymentList} of the {@code Student} that we are building.
     */
    public StudentBuilder withPaymentList(PaymentList pl) {
        this.pl = pl;
        return this;
    }

    /**
     * Returns a new {@code Student} with the specified attributes.
     *
     * @return A new {@code Student} instance.
     */
    public Student build() {
        return new DummyPerson(name, phone, email, address, tags, ll, pl);
    }

    private static class DummyPerson extends Student {
        DummyPerson(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LessonList lessonList,
                    PaymentList paymentList) {
            super(name, phone, email, address, tags, lessonList, paymentList);
        }
    }
}
