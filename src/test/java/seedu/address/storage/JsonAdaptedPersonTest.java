package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Address;
import seedu.address.testutil.StudentBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }


    @Test
    public void constructor_nonStudent_setsAddressNull() {
        // Create a simple non-Student Person using PersonBuilder (DummyPerson)
        Student dummyPerson = new StudentBuilder().build();

        // Build a JsonAdaptedPerson from this dummy
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(dummyPerson);

        // Since non-Student persons have no address, the address field should be null
        // When we call toModelType(), it should throw because address == null for student subtype
        assertThrows(IllegalValueException.class, jsonPerson::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("student", INVALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson("student", null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("student", VALID_NAME, INVALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson("student", VALID_NAME, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("student", VALID_NAME, VALID_PHONE,
                        INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson("student", VALID_NAME, VALID_PHONE,
                null, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("student", VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson("student", VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_TAGS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("student", VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, invalidTags, null, null);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("teacher", VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = "Unknown person type: teacher";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_parentType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson("parent", VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        String expectedMessage = "Parent deserialization not supported yet.";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTypeBackCompat_returnsStudent() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null, null);
        Student result = person.toModelType();
        assertEquals(Student.class, result.getClass());
    }

}
