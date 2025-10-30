package tutman.tuiniverse.model.lesson;

import java.util.Comparator;

/**
 * Represents a Comparator that compare two lesson
 * based on the start time of lessonTime.
 */
public class LessonTimeComparator implements Comparator<Lesson> {

    @Override
    public int compare(Lesson o1, Lesson o2) {
        return o1.getLessonTime().compareTo(o2.getLessonTime());
    }
}
