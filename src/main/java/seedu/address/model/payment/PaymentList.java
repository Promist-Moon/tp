package seedu.address.model.payment;

import java.time.YearMonth;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Represents a list of payments.
 * A PaymentList represents the payments one student takes under one tutor.
 * It supports adding, retrieving, deleting, and printing payments.
 */
public class PaymentList {

    private final NavigableMap<YearMonth, Payment> byMonth = new TreeMap<>();

    public boolean add(Payment payment) {
        Objects.requireNonNull(payment);
        YearMonth m = payment.getYearMonth();
        // return false if duplicate month (don’t overwrite silently)
        return byMonth.putIfAbsent(m, payment) == null;
    }

    /**
     * Returns the Payment corresponding to the requested YearMonth.
     *
     * @param month YearMonth month of payment
     * @return Payment corresponding to month
     */
    public Optional<Payment> getPayment(YearMonth month) {
        return Optional.ofNullable(byMonth.get(month));
    }

    /**
     * Returns the latest Payment in the paymentList
     * @return payment in paymentList corresponding to current date
     */
    public Optional<Payment> getLatestPayment() {
        var e = byMonth.lastEntry();
        return Optional.ofNullable(e == null ? null : e.getValue());
    }

    public boolean remove(YearMonth month) {
        return byMonth.remove(month) != null;
    }

    /**
     * Gets latest unpaid or overdue Payment in payment list
     * @return Payment object that has false isPaid
     */
    public Optional<Payment> getLatestUnpaidOrOverdue() {
        return byMonth.descendingMap().values().stream()
                .filter(p -> !p.isPaid())
                .findFirst();
    }

    public int getSize() {
        return byMonth.size();
    }

    public boolean isEmpty() {
        return byMonth.isEmpty();
    }
}
