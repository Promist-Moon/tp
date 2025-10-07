package seedu.address.model.lesson;

public enum Level {
    SECONDARY_ONE(1),
    SECONDARY_TWO(2),
    SECONDARY_THREE(3),
    SECONDARY_FOUR(4),
    SECONDARY_FIVE(5);

    public static final String MESSAGE_CONSTRAINTS = "Levels can only take these integer values: 1, 2, 3, 4, 5";

    private final int level;

    Level(int level) {
        this.level = level;
    }

    /**
     * Checks if the given integer corresponds to a valid level code.
     *
     * @param year the code to validate.
     * @return {@code true} if the year matches any defined level, otherwise {@code false}.
     */
    public static boolean isValidLevel(int year) {
        for (Level l : values()) {
            if (l.level == year) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts an integer level into the corresponding {@code Level} constant.
     *
     * @param year the integer level to convert.
     * @return the {@code Level} corresponding to the given code.
     * @throws IllegalArgumentException if the code does not match any defined level.
     */
    public static Level fromInt(int year) {
        for (Level l : values()) {
            if (l.level == year) {
                return l;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return String.format("%d", this.level);
    }
}
