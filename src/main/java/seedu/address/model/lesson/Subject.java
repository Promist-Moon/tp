package seedu.address.model.lesson;

/**
 * Represents a Lesson's Subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public enum Subject {
    MATH("Math"),
    ENGLISH("English"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    GEOGRAPHY("Geography"),
    HISTORY("History"),
    LITERATURE("Literature"),
    SOCIAL_STUDIES("Social Studies"),
    MOTHER_TONGUE("Mother Tongue");

    public static final String MESSAGE_CONSTRAINTS = "Subjects are not case-sensitive, "
            + "and can only take these values: Math, English, Physics, Chemistry, Biology, "
            + "Geography, History, Literature, Social Studies, Mother Tongue";

    private final String displayName;

    Subject(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Checks if the given string corresponds to a valid subject.
     *
     * @param name the name to validate.
     * @return {@code true} if {@code name} matches any defined subject (case-insensitive),
     *         otherwise {@code false}.
     */
    public static boolean isValidSubject(String name) {
        for (Subject s : values()) {
            if (s.displayName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a string into the corresponding {@code Subject} constant.
     *
     * @param name the subject name to convert.
     * @return the {@code Subject} constant that matches the name (case-insensitive).
     * @throws IllegalArgumentException if the name does not match any valid subject.
     */
    public static Subject fromString(String name) {
        for (Subject s : values()) {
            if (s.displayName.equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
