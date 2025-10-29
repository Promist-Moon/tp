package tutman.tuiniverse.model.person;

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

import tutman.tuiniverse.testutil.PersonBuilder;


public class PersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonBuilder().withName(null).build());
        assertThrows(NullPointerException.class, () -> new PersonBuilder().withPhone(null).build());
        assertThrows(NullPointerException.class, () -> new PersonBuilder().withEmail(null).build());
    }

    @Test
    public void equals() {
        Person amy = new PersonBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Person amyCopy = new PersonBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Person bob = new PersonBuilder()
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
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Person amyCopy = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        assertEquals(amy.hashCode(), amyCopy.hashCode());
    }

    @Test
    public void isSamePerson() {
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        Person amyCopyDifferentEmail = new PersonBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_BOB) // purposely different
                .build();
        Person bob = new PersonBuilder().withName(VALID_NAME_BOB)
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
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY)
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
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY)
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
