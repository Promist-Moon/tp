package seedu.address.model.lesson;

import java.util.Comparator;

public class LessonTimeComparator implements Comparator<Lesson> {

    @Override
    public int compare(Lesson o1, Lesson o2) {
        return o1.getLessonTime().compareTo(o2.getLessonTime());
    }
}
