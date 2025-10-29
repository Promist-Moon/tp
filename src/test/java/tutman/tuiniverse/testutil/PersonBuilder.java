package tutman.tuiniverse.testutil;

import tutman.tuiniverse.model.person.Email;
import tutman.tuiniverse.model.person.Name;
import tutman.tuiniverse.model.person.Person;
import tutman.tuiniverse.model.person.Phone;


/**
 * A utility class to help with building Person objects for testing.
 */
public class PersonBuilder {

    private static final String DEFAULT_NAME = "Alice Pauline";
    private static final String DEFAULT_PHONE = "85355255";
    private static final String DEFAULT_EMAIL = "alice@example.com";

    private Name name;
    private Phone phone;
    private Email email;

    /**
     * Creates a {@code PersonBuilder} with default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     *
     * @param name The name to set.
     * @return This {@code PersonBuilder} instance for chaining.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     *
     * @param phone The phone to set.
     * @return This {@code PersonBuilder} instance for chaining.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     *
     * @param email The email to set.
     * @return This {@code PersonBuilder} instance for chaining.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Returns a new {@code Person} with the specified attributes.
     *
     * @return A new {@code Person} instance.
     */
    public Person build() {
        return new DummyPerson(name, phone, email);
    }

    private static class DummyPerson extends Person {
        DummyPerson(Name name, Phone phone, Email email) {
            super(name, phone, email);
        }
    }
}
