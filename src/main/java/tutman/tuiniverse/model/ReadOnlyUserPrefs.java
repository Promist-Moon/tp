package tutman.tuiniverse.model;

import java.nio.file.Path;
import java.time.YearMonth;

import tutman.tuiniverse.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    YearMonth getLastOpened();

}
