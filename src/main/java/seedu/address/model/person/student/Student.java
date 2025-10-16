package seedu.address.model.person.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.payment.PaymentList;
import seedu.address.model.payment.exceptions.PaymentException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final LessonList lessons;
    private final PaymentList payments;

    private PaymentStatus paymentStatus;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email);
        requireAllNonNull(address, tags);
        this.address = address;
        this.tags.addAll(tags);
        this.lessons = new LessonList();
        this.payments = new PaymentList();
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LessonList ll, PaymentList pl) {
        super(name, phone, email);
        requireAllNonNull(address, tags);
        this.address = address;
        this.tags.addAll(tags);
        this.lessons = ll;
        this.payments = pl;
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an mutable ArrayList of Lessons.
     */
    public LessonList getLessonList() {
        return this.lessons;
    }

    /**
     * Returns a mutable ArrayList of Payments.
     */
    public PaymentList getPaymentList() {
        return this.payments;
    }

    /**
     * Returns the payment status of the student for the month
     */
    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * Changes payment status to {@link PaymentStatus}
     *
     * Changes to paid when pay command is used.
     * Changes to unpaid when a new month has started.
     * Changes to overdue when a new month has started and previous month is still unpaid.
     *
     * @param paymentStatus paid, unpaid, or overdue.
     */
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        requireNonNull(paymentStatus);
        this.paymentStatus = paymentStatus;
    }

    /**
     * Marks all the payments in the student's PaymentList as paid.
     *
     * @throws PaymentException
     */
    public void pay() throws PaymentException {
        payments.markAllPaid();
        setPaymentStatus(PaymentStatus.PAID);
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // compare Person identity
        if (!super.equals(other)) {
            return false;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return address.equals(otherStudent.address)
                && tags.equals(otherStudent.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.hashCode(), address, tags);
    }

    @Override
    public String toDisplayString() {
        final StringBuilder builder = new StringBuilder(super.toDisplayString());
        builder.append("; Address: ").append(address)
                .append("; Tags: ");
        this.getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
