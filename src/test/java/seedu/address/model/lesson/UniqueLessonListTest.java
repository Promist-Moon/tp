package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.Y1_PHYSICS;
import static seedu.address.testutil.TypicalLessons.Y3_MATH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.person.student.Address;
import seedu.address.testutil.LessonBuilder;

public class UniqueLessonListTest {
    private UniqueLessonList uniqueLessonList;

    @BeforeEach
    public void setUp() {
        uniqueLessonList = new UniqueLessonList();
    }

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(uniqueLessonList.contains(Y3_MATH));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        uniqueLessonList.add(Y3_MATH);
        assertTrue(uniqueLessonList.contains(Y3_MATH));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueLessonList.add(Y3_MATH);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.add(Y3_MATH));
    }

    @Test
    public void setLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLesson(null, Y3_MATH));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        uniqueLessonList.add(Y3_MATH);
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLesson(Y3_MATH, null));
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.setLesson(Y3_MATH, Y1_PHYSICS));
    }

    @Test
    public void setLesson_editedLessonIsSameLesson_success() {
        uniqueLessonList.add(Y3_MATH);
        uniqueLessonList.setLesson(Y3_MATH, Y3_MATH);
        assertEquals(Collections.singletonList(Y3_MATH), uniqueLessonList.asUnmodifiableObservableList());
    }

    @Test
    public void setLesson_editedLessonHasDifferentIdentity_success() {
        uniqueLessonList.add(Y3_MATH);
        uniqueLessonList.setLesson(Y3_MATH, Y1_PHYSICS);
        assertFalse(uniqueLessonList.contains(Y3_MATH));
        assertTrue(uniqueLessonList.contains(Y1_PHYSICS));
    }

    @Test
    public void setLesson_editedLessonHasNonUniqueIdentity_throwsDuplicateLessonException() {
        uniqueLessonList.add(Y3_MATH);
        uniqueLessonList.add(Y1_PHYSICS);
        // Attempt to update MATH to SCIENCE, which already exists
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLesson(Y3_MATH, Y1_PHYSICS));
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.remove(null));
    }

    @Test
    public void remove_lessonDoesNotExist_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(Y3_MATH));
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        uniqueLessonList.add(Y3_MATH);
        uniqueLessonList.remove(Y3_MATH);
        assertFalse(uniqueLessonList.contains(Y3_MATH));
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((UniqueLessonList) null));
    }

    @Test
    public void setLessons_uniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        uniqueLessonList.add(Y3_MATH);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(Y1_PHYSICS);
        uniqueLessonList.setLessons(expectedUniqueLessonList);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        uniqueLessonList.add(Y3_MATH);
        List<Lesson> lessonList = Collections.singletonList(Y1_PHYSICS);
        uniqueLessonList.setLessons(lessonList);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(Y1_PHYSICS);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsDuplicateLessonException() {
        List<Lesson> listWithDuplicateLessons = Arrays.asList(Y3_MATH, Y3_MATH);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLessons(listWithDuplicateLessons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueLessonList.add(Y3_MATH);
        assertThrows(UnsupportedOperationException.class, () -> uniqueLessonList.asUnmodifiableObservableList().remove(0));
    }
}
