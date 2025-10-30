package tutman.tuiniverse.model.student;

import java.util.function.Predicate;

import tutman.tuiniverse.commons.util.ToStringBuilder;

/**
 * Tests that a {@code student}'s {@code Name} matches any of the keywords given.
 */
public class StudentMatchesPaymentStatusPredicate implements Predicate<Student> {
    private final PaymentStatus paymentStatus;

    public StudentMatchesPaymentStatusPredicate(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean test(Student student) {
        return student.getPaymentStatus().equals(paymentStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentMatchesPaymentStatusPredicate)) {
            return false;
        }

        StudentMatchesPaymentStatusPredicate otherNameContainsKeywordsPredicate =
                (StudentMatchesPaymentStatusPredicate) other;
        return paymentStatus.equals(otherNameContainsKeywordsPredicate.paymentStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("payment status", paymentStatus).toString();
    }
}
