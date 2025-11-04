package tutman.tuiniverse.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static tutman.tuiniverse.testutil.Assert.assertThrows;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;
import tutman.tuiniverse.model.student.Address;
import tutman.tuiniverse.model.student.Email;
import tutman.tuiniverse.model.student.Name;
import tutman.tuiniverse.model.student.Phone;
import tutman.tuiniverse.model.student.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    void parseSubject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSubject(null));
    }

    @Test
    void parseSubject_invalid_throwsParseException() {
        // Adjust "GARBAGE" if your validator allows anything else
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject("GARBAGE"));
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseSubject("   "));
    }

    @Test
    void parseSubject_valid_trimsAndParses() throws Exception {
        Subject expected = Subject.fromString("English");
        assertEquals(expected, ParserUtil.parseSubject("English"));
        assertEquals(expected, ParserUtil.parseSubject("  English  "));
    }

    @Test
    void parseLevel_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLevel(null));
    }

    @Test
    void parseLevel_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel("6"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel("X"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLevel(""));
    }

    @Test
    void parseLevel_valid_trimsAndParses() throws Exception {
        Level expected = Level.fromString("3");
        assertEquals(expected, ParserUtil.parseLevel("3"));
        assertEquals(expected, ParserUtil.parseLevel("   3   "));
    }

    @Test
    void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay(null));
    }

    @Test
    void parseDay_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("Funday"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDay(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("   "));
    }

    @Test
    void parseDay_valid_trimsAndParses() throws Exception {
        Day expected = new Day("Monday");
        assertEquals(expected, ParserUtil.parseDay("Monday"));
        assertEquals(expected, ParserUtil.parseDay("   Monday   "));
    }

    @Test
    void parseRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRate(null));
    }

    @Test
    void parseRate_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRate("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseRate("abc"));
        assertThrows(ParseException.class, () -> ParserUtil.parseRate(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseRate("   "));
    }

    @Test
    void parseLessonTime_nulls_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonTime(null, "10:00"));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonTime("09:00", null));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonTime(null, null));
    }

    @Test
    void parseLessonTime_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime("9:00", "10:00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime("09:60", "10:00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime("aa:bb", "10:00"));
    }

    @Test
    void parseLessonTime_endBeforeStart_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime("10:00", "09:00"));
    }

    @Test
    void parseLessonTime_equalTimes_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonTime("09:00", "09:00"));
    }

    @Test
    void parseLessonTime_valid_trimsAndParses() throws Exception {
        LessonTime expected = LessonTime.ofLessonTime("14:00", "15:30");
        assertEquals(expected, ParserUtil.parseLessonTime("14:00", "15:30"));
        assertEquals(expected, ParserUtil.parseLessonTime("  14:00  ", "  15:30  "));
    }
}
