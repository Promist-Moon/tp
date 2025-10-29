package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableFloatValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Student person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Student target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Student person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Student target, Student editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Student> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered lesson list */
    ObservableList<Lesson> getFilteredLessonList();

    /** Returns the sorted lesson list */
    SortedList<Lesson> getSortedFilteredLessons();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonListByPaymentStatus(Predicate<Student> predicate);

    /**
     * Returns true if a lesson with the same timeslot as {@code lesson} exists in the address book.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the address book.
     */
    void addLesson(Student student, Lesson lesson);

    /**
     * Adds the given payment.
     * {@code lesson} must exist in the address book.
     */
    void deleteLesson(Student student, Lesson lesson);

    /**
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     * {@code target} must exist in the address book.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the address book.
     */
    void setLesson(Student student, Lesson lesson, Lesson editedLesson);

    /**
     * Returns a float value of the total earned from all students for the month.
     * This should be the total of totalAmounts in all student's payment lists.
     */
    ObservableFloatValue totalEarningsProperty();

    /**
     * Returns a float value of the total yet to be paid from all students.
     * This should be the total of unpaidAmounts in all student's payment lists.
     */
    ObservableFloatValue totalUnpaidProperty();
}
