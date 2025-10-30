package tutman.tuiniverse.model.lesson;

import java.util.List;
import java.util.function.Predicate;

import tutman.tuiniverse.commons.util.StringUtil;
import tutman.tuiniverse.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Lesson}'s {@code Subject} matches any of the keywords given.
 */
public class LessonSubjectContainsKeywordsPredicate implements Predicate<Lesson> {
    private final List<String> keywords;

    public LessonSubjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Lesson lesson) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(lesson.getSubject().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonSubjectContainsKeywordsPredicate)) {
            return false;
        }

        LessonSubjectContainsKeywordsPredicate otherPredicate = (LessonSubjectContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
