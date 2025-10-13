package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonBuilder().withSubject(null).build());
        assertThrows(NullPointerException.class, () -> new LessonBuilder().withLevel(null).build());
        assertThrows(NullPointerException.class, () -> new LessonBuilder().withDay(null).build());
        assertThrows(NullPointerException.class, () -> new LessonBuilder().withLessonTime(null, null).build());
        assertThrows(NullPointerException.class, () -> new LessonBuilder().withRate(null).build());
    }

}
