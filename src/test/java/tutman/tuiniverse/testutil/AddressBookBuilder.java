package tutman.tuiniverse.testutil;

import tutman.tuiniverse.model.AddressBook;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Lesson} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLessonList(Lesson l) {
        addressBook.addLesson(l);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
