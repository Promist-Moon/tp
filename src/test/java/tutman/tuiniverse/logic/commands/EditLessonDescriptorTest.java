package tutman.tuiniverse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_DAY_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_DAY_LESSON2;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_END_TIME_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_END_TIME_LESSON2;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_LEVEL_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_LEVEL_LESSON2;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_RATE_2;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_START_TIME_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_SUBJECT_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_SUBJECT_LESSON2;

import org.junit.jupiter.api.Test;

import tutman.tuiniverse.logic.commands.EditLessonCommand.EditLessonDescriptor;
import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Subject;
import tutman.tuiniverse.testutil.EditLessonDescriptorBuilder;

public class EditLessonDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(VALID_DAY_LESSON1,
                VALID_START_TIME_LESSON1, VALID_END_TIME_LESSON1,
                VALID_LEVEL_LESSON1, VALID_RATE_2, VALID_SUBJECT_LESSON1);
        assertTrue(descriptorWithSameValues.equals(new EditLessonDescriptor(VALID_DAY_LESSON1, VALID_START_TIME_LESSON1,
                VALID_END_TIME_LESSON1, VALID_LEVEL_LESSON1, VALID_RATE_2, VALID_SUBJECT_LESSON1)));

        // same object -> returns true
        assertTrue(descriptorWithSameValues.equals(descriptorWithSameValues));

        // null -> returns false
        assertFalse(descriptorWithSameValues.equals(null));

        // different types -> returns false
        assertFalse(descriptorWithSameValues.equals(5));

        // different day -> returns false
        EditLessonDescriptor editedLesson = new EditLessonDescriptorBuilder()
                .withDay(new Day(Integer.valueOf(VALID_DAY_LESSON2))).build();
        assertFalse(descriptorWithSameValues.equals(editedLesson));

        // different start time -> returns false
        editedLesson = new EditLessonDescriptorBuilder()
                .withLessonTime(LessonTime.ofLessonTime(VALID_START_TIME_LESSON1, VALID_END_TIME_LESSON2))
                .build();
        assertFalse(descriptorWithSameValues.equals(editedLesson));

        // different level -> returns false
        editedLesson = new EditLessonDescriptorBuilder()
                .withLevel(Level.fromString(VALID_LEVEL_LESSON2))
                .build();
        assertFalse(descriptorWithSameValues.equals(editedLesson));

        // different subject -> returns false
        editedLesson = new EditLessonDescriptorBuilder()
                .withSubject(Subject.fromString(VALID_SUBJECT_LESSON2))
                .build();
        assertFalse(descriptorWithSameValues.equals(editedLesson));
    }

    @Test
    public void toStringMethod() {
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        String expected = EditLessonDescriptor.class.getCanonicalName() + "{day="
                + editLessonDescriptor.getDay().orElse(null) + ", lessonTime="
                + editLessonDescriptor.getLessonTime().orElse(null) + ", level="
                + editLessonDescriptor.getLevel().orElse(null) + ", rate="
                + editLessonDescriptor.getRate().orElse(null) + ", subject="
                + editLessonDescriptor.getSubject().orElse(null) + "}";
        assertEquals(expected, editLessonDescriptor.toString());
    }
}
