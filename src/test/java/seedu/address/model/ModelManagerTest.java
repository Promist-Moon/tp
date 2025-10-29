package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.value.ChangeListener;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Student;
import seedu.address.model.person.StudentMatchesPaymentStatusPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.stubs.StudentStub;


public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    void setUp() {
        // Always start clean
        modelManager = new ModelManager();
    }

    @AfterEach
    void tearDown() {
        modelManager = null;
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasLesson_lessonNotInAddressBook_returnsFalse() {
        Lesson lesson = new LessonBuilder().build();
        assertFalse(modelManager.hasLesson(lesson));
    }


    @Test
    public void addLesson_nullStudent_throwsNullPointerException() {
        Lesson lesson = new LessonBuilder().build();
        assertThrows(NullPointerException.class, () -> modelManager.addLesson(null, lesson));
    }

    @Test
    public void deleteLesson_nullStudent_throwsNullPointerException() {
        Lesson lesson = new LessonBuilder().build();
        assertThrows(NullPointerException.class, () -> modelManager.deleteLesson(null, lesson));
    }

    @Test
    public void deleteLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteLesson(ALICE, null));
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addLesson(ALICE, null));
    }

    @Test
    public void addLesson_validStudentAndLesson_success() {
        ModelManager modelManager = new ModelManager();
        Student originalStudent = new StudentBuilder().build();
        modelManager.addPerson(originalStudent);

        Lesson lesson = new LessonBuilder().build();

        modelManager.addLesson(originalStudent, lesson);

        // Check if lesson is added to addressBook's lesson list
        assertTrue(modelManager.hasLesson(lesson));

        // Check if lesson is added to student's lesson list
        Student updatedStudent = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(originalStudent))
                .map(p -> (Student) p)
                .findFirst()
                .orElseThrow();

        assertTrue(updatedStudent.getLessonList().hasLesson(lesson));

        // Check if student is added to lesson's student list
        assertTrue(lesson.getStudent().equals(updatedStudent));

        // Check if filtered person list is updated to show all persons
        assertEquals(modelManager.getFilteredPersonList(), modelManager.getFilteredPersonList());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void updateFilteredPersonListByPaymentStatus() {
        Student alicePaid = new StudentBuilder(ALICE).build();
        alicePaid.setPaymentStatus(PaymentStatus.PAID);
        Student bensonPaid = new StudentBuilder(BENSON).build();
        bensonPaid.setPaymentStatus(PaymentStatus.PAID);

        Student carlOverdue = new StudentBuilder(CARL).build();
        carlOverdue.setPaymentStatus(PaymentStatus.OVERDUE);
        Student danielUnpaid = new StudentBuilder(DANIEL).build();
        danielUnpaid.setPaymentStatus(PaymentStatus.UNPAID);

        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(alicePaid)
                .withPerson(bensonPaid)
                .withPerson(carlOverdue)
                .withPerson(danielUnpaid).build();
        UserPrefs userPrefs = new UserPrefs();

        // different filteredList (by paymentStatus PAID)-> returns false
        modelManager.updateFilteredPersonListByPaymentStatus(
                new StudentMatchesPaymentStatusPredicate(PaymentStatus.UNPAID));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredList (by paymentStatus PAID)-> returns false
        modelManager.updateFilteredPersonListByPaymentStatus(
                new StudentMatchesPaymentStatusPredicate(PaymentStatus.PAID));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredList (by paymentStatus OVERDUE)-> returns false
        modelManager.updateFilteredPersonListByPaymentStatus(
                new StudentMatchesPaymentStatusPredicate(PaymentStatus.OVERDUE));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
    }

    @Test
    public void totals_initiallyZero() {
        assertEquals(0.0f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(0.0f, modelManager.totalUnpaidProperty().get(), 1e-6);
    }

    @Test
    public void totals_addPersonWithPayments_calculatesNewTotal() {
        assertEquals(0f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(0f, modelManager.totalUnpaidProperty().get(), 1e-6);

        // one student contributes total=300, unpaid=600
        Student s = new StudentStub("Alice", 300f, 600f);
        modelManager.addPerson(s);

        assertEquals(300f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(600f, modelManager.totalUnpaidProperty().get(), 1e-6);
    }

    @Test
    public void totals_editPersonPayment_calculatesNewTotal() {
        Student before = new StudentStub("Bob", 100f, 40f);
        modelManager.addPerson(before);
        assertEquals(100f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(40f, modelManager.totalUnpaidProperty().get(), 1e-6);

        Student after = new StudentStub("Bob", 200f, 0f);
        modelManager.setPerson(before, after);

        assertEquals(1, modelManager.getAddressBook().getPersonList().size());
        assertEquals(200f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(0f, modelManager.totalUnpaidProperty().get(), 1e-6);
    }

    @Test
    public void totals_deletePerson_goesToZero() {
        Student s = new StudentStub("Carol", 120f, 10f);
        modelManager.addPerson(s);
        assertEquals(120f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(10f, modelManager.totalUnpaidProperty().get(), 1e-6);

        modelManager.deletePerson(s);
        assertEquals(0f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(0f, modelManager.totalUnpaidProperty().get(), 1e-6);
    }

    @Test
    public void totals_changePayments_firesListeners() {
        float[] earned = new float[]{Float.NaN};
        float[] unpaid = new float[]{Float.NaN};
        ChangeListener<Number> earnListener = (obs, o, n) -> earned[0] = n.floatValue();
        ChangeListener<Number> unpaidListener = (obs, o, n) -> unpaid[0] = n.floatValue();

        modelManager.totalEarningsProperty().addListener(earnListener);
        modelManager.totalUnpaidProperty().addListener(unpaidListener);

        modelManager.addPerson(new StudentStub("Dan", 55.5f, 12.3f));

        assertEquals(55.5f, modelManager.totalEarningsProperty().get(), 1e-6);
        assertEquals(12.3f, modelManager.totalUnpaidProperty().get(), 1e-6);
        assertEquals(55.5f, earned[0], 1e-6);
        assertEquals(12.3f, unpaid[0], 1e-6);

        modelManager.totalEarningsProperty().removeListener(earnListener);
        modelManager.totalUnpaidProperty().removeListener(unpaidListener);
    }
}
