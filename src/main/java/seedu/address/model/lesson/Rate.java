package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class Rate {

    public static final String MESSAGE_CONSTRAINTS = "Rates must be positive";

    private final float rate;

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


}
