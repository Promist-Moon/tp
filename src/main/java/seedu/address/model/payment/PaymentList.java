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

    /**
     * Constructs a new lesson list by creating an empty array list.
     */
    public PaymentList() {
        this.payments = new ArrayList<>();
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

}