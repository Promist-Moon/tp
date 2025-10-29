package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.payment.PaymentList;
import seedu.address.model.person.Address;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.tag.Tag;


/**
 * Represents a Person in the address book.
 * A Person stores basic identity details such as name, phone number, and email address.
 * Subclasses (e.g., Student, Parent) may include additional fields specific to their roles.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final LessonList lessons;
    private final PaymentList payments;

    private PaymentStatus paymentStatus;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.lessons = new LessonList();
        this.payments = new PaymentList();
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LessonList ll) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.lessons = ll;
        this.payments = new PaymentList();
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
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
     * Returns a mutable TreeMap of Payments.
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
     * Checks if this person is equal to another object.
     * Two persons are considered equal if they have the same name, phone, and email,
     * and are of the same subclass type.
     *
     * @param other the object to compare with.
     * @return true if both objects represent the same person with identical identity fields.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        // might need to change
        if (other == null || !(other instanceof Person)) {
            return false;
        }

        Person that = (Person) other;
        return name.equals(that.name)
                && phone.equals(that.phone)
                && email.equals(that.email)
                && address.equals(that.address)
                && tags.equals(that.tags);
    }

    /**
     * Returns true if both person have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    public int hashCode() {
        return Objects.hash(name, phone, email, address, tags);
    }


    /**
     * Returns a user-facing string for display.
     * Subclasses may override this method to append their own fields.
     */
    public String toDisplayString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name)
                .append("; Phone: ").append(phone)
                .append("; Email: ").append(email)
                .append("; Address: ").append(address)
                .append("; Lessons: ").append(this.getLessonList().toString())
                .append("; Tags: ");
        this.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this {@code Person} for debugging purposes.
     * The string includes the person's {@code Name}, {@code Phone}, and {@code Email}, and is mainly intended
     * for logging or internal inspection rather than user display.
     *
     * @return a string representation of this person with their core identity fields.
     */
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("lessons", lessons)
                .add("tags", tags)
                .toString();

    }
}

