package tutman.tuiniverse.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableFloatValue;
import javafx.collections.ObservableList;
import tutman.tuiniverse.commons.core.GuiSettings;
import tutman.tuiniverse.logic.commands.CommandResult;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.ReadOnlyAddressBook;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of lessons */
    ObservableList<Lesson> getFilteredLessonList();

    /** Returns an unmodifiable view of the filtered list of today's lessons */
    ObservableList<Lesson> getTodayLessonList();

    /**
     * Returns a float value of the total earned from all students for the month.
     * This should be the total of totalAmounts in all student's payment lists.
     */
    ObservableFloatValue totalEarningsProperty();

    /**
     * Returns a float value of the total unpaid from all students.
     * This should be the total of unpaidAmounts in all student's payment lists.
     */
    ObservableFloatValue totalUnpaidProperty();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
