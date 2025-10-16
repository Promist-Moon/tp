package seedu.address.model.payment;

import java.time.YearMonth;
import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.util.DateTimeUtil;

/**
 * Handles the monthly payment rollover logic when the app is opened
 * after one or more months have passed since the last session
 */
public class MonthlyRollover {
    private final Model model;

    public MonthlyRollover(Model model) {
        Objects.requireNonNull(model);
        this.model = model;
    }

    /**
     * Checks how many months have elapsed since the app was last opened,
     * and performs a payment rollover for each elapsed month.
     *
     * @param lastOpened the {@code YearMonth} when the app was last opened
     * @param now the current {@code YearMonth}
     */
    public void compute(YearMonth lastOpened, YearMonth now) {
        Objects.requireNonNull(lastOpened, "lastOpened cannot be null");
        Objects.requireNonNull(now, "now cannot be null");

        long monthsElapsed = DateTimeUtil.monthsBetweenInclusive(lastOpened, now);
        if (monthsElapsed <= 0) {
            return; // No rollover needed
        }

        for (int i = 0; i < monthsElapsed; i++) {
            YearMonth rolloverMonth = lastOpened.plusMonths(i + 1);
            rolloverForMonth(rolloverMonth);
        }
    }

    /**
     * Applies rollover logic for a specific month.
     *
     * @param month the {@code YearMonth} to perform rollover for
     */
    private void rolloverForMonth(YearMonth month) {
        // TODO: Implement how payments roll over month-by-month.
        // Example: iterate through all students, duplicate unpaid payments,
        // mark overdue payments, etc.
    }
}

