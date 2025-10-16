package seedu.address.model.payment;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.model.payment.exceptions.PaymentException;

/**
 * Represents a list of payments.
 * A PaymentList represents the payments one student takes under one tutor.
 * It supports adding, retrieving, deleting, and printing payments.
 */
public class PaymentList {
    private final ArrayList<Payment> payments;
    private YearMonth earliestUnpaidYearmonth;

    private Status status;

    /**
     * Constructs a new payment list by creating an empty array list.
     */
    public PaymentList() {
        this.payments = new ArrayList<>();

        earliestUnpaidYearmonth = null;
        setPaymentStatus(Status.PAID);
    }

    /**
     * Constructs a new payment list by adding a payment.
     * Assumes added payment is unpaid.
     */
    public PaymentList(Payment payment) {
        this.payments = new ArrayList<>();
        this.payments.add(payment);

        updateStatus();
        setEarliestUnpaidYearmonth(findAndSetEarliestUnpaidYearMonth());
    }

    /**
     * Constructs a new payment list by adding an arraylist of payments.
     */
    public PaymentList(ArrayList<Payment> payments) {
        this.payments = payments;
        sortByYearMonth();

        updateStatus();
        setEarliestUnpaidYearmonth(findAndSetEarliestUnpaidYearMonth());
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

        if (status == Status.PAID && !payment.isPaid()) {
            setPaymentStatus(Status.UNPAID);
            setEarliestUnpaidYearmonth(payment.getYearMonth());
        } else if (status == Status.UNPAID && !payment.isPaid()) {
            setPaymentStatus(Status.OVERDUE);
        }
        sortByYearMonth();
    }

    /**
     * Find unpaid payments in a payment list.
     * @return
     */
    public ArrayList<Payment> findUnpaids() {
        ArrayList<Payment> unpaidList = new ArrayList<>();
        for (Payment p : payments) {
            if (!p.isPaid()) {
                unpaidList.add(p);
            }
        }

        return unpaidList;
    }

    /**
     * Updates payment status according to the size of unpaid list.
     */
    public void updateStatus() {
        ArrayList<Payment> unpaidList = findUnpaids();
        if (unpaidList.isEmpty()) {
            setPaymentStatus(Status.PAID);
        } else if (unpaidList.size() == 1) {
            setPaymentStatus(Status.UNPAID);
        } else {
            setPaymentStatus(Status.OVERDUE);
        }
    }

    /**
     * Returns the earlier (max YearMonth) unpaid payment's month, or null if none.
     * Manual method.
     */
    public YearMonth findAndSetEarliestUnpaidYearMonth() {
        YearMonth earliest = null;
        for (Payment p : payments) {
            if (!p.isPaid()) {
                YearMonth ym = p.getYearMonth();
                if (earliest == null || ym.isBefore(earliest)) {
                    earliest = ym;
                }
            }
        }
        if (earliest != earliestUnpaidYearmonth) {
            setEarliestUnpaidYearmonth(earliest);
        }
        return earliest;
    }

    /**
     * Mark all outstanding payments as paid by iterating through
     * list of unpaid payments and marking them as paid.
     */
    public void markAllPaid() throws PaymentException {
        ArrayList<Payment> unpaidList = findUnpaids();
        if (unpaidList.isEmpty()) {
            throw new PaymentException("All lessons paid for already.");
        }

        for (Payment p : unpaidList) {
            p.markPaid();
        }

        setPaymentStatus(Status.PAID);
        setEarliestUnpaidYearmonth(null);

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

    private void sortByYearMonth() {
        payments.sort(Comparator.comparing(Payment::getYearMonth));
    }

    private void setPaymentStatus(Status status) {
        this.status = status;
    }

    private void setEarliestUnpaidYearmonth(YearMonth month) {
        this.earliestUnpaidYearmonth = month;
    }

}
