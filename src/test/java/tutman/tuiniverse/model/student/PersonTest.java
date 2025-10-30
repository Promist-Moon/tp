package tutman.tuiniverse.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import tutman.tuiniverse.testutil.StudentBuilder;


public class PersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentBuilder().withName(null).build());
        assertThrows(NullPointerException.class, () -> new StudentBuilder().withPhone(null).build());
        assertThrows(NullPointerException.class, () -> new StudentBuilder().withEmail(null).build());
    }

    @Test
    public void equals() {
        Student amy = new StudentBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Student amyCopy = new StudentBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Student bob = new StudentBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .build();

        assertTrue(amy.equals(amy));
        assertTrue(amy.equals(amyCopy));
        assertFalse(amy.equals(null));
        assertFalse(amy.equals(bob));
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Student amy = new StudentBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Student amyCopy = new StudentBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        assertEquals(amy.hashCode(), amyCopy.hashCode());
    }

    @Test
    public void isSamePerson() {
        Student amy = new StudentBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Student amyCopyDifferentEmail = new StudentBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_BOB) // purposely different
                .build();
        Student bob = new StudentBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .build();

        assertTrue(amy.isSamePerson(amy));
        assertFalse(amy.isSamePerson(null));
        assertTrue(amy.isSamePerson(amyCopyDifferentEmail));
        assertFalse(amy.isSamePerson(bob));
    }

    @Test
    public void toString_containsCanonicalNameAndFields() {
        Student amy = new StudentBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        String toString = amy.toString();
        assertTrue(toString.contains(VALID_NAME_AMY));
        assertTrue(toString.contains(VALID_PHONE_AMY));
        assertTrue(toString.contains(VALID_EMAIL_AMY));
    }

    @Test
    public void toDisplayString_containsLabelsAndFields() {
        Student amy = new StudentBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        String display = amy.toDisplayString();
        assertTrue(display.contains("Name:"));
        assertTrue(display.contains("Phone:"));
        assertTrue(display.contains("Email:"));
        assertTrue(display.contains(VALID_NAME_AMY));
        assertTrue(display.contains(VALID_PHONE_AMY));
        assertTrue(display.contains(VALID_EMAIL_AMY));
    }
}
