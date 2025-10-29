package tutman.tuiniverse.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutman.tuiniverse.commons.exceptions.IllegalValueException;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonList;
import tutman.tuiniverse.model.payment.Payment;
import tutman.tuiniverse.model.payment.PaymentList;
import tutman.tuiniverse.model.person.Email;
import tutman.tuiniverse.model.person.Name;
import tutman.tuiniverse.model.person.Person;
import tutman.tuiniverse.model.person.Phone;
import tutman.tuiniverse.model.person.student.Address;
import tutman.tuiniverse.model.person.student.Student;
import tutman.tuiniverse.model.person.student.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String type; // "student" or "parent" (null for legacy files)
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();
    private final List<JsonAdaptedPayment> payments = new ArrayList<>();
    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") String type, @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("lessonList") ArrayList<JsonAdaptedLesson> lessons,
                             @JsonProperty("paymentList") ArrayList<JsonAdaptedPayment> payments) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
        if (payments != null) {
            this.payments.addAll(payments);
        }
    }


    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        this.type = (source instanceof Student) ? "student" : "parent";
        if (source instanceof Student student) {
            address = student.getAddress().value;
            tags.addAll(student.getTags().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
            lessons.addAll(student.getLessonList().getLessons().stream()
                    .map(JsonAdaptedLesson::new)
                    .collect(Collectors.toList()));
            payments.addAll(student.getPayments().getPayments().stream()
                    .map(JsonAdaptedPayment::new)
                    .collect(Collectors.toList()));
        } else {
            // For now only Student is supported; non-student persons will fail during toModelType() validation
            address = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        //

        final ArrayList<Payment> personPayments = new ArrayList<>();
        for (JsonAdaptedPayment payment : payments) {
            personPayments.add(payment.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        // Determine subtype (back-compat: no type => student)
        String kind = (type == null) ? "student" : type.toLowerCase();
        switch (kind) {
        case "student": {
            if (address == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                        Address.class.getSimpleName()));
            }
            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
            }
            final Address modelAddress = new Address(address);
            final Set<Tag> modelTags = new HashSet<>(personTags);
            final PaymentList pl = new PaymentList(personPayments);
            final LessonList ll = new LessonList();
            final Student student = new Student(modelName, modelPhone, modelEmail, modelAddress, modelTags, ll, pl);

            for (JsonAdaptedLesson lesson : lessons) {
                Lesson l = lesson.toModelType();
                l.addStudent(student);
                student.getLessonList().addLesson(l);
            }

            return student;
        }
        case "parent": {
            // TODO: When Parent is implemented, validate Parent-specific fields here and return new Parent(...)
            throw new IllegalValueException("Parent deserialization not supported yet.");
        }
        default:
            throw new IllegalValueException("Unknown person type: " + kind);
        }
    }

}
