package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import tutman.tuiniverse.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The title of lesson list panel should show student's name. */
    private final boolean showingName;

    /** The student's name to show, if needed */
    private final String studentName;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean showingName, String studentName) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showingName = showingName;
        this.studentName = studentName;
    }

    /**
     * Constructs a {@code CommandResult} with specified feedbackToUser,
     * showHelp and exit, with showingName default to false and
     * studentName set to null.
     * @param feedbackToUser
     * @param showHelp
     * @param exit
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showingName = false;
        this.studentName = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields
     * when there's a need to reset lesson list panel title.
     */
    public CommandResult(String feedbackToUser, boolean showingName, String name) {
        this(feedbackToUser, false, false, showingName, name);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowingName() {
        return showingName;
    }

    public String getStudentName() {
        return studentName;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
