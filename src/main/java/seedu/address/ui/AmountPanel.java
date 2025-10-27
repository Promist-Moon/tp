package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.payment.TotalAmount;
import seedu.address.model.payment.UnpaidAmount;

/**
 * Horizontal panel showing total to be earned and total unpaid.
 */
public class AmountPanel extends UiPart<Region> {
    private static final String FXML = "AmountPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AmountPanel.class);

    @FXML
    private Label totalEarned;
    @FXML
    private Label totalUnpaid;

    /**
     * Creates a {@code AmountPanel} with the given {@code TotalAmount} and {@code UnpaidAmount}.
     */
    public AmountPanel(TotalAmount totalEarned, UnpaidAmount totalUnpaid) {
        super(FXML);
        setTotals(totalEarned, totalUnpaid);
    }

    /**
     * Sets both totals at once.
     *
     * @param totalEarned
     * @param totalUnpaid
     */
    public void setTotals(TotalAmount totalEarned, UnpaidAmount totalUnpaid) {
        setEarned(totalEarned);
        setUnpaid(totalUnpaid);
    }

    public void setEarned(TotalAmount totalEarned) {
        this.totalEarned.setText("$" + totalEarned);
    }

    public void setUnpaid(UnpaidAmount totalUnpaid) {
        this.totalUnpaid.setText("$" + totalUnpaid);

        if (totalUnpaid.isZero()) {
            this.totalUnpaid.getStyleClass().add("amount-unpaid-zero");
        } else {
            this.totalUnpaid.getStyleClass().add("amount-unpaid-real");
        }
    }
}
