package seedu.address.model.payment;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.ArrayList;

import seedu.address.model.payment.exceptions.PaymentException;

/**
 * Represents a list of payments.
 * A PaymentList represents the payments one student takes under one tutor.
 * It supports adding, retrieving, deleting, and printing payments.
 */
public class PaymentList {
    private final ArrayList<Payment> payments;
    private YearMonth latestUnpaidYearmonth;

    private ArrayList<Payment> unpaidList;

    private PaymentStatus status;

    /**
     * Constructs a new payment list by creating an empty array list.
     */
    public PaymentList() {
        this.payments = new ArrayList<>();
        this.unpaidList = new ArrayList<>();
    }

    /**
     * Constructs a new payment list by adding a payment.
     * Assumes added payment is unpaid.
     */
    public PaymentList(Payment payment) {
        this.payments = new ArrayList<>();
        this.payments.add(payment);

        // implement later what latestUnpaidYearmonth is if all paid
        if (payment.isPaid()) {
            status = PaymentStatus.PAID;
        } else {
            // overdue logic later
            status = PaymentStatus.UNPAID;
        }

        this.unpaidList = new ArrayList<>();
    }

    /**
     * Constructs a new payment list by adding an arraylist of payments.
     */
    public PaymentList(ArrayList<Payment> payments) {
        this.payments = payments;

        this.unpaidList = new ArrayList<>();
    }

    public int getSize() {
        return payments.size();
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    /**
     * Returns payment by index.
     *
     * @param indexOneBased index of payment object, one based.
     * @return a Payment object
     * @throws PaymentException when no payment corresponding to yearmonth found.
     */
    public Payment getPaymentByIndex(int indexOneBased) throws PaymentException {
        int idx = indexOneBased - 1;
        if (idx < 0 || idx >= payments.size()) {
            throw new PaymentException("No such payment: " + indexOneBased);
        }
        return payments.get(idx);
    }

    /**
     * Returns payment by month.
     *
     * @param month a YearMonth object
     * @return a Payment object corresponding to YearMonth
     * @throws PaymentException when no payment corresponding to yearmonth found.
     */
    public Payment getPaymentByMonth(YearMonth month) throws PaymentException {
        requireNonNull(month, "Month must not be null.");

        for (Payment p : payments) {
            if (month.equals(p.getYearMonth())) {
                return p;
            }
        }

        throw new PaymentException("No payment found for " + month);
    }

    /**
     * Adds a new payment to the payment list.
     */
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    /**
     * Find unpaid payments in a payment list.
     * @return
     */
    public ArrayList<Payment> findUnpaids() {
        for (Payment p : payments) {
            if (!p.isPaid()) {
                unpaidList.add(p);
            }
        }

        return this.unpaidList;
    }

    /**
     * Updates payment status according to the size of unpaid list.
     */
    public void updateStatus() {
        if (unpaidList.isEmpty()) {
            setPaymentStatus(PaymentStatus.PAID);
        } else if (unpaidList.size() == 1) {
            setPaymentStatus(PaymentStatus.UNPAID);
        } else {
            setPaymentStatus(PaymentStatus.OVERDUE);
        }
    }

    /**
     * Returns list of unpaid payments
     * @return ArrayList of unpaid items
     */
    public ArrayList<Payment> getUnpaidList() {
        return this.unpaidList;
    }

    /**
     * Clears unpaid.
     */
    public void clearUnpaids() {
        this.unpaidList = new ArrayList<>();
    }

    /**
     * Returns a string representation of the payment list, with
     * each lesson prefixed by its index.
     *
     * @return the formatted string representation of the payment list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < payments.size(); i++) {
            sb.append(i + 1).append(". ").append(payments.get(i)).append("\n");
        }
        return sb.toString();
    }

    private void setPaymentStatus(PaymentStatus status) {
        this.status = status;
    }

    private void setLatestUnpaidYearmonth(YearMonth month) {
        this.latestUnpaidYearmonth = month;
    }

}