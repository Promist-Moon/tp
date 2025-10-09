package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Represents an abstract Person in the address book.
 * A Person stores basic identity details such as name, phone number, and email address.
 * Subclasses (e.g., Student, Parent) may include additional fields specific to their roles.
 */
public abstract class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    protected Person(Name name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
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

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Person that = (Person) other;
        return name.equals(that.name)
                && phone.equals(that.phone)
                && email.equals(that.email);
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
        return Objects.hash(name, phone, email);
    }


    /**
     * Returns a user-facing string for display.
     * Subclasses may override this method to append their own fields.
     */
    public String toDisplayString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name)
                .append("; Phone: ").append(phone)
                .append("; Email: ").append(email);
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
                .toString();

    }
}
