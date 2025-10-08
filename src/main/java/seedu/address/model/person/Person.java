package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public abstract class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email  email;

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

    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .toString();

    }
}
