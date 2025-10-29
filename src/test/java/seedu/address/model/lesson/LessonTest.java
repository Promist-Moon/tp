package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.Y3_MATH;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.student.Address;
import seedu.address.model.person.student.Student;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.StudentBuilder;

public class LessonTest {

    @Test
    public void constructor_validFields_success() {
        Lesson lesson = new LessonBuilder()
                .withSubject("Math")
                .withLevel("2")
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .withRate("40")
                .build();

        assertEquals(Subject.MATH, lesson.getSubject());
        assertEquals(Level.SECONDARY_TWO, lesson.getLevel());
        assertEquals("MONDAY", lesson.getDay().toString());
        assertEquals(LocalTime.of(9, 0), lesson.getStartTime());
        assertEquals(LocalTime.of(10, 30), lesson.getEndTime());
        assertEquals(90, lesson.getDuration().toMinutes());
        assertEquals("40.00", lesson.getRate().toString());
        assertNull(lesson.getStudent());
        assertNull(lesson.getAddress());
    }

    @Test
    public void constructor_nullFields_throwsNullPointer() {
        Day day = new Day(1);
        LessonTime time = LessonTime.ofLessonTime("10:00", "11:30");
        Rate rate = new Rate("30");

        assertThrows(NullPointerException.class, () ->
                new Lesson(null, Level.SECONDARY_ONE, day, time, rate));
        assertThrows(NullPointerException.class, () ->
                new Lesson(Subject.MATH, null, day, time, rate));
        assertThrows(NullPointerException.class, () ->
                new Lesson(Subject.MATH, Level.SECONDARY_ONE, null, time, rate));
        assertThrows(NullPointerException.class, () ->
                new Lesson(Subject.MATH, Level.SECONDARY_ONE, day, null, rate));
        assertThrows(NullPointerException.class, () ->
                new Lesson(Subject.MATH, Level.SECONDARY_ONE, day, time, null));
    }

    @Test
    public void addStudent_setsStudentAndAddress() {
        Lesson lesson = new LessonBuilder().build();

        Student s = new StudentBuilder()
                .withName("Alice")
                .withAddress("123, Clementi Ave 3")
                .withEmail("alice@example.com")
                .withPhone("91234567")
                .build();

        Address expected = s.getAddress();
        lesson.addStudent(s);

        assertSame(s, lesson.getStudent());
        assertEquals(expected, lesson.getAddress());
    }

    @Test
    public void toString_containsKeyInfo() {
        Lesson lesson = new LessonBuilder()
                .withSubject("Biology")
                .withLevel("5")
                .withDay("4")
                .withLessonTime("14:00", "15:30")
                .withRate("50")
                .build();

        String str = lesson.toString();
        assertTrue(str.contains("Biology"));
        assertTrue(str.contains("5"));
        assertTrue(str.contains("THURSDAY"));
        assertTrue(str.contains("14:00 - 15:30"));
        assertTrue(str.contains("50.00"));
    }

    @Test
    public void hasTimeClash_sameDayAndOverlappingTimes_returnsTrue() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:30", "11:00")
                .build();

        assertTrue(l1.hasTimeClash(l2));
    }

    @Test
    public void hasTimeClash_sameDayButBackToBack_returnsFalse() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("10:30", "12:00")
                .build();

        assertFalse(l1.hasTimeClash(l2));
    }

    @Test
    public void hasTimeClash_differentDay_returnsFalse() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("2")
                .withLessonTime("09:00", "10:30")
                .build();

        assertFalse(l1.hasTimeClash(l2));
    }

    @Test
    public void hasTimeClash_nullAndDifferentType_returnsFalse() {
        Lesson l = new LessonBuilder().build();
        assertFalse(l.hasTimeClash(null));
    }

    @Test
    @DisplayName("equals method returns true for same instance")
    public void equals_sameInstance_returnsTrue() {
        Lesson l1 = Y3_MATH;
        Lesson l2 = Y3_MATH;
        assertTrue(l1.equals(l2));
    }

    @Test
    @DisplayName("equals method returns true for different but equal instance")
    public void equals_differentButEqualInstance_returnsTrue() {
        Lesson l1 = Y3_MATH;
        Lesson l2 = new LessonBuilder().withSubject("Math").withLevel("3").withDay("3")
                .withLessonTime("09:00", "11:00").withStudent(ALICE).build();
        assertTrue(l1.equals(l2));
    }

    @Test
    public void equals_sameDayButBackToBack_returnsFalse() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("10:30", "12:00")
                .build();

        assertNotEquals(l1, l2);
    }

    @Test
    public void equals_differentDay_returnsFalse() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("2")
                .withLessonTime("09:00", "10:30")
                .build();

        assertFalse(l1.equals(l2));
    }

    @Test
    public void equals_nullAndDifferentType_returnsFalse() {
        Lesson l = new LessonBuilder().build();
        assertFalse(l.equals(null));
        assertFalse(l.equals("Not a Lesson"));
    }

    @Test
    public void hashcode_differsForDifferentTimesOrDays() {
        Lesson l1 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("09:00", "10:30")
                .build();

        Lesson l2 = new LessonBuilder()
                .withDay("1")
                .withLessonTime("10:30", "12:00")
                .build();

        Lesson l3 = new LessonBuilder()
                .withDay("2")
                .withLessonTime("09:00", "10:30")
                .build();

        assertNotEquals(l1.hashCode(), l2.hashCode());
        assertNotEquals(l1.hashCode(), l3.hashCode());
    }
}

