package seedu.address.model.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPayments.feb25Paid;
import static seedu.address.testutil.TypicalPayments.feb25Unpaid;
import static seedu.address.testutil.TypicalPayments.jan25Paid;
import static seedu.address.testutil.TypicalPayments.mar25Unpaid;
import static seedu.address.testutil.TypicalPayments.sampleArrayList;
import static seedu.address.testutil.TypicalPayments.sep25Unpaid;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.payment.exceptions.PaymentException;
import seedu.address.testutil.PaymentBuilder;

public class PaymentListTest {

    /*
    Testing the payment constructor
     */
    @Test
    public void constructor_noArguments_success() {
        PaymentList pl = new PaymentList();
        assertEquals(0, pl.size());
        assertTrue(pl.isEmpty());
        assertNull(pl.findAndSetEarliestUnpaidYearMonth());
    }

    @Test
    public void constructor_oneValidUnpaidLessonArgument_success() {
        PaymentList pl = new PaymentList(new PaymentBuilder()
                .withYearMonth("2025-03").withTotalAmount(600f)
                .withIsPaid(false).build());
        assertEquals(1, pl.size());
        assertNotNull(pl.findAndSetEarliestUnpaidYearMonth());
        assertEquals(Status.UNPAID, pl.getStatus());
    }

