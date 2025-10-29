package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.value.ObservableFloatValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.LessonTimeComparator;
import seedu.address.model.payment.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Lesson> filteredLessons;
    private final SortedList<Lesson> sortedFilteredLessons;
    private final ReadOnlyFloatWrapper totalEarnings = new ReadOnlyFloatWrapper(0.0f);
    private final ReadOnlyFloatWrapper totalUnpaid = new ReadOnlyFloatWrapper(0.0f);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredLessons = new FilteredList<>(this.addressBook.getLessonList());
        sortedFilteredLessons = new SortedList<>(filteredLessons, new LessonTimeComparator());
        recomputeTotalEarnings();
        recomputeTotalUnpaid();

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonListByPaymentStatus(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(person -> {
            // Check if the person is a Student
            if (person instanceof Student) {
                // Apply the predicate to the Student
                return predicate.test((Student) person);
            }
            // If not a Student, exclude from the filtered list
            return false;
        });
    }

    //=========== Filtered Lesson List Accessors =============================================================

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);

    }

    @Override
    public SortedList<Lesson> getSortedFilteredLessons() {
        return sortedFilteredLessons;
    }

    //====================================================================================================

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Lesson ================================================================================



    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return addressBook.hasLesson(lesson);
    }

    @Override
    public void addLesson(Student student, Lesson lesson) {
        requireNonNull(student);
        requireNonNull(lesson);

        addressBook.addLesson(lesson);
        LessonList ls = student.getLessonList();
        ls.addLesson(lesson);
        lesson.addStudent(student);

        LessonList oldLessonList = student.getLessonList();
        LessonList newLessonList = new LessonList(oldLessonList.getLessons());
        newLessonList.addLesson(lesson);

        Student editedStudent = new Student(
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                student.getTags(),
                newLessonList,
                student.getPayments()
        );

        setPerson(student, editedStudent);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    @Override
    public void deleteLesson(Student student, Lesson lesson) {
        requireNonNull(student);
        requireNonNull(lesson);

        addressBook.removeLesson(lesson);
        LessonList ls = student.getLessonList();
        ls.deleteLesson(lesson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    @Override
    public void setLesson(Student student, Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        addressBook.setLesson(target, editedLesson);
        LessonList studentLessonList = student.getLessonList();
        studentLessonList.setLesson(target, editedLesson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        recomputeTotalEarnings();
        recomputeTotalUnpaid();
    }

    @Override
    public ObservableFloatValue totalEarningsProperty() {
        return totalEarnings.getReadOnlyProperty();
    }

    private void recomputeTotalEarnings() {
        float sum = 0f;
        for (Person p : addressBook.getPersonList()) {
            if (p instanceof Student student) {
                sum += student.getTotalAmountFloat();
            }
        }
        totalEarnings.set(sum);
    }

    @Override
    public ObservableFloatValue totalUnpaidProperty() {
        return totalUnpaid.getReadOnlyProperty();
    }

    private void recomputeTotalUnpaid() {
        float sum = 0f;
        for (Person p : addressBook.getPersonList()) {
            if (p instanceof Student student) {
                sum += student.getPayments().getPayments().stream()
                        .map(Payment::getUnpaidAmountFloat)
                        .reduce(0f, Float::sum);
            }
        }
        totalUnpaid.set(sum);
    }

}
