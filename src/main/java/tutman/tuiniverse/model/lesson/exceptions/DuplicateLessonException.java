package tutman.tuiniverse.model.lesson.exceptions;

/**
 * Signals that the operation will result in duplicate lessons (Lessons are considered duplicates if they have the
 * same day and their lesson times clash.).
 */
public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate persons");
    }
}
