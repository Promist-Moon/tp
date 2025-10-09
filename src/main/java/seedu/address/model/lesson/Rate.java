package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the hourly rate in $ of a Lesson.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate(String)}
 */
public class Rate {

    public static final String MESSAGE_CONSTRAINTS = "Rates must be positive";

    private final float rate;

    /**
     * Constructs a {@code Rate}.
     * 
     * @param str A valid rate
     */
    public Rate(String str) {
        checkArgument(isValidRate(str), MESSAGE_CONSTRAINTS);
        float rate = Float.parseFloat(str);
        this.rate = rate;
    }

    /**
     * Checks if the given float is a valid rate.
     */
    public static boolean isValidRate(String str) {
        float rate = Float.parseFloat(str);
        return rate >= 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", rate);
    }

    /*
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rate)) {
            return false;
        }

        Rate otherRate = (Rate) other;
        return rate == otherRate.rate;
    }

    @Override
    public int hashCode() {
        Float floatRate = rate;
        return floatRate.hashCode();
    }

     */

}
