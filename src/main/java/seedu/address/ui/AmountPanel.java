package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableFloatValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

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
    public AmountPanel(ObservableFloatValue totalEarningsObs, ObservableFloatValue totalUnpaidObs) {
        super(FXML);

        setTotals(totalEarningsObs, totalUnpaidObs);
    }

    /**
     * Sets both totals at once.
     *
     * @param totalEarningsObs
     * @param totalUnpaidObs
     */
    public void setTotals(ObservableFloatValue totalEarningsObs, ObservableFloatValue totalUnpaidObs) {
        setEarned(totalEarningsObs);
        setUnpaid(totalUnpaidObs);
    }

    public void setEarned(ObservableFloatValue totalEarningsObs) {
        totalEarned.setText(String.format("$%.2f", totalEarningsObs.get()));
        totalEarningsObs.addListener((obs, oldVal, newVal) -> {
            totalEarned.setText(String.format("$%.2f", newVal.floatValue()));
        });
    }

    public void setUnpaid(ObservableFloatValue totalUnpaidObs) {
        updateUnpaidLabel(totalUnpaidObs.get());
        totalUnpaidObs.addListener((obs, oldVal, newVal) -> {
            updateUnpaidLabel(newVal.floatValue());
        });
    }

    /**
     * Updates label text and style depending on amount.
     *
     * @param amount
     */
    private void updateUnpaidLabel(float amount) {
        totalUnpaid.setText(String.format("$%.2f", amount));

        totalUnpaid.getStyleClass().removeAll("unpaid", "paid"); // remove old state

        if (Math.abs(amount) < 0.005) { // treat 0.00 as paid
            totalUnpaid.getStyleClass().add("amount-unpaid-zero");
        } else {
            totalUnpaid.getStyleClass().add("amount-unpaid-high");
        }
    }
}
