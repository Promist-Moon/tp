package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class Rate {

    public static final String MESSAGE_CONSTRAINTS = "Rates must be positive";

    private final float rate;

    private Rate(float rate) {
        checkArgument(isValidRate(rate), MESSAGE_CONSTRAINTS);
        this.rate = rate;
    }

    /**
     * Checks if the given float is a valid rate.
     */
    public static boolean isValidRate(float rate) {
        return rate >= 0;
    }


}