    @Test
    public void constructor_oneValidPaidLessonArgument_success() {
        PaymentList pl = new PaymentList(jan25Paid());
        assertEquals(1, pl.size());
        assertNull(pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.PAID, pl.getStatus());
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaymentList((Payment) null));
    }

    @Test
    public void constructor_validUnpaidLessonList_success() {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Unpaid()));
        PaymentList pl = new PaymentList(al);
        assertEquals(2, pl.size());
        assertEquals(YearMonth.of(2025, 2), pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.UNPAID, pl.getStatus());
    }

    @Test
    public void constructor_validOverdueLessonList_success() {
        PaymentList pl = new PaymentList(sampleArrayList());
        assertEquals(3, pl.size());
        assertEquals(YearMonth.of(2025, 2), pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.OVERDUE, pl.getStatus());
    }

    @Test
    public void getPaymentByIndex_validIndex_success() throws PaymentException {
        PaymentList pl = new PaymentList(sampleArrayList());
        System.out.println("Payments size: " + pl.size());
        System.out.println("Payments list: " + pl);
        assertEquals(jan25Paid(), pl.getPaymentByIndex(1));
    }

    @Test
    public void getPaymentByMonth_validIndex_success() throws PaymentException {
        PaymentList pl = new PaymentList(sampleArrayList());
        assertEquals(jan25Paid(), pl.getPaymentByMonth(YearMonth.of(2025, 1)));
    }

    @Test
    public void addPayment_validPayment_success() {
        PaymentList pl = new PaymentList(sampleArrayList());
        pl.addPayment(sep25Unpaid());
        assertEquals(4, pl.size());
        assertEquals(YearMonth.of(2025, 2), pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.OVERDUE, pl.getStatus());
    }

    @Test
    public void addPayment_validPayment_successAndStatusChangesFromUnpaidToOverdue() {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Unpaid()));
        PaymentList pl = new PaymentList(al);
        pl.addPayment(mar25Unpaid());
        assertEquals(3, pl.size());
        assertEquals(YearMonth.of(2025, 2), pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.OVERDUE, pl.getStatus());
    }

    @Test
    public void addPayment_validPayment_successAndStatusChangesFromPaidToUnpaid() {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Paid()));
        PaymentList pl = new PaymentList(al);
        pl.addPayment(mar25Unpaid());
        assertEquals(3, pl.size());
        assertEquals(YearMonth.of(2025, 3), pl.getEarliestUnpaidYearmonth());
        assertEquals(Status.UNPAID, pl.getStatus());
    }

    @Test
    public void findUnpaids_returnNull() {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Paid()));
        PaymentList pl = new PaymentList(al);
        ArrayList<Payment> unpaids = pl.findUnpaids();
        assertEquals(0, unpaids.size());
        assertEquals(Status.PAID, pl.getStatus());
    }

    @Test
    public void findUnpaids_returnArrayList() {
        PaymentList pl = new PaymentList(sampleArrayList());
        ArrayList<Payment> unpaids = pl.findUnpaids();

        ArrayList<Payment> expectedUnpaids = new ArrayList<>(List.of(feb25Unpaid(), mar25Unpaid()));
        assertEquals(2, unpaids.size());
        assertEquals(expectedUnpaids, unpaids);
        assertEquals(Status.OVERDUE, pl.getStatus());
    }

    @Test
    public void updateExistingAmount_amountUpdatedAndMarkedUnpaid() throws Exception {
        YearMonth ym = YearMonth.of(2025, 10);
        Payment p = new PaymentBuilder().withYearMonth(ym.toString()).withTotalAmount(0f).build();
        PaymentList pl = new PaymentList(p);

        pl.updateExistingPayment(ym, 2600.00f);

        Payment pp = pl.getPaymentByMonth(ym);
        assertEquals(2600.00f, pp.getTotalAmountFloat(), 1e-4, "totalAmount should update");
        assertFalse(p.isPaid(), "payment must be marked unpaid after updateExistingPayment");
    }

    @Test
    public void updateExistingAmount_recomputesAggregateStatus() throws Exception {
        YearMonth ym = YearMonth.of(2025, 10);
        Payment p = new PaymentBuilder().withYearMonth(ym.toString()).withTotalAmount(50f).build();
        p.setPaid(true);
        PaymentList pl = new PaymentList(p);

        assertEquals(Status.PAID, pl.getStatus(), "precondition: list status starts as PAID");

        pl.updateExistingPayment(ym, 100f);

        assertEquals(Status.UNPAID, pl.getStatus(),
                "after making target month unpaid, aggregate status should be UNPAID");
    }

    @Test
    public void updateExistingAmount_doesNotAffectOtherMonths() throws Exception {
        YearMonth oct = YearMonth.of(2025, 10);
        YearMonth nov = YearMonth.of(2025, 11);

        Payment pOct = new PaymentBuilder().withYearMonth(oct.toString()).withTotalAmount(500f).build();
        Payment pNov = new PaymentBuilder().withYearMonth(nov.toString()).withTotalAmount(750f).build();

        PaymentList pl = new PaymentList(new ArrayList<>(List.of(pOct, pNov)));

        pl.updateExistingPayment(oct, 600f);

        Payment paymentOct = pl.getPaymentByMonth(oct);
        Payment paymentNov = pl.getPaymentByMonth(nov);

        assertEquals(600f, paymentOct.getTotalAmountFloat(), 1e-4);
        assertFalse(pOct.isPaid(), "Target month should be set to unpaid");

        assertEquals(750f, paymentNov.getTotalAmountFloat(), 1e-4,
                "Other months' amounts must not change");
    }

    @Test
    public void updateExistingAmount_invalidMonth_throwsWhenMonthMissing() {
        YearMonth ym = YearMonth.of(2025, 10);
        YearMonth nov = YearMonth.of(2025, 11);
        Payment p = new PaymentBuilder().withYearMonth(nov.toString()).withTotalAmount(500f).build();
        PaymentList pl = new PaymentList(p);

        assertThrows(PaymentException.class, () ->
                        pl.updateExistingPayment(ym, 123f),
                "should throw when target month is absent");
    }

    @Test
    public void markAllPaid_success() throws PaymentException {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Unpaid(), mar25Unpaid()));
        PaymentList pl = new PaymentList(al);
        pl.markAllPaid();

        assertEquals(Status.PAID, pl.getStatus());
        assertNull(pl.getEarliestUnpaidYearmonth());
    }

    @Test
    public void markAllPaid_allPaid_throwsPaymentException() throws PaymentException {
        ArrayList<Payment> al = new ArrayList<>(List.of(jan25Paid(), feb25Paid()));
        PaymentList pl = new PaymentList(al);

        assertThrows(PaymentException.class, () -> pl.markAllPaid());
    }

    /*
    Testing toString
     */
    @Test
    void toString_numberedAndIncludesPaymentStrings() throws PaymentException {
        PaymentList pl = new PaymentList(sampleArrayList());

        String s = pl.toString();
        String[] lines = s.split("\\R");

        assertTrue(lines[0].startsWith("1. "));
        assertTrue(lines[1].startsWith("2. "));
        assertTrue(lines[2].startsWith("3. "));

        assertTrue(lines[0].contains("2025-01"));
        assertTrue(lines[1].contains("2025-02"));
        assertTrue(lines[2].contains("2025-03"));
    }

    @Test
    void toString_numberedAndIncludesPaymentStringsAndSorts() throws PaymentException {
        ArrayList<Payment> al = new ArrayList<>(List.of(mar25Unpaid(), jan25Paid(), feb25Unpaid()));
        PaymentList pl = new PaymentList(al);

        String s = pl.toString();
        String[] lines = s.split("\\R");

        assertTrue(lines[0].startsWith("1. "));
        assertTrue(lines[1].startsWith("2. "));
        assertTrue(lines[2].startsWith("3. "));

        assertTrue(lines[0].contains("2025-01"));
        assertTrue(lines[1].contains("2025-02"));
        assertTrue(lines[2].contains("2025-03"));
    }

    // ---------- addPaymentIfAbsent ----------

    @Test
    public void addPaymentIfAbsent_absent_addsTrue_keepsSorted_andUpdatesStatus() throws Exception {
        PaymentList pl = new PaymentList();
        // add in non-sorted order and mix paid/unpaid
        assertTrue(pl.addPaymentIfAbsent(mar25Unpaid())); // unpaid
        assertTrue(pl.addPaymentIfAbsent(jan25Paid()));   // paid
        assertTrue(pl.addPaymentIfAbsent(feb25Unpaid())); // unpaid

        // Sorted by YearMonth
        assertEquals(YearMonth.of(2025, 1), pl.getPaymentByIndex(1).getYearMonth());
        assertEquals(YearMonth.of(2025, 2), pl.getPaymentByIndex(2).getYearMonth());
        assertEquals(YearMonth.of(2025, 3), pl.getPaymentByIndex(3).getYearMonth());

        // Status/earliest: two unpaids (Feb & Mar) -> OVERDUE, earliest = Feb
        assertEquals(Status.OVERDUE, pl.getStatus());
        assertEquals(YearMonth.of(2025, 2), pl.getEarliestUnpaidYearmonth());
    }

    @Test
    public void addPaymentIfAbsent_present_sameMonth_returnsFalse_andNoDuplicate() throws Exception {
        PaymentList pl = new PaymentList();
        assertTrue(pl.addPaymentIfAbsent(feb25Unpaid()));
        int size = pl.size();

        // Attempt to add same YearMonth again (even with different amount/paidness)
        Payment duplicateDifferentFields = new PaymentBuilder()
                .withYearMonth("2025-02").withTotalAmount(999f).withIsPaid(true).build();
        assertFalse(pl.addPaymentIfAbsent(duplicateDifferentFields));
        assertEquals(size, pl.size(), "must not duplicate entries for same YearMonth");

        // Stored entry remains the original unpaid one
        Payment stored = pl.getPaymentByMonth(YearMonth.of(2025, 2));
        assertEquals(feb25Unpaid(), stored);
        assertFalse(stored.isPaid());
    }

    // ---------- putPaymentForMonth (upsert) ----------

    @Test
    public void putPaymentForMonth_absent_inserts_andReturnsNull_updatesState() throws Exception {
        PaymentList pl = new PaymentList();
        Payment previous = pl.putPaymentForMonth(mar25Unpaid()); // insert
        assertNull(previous);

        Payment got = pl.getPaymentByMonth(YearMonth.of(2025, 3));
        assertFalse(got.isPaid());
        assertEquals(Status.UNPAID, pl.getStatus());
        assertEquals(YearMonth.of(2025, 3), pl.getEarliestUnpaidYearmonth());
    }

    @Test
    public void putPaymentForMonth_present_overwrites_andReturnsPrevious_keepsSortedAndStatus() throws Exception {
        // Start with Jan paid, Feb unpaid
        PaymentList pl = new PaymentList(new ArrayList<>(List.of(jan25Paid(), feb25Unpaid())));

        // Overwrite Feb with a PAID payment and different amount
        Payment overwrite = new PaymentBuilder().withYearMonth("2025-02").withTotalAmount(321.0f).withIsPaid(true).build();
        Payment prev = pl.putPaymentForMonth(overwrite);

        assertNotNull(prev, "should return previous value when overwriting existing month");
        assertEquals(feb25Unpaid(), prev, "previous should equal the original stored value");

        // Now Feb should be PAID with new amount; both months paid => PAID status and null earliest
        Payment feb = pl.getPaymentByMonth(YearMonth.of(2025, 2));
        assertTrue(feb.isPaid());
        assertEquals(321.0f, feb.getTotalAmountFloat(), 1e-6);

        assertEquals(Status.PAID, pl.getStatus());
        assertNull(pl.getEarliestUnpaidYearmonth());

        // Still sorted: Jan then Feb
        assertEquals(YearMonth.of(2025, 1), pl.getPaymentByIndex(1).getYearMonth());
        assertEquals(YearMonth.of(2025, 2), pl.getPaymentByIndex(2).getYearMonth());
    }

    // ---------- copy (deep defensive copy) ----------

    @Test
    public void copy_returnsDeepCopy_mutationsDoNotLeakAcross() throws Exception {
        // Build original with a specific unpaid Jan and paid Feb using PaymentBuilder for explicit amounts
        Payment janUnpaid10 = new PaymentBuilder().withYearMonth("2025-01").withTotalAmount(10f).withIsPaid(false).build();
        Payment febPaid20 = new PaymentBuilder().withYearMonth("2025-02").withTotalAmount(20f).withIsPaid(true).build();
        PaymentList original = new PaymentList(new ArrayList<>(List.of(janUnpaid10, febPaid20)));

        PaymentList cloned = original.copy();

        // Mutate original's Jan: mark paid and change amount
        Payment janInOriginal = original.getPaymentByMonth(YearMonth.of(2025, 1));
        janInOriginal.markPaid();
        janInOriginal.setTotalAmount(777f);

        // Clone should retain its original independent copy (unpaid, amount 10)
        Payment janInClone = cloned.getPaymentByMonth(YearMonth.of(2025, 1));
        assertFalse(janInClone.isPaid(), "clone must be independent of original mutations");
        assertEquals(10f, janInClone.getTotalAmountFloat(), 1e-6);

        // Mutate clone's Feb: change amount; original must remain unchanged
        Payment febInClone = cloned.getPaymentByMonth(YearMonth.of(2025, 2));
        febInClone.setTotalAmount(999f);
        febInClone.markPaid();

        Payment febInOriginal = original.getPaymentByMonth(YearMonth.of(2025, 2));
        assertEquals(20f, febInOriginal.getTotalAmountFloat(), 1e-6);
        assertTrue(febInOriginal.isPaid());

        assertEquals(Status.UNPAID, cloned.getStatus());
        assertEquals(YearMonth.of(2025, 1), cloned.getEarliestUnpaidYearmonth());
    }
}
