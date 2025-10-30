package tutman.tuiniverse.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.value.ObservableFloatValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import tutman.tuiniverse.commons.core.GuiSettings;
import tutman.tuiniverse.commons.core.LogsCenter;
import tutman.tuiniverse.logic.commands.Command;
import tutman.tuiniverse.logic.commands.CommandResult;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.logic.parser.AddressBookParser;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ReadOnlyAddressBook;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.TodaysLessonPredicate;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.model.util.DateTimeUtil;
import tutman.tuiniverse.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model}, {@code Storage} and
     * {@code Clock}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);


        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return model.getFilteredLessonList();
    }
    @Override
    public SortedList<Lesson> getTodayLessonList() {
        //by default, we show the lesson for today only
        model.updateFilteredLessonList(new TodaysLessonPredicate(DateTimeUtil.currentDay()));
        return model.getSortedFilteredLessons();
    }

    @Override
    public ObservableFloatValue totalEarningsProperty() {
        return model.totalEarningsProperty();
    }

    @Override
    public ObservableFloatValue totalUnpaidProperty() {
        return model.totalUnpaidProperty();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
