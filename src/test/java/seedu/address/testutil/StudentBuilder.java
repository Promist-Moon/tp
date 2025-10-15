package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.LessonTime;
import seedu.address.model.lesson.Level;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Rate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.Address;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final Lesson DEFAULT_LESSON = new Lesson(
            Subject.fromString("Math"),
            Level.fromString("1"),
            new Day("1"),
            LessonTime.ofLessonTime("10:00", "12:00"),
            new Rate("40")
    );

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private LessonList ll = new LessonList();
    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        ll.addLesson(DEFAULT_LESSON);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code personToCopy}.
     */
    public StudentBuilder(Student personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        ll = personToCopy.getLessonList();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Lessonlist} of the {@code Person} that we are building.
     */
    public StudentBuilder withLessonList(LessonList ll) {
        this.ll = ll;
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, address, tags, ll);
    }

}
