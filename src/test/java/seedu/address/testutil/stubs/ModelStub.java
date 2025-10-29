package seedu.address.testutil.stubs;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableFloatValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;


/**
 * A base Model stub that throws AssertionError for all methods.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Student person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Student person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Student target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Student target, Student editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public SortedList<Lesson> getSortedFilteredLessons() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonListByPaymentStatus(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLesson(Student student, Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteLesson(Student student, Lesson lesson) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void setLesson(Student student, Lesson lesson, Lesson editedLesson) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableFloatValue totalEarningsProperty() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableFloatValue totalUnpaidProperty() {
        throw new AssertionError("This method should not be called");
    }
}
