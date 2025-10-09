package seedu.address.testutil;

import seedu.address.model.person.Person;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Email;

public class PersonBuilder {

    private static final String DEFAULT_NAME = "Alice Pauline";
    private static final String DEFAULT_PHONE = "85355255";
    private static final String DEFAULT_EMAIL = "alice@example.com";

    private Name name;
    private Phone phone;
    private Email email;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new DummyPerson(name, phone, email);
    }

    private static class DummyPerson extends Person {
        DummyPerson(Name name, Phone phone, Email email) {
            super(name, phone, email);
        }
    }
}
