package tutman.tuiniverse.model;

import javafx.collections.ObservableList;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Lesson> getLessonList();
}
