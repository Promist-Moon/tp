package tutman.tuiniverse.model.payment.exceptions;

/**
 * Represents a custom checked exception used within the seedu.address.model.lesson package.
 * A LessonException indicates that an error has occurred during
 * user command processing or task management, such as invalid input,
 * referencing a non-existent task, or providing a malformed date/time.
 */
public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}

