package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Trader;
import com.kenzie.streams.drills.resources.Transaction;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TransactionQuestionsTest {

    private static final Trader RAOUL = new Trader("Raoul", "Cambridge");
    private static final Trader MARIO = new Trader("Mario", "Milan");
    private static final Trader ALAN = new Trader("Alan", "Cambridge");
    private static final Trader BRIAN = new Trader("Brian", "Cambridge");

    private static final Transaction BRIAN_2011_300 = new Transaction(BRIAN, 2011, 300);
    private static final Transaction RAOUL_2012_1000 = new Transaction(RAOUL, 2012, 1000);
    private static final Transaction RAOUL_2011_400 = new Transaction(RAOUL, 2011, 400);
    private static final Transaction MARIO_2012_710 = new Transaction(MARIO, 2012, 710);
    private static final Transaction MARIO_2012_700 = new Transaction(MARIO, 2012, 700);
    private static final Transaction ALAN_2012_950 = new Transaction(ALAN, 2012, 950);

    // should match the values in TransactionQuestions
    private static final List<Transaction> TRANSACTIONS = List.of(
            BRIAN_2011_300,
            RAOUL_2012_1000,
            RAOUL_2011_400,
            MARIO_2012_710,
            MARIO_2012_700,
            ALAN_2012_950
    );

    private static final TransactionQuestions TRANSACTION_QUESTIONS = new TransactionQuestions(TRANSACTIONS);

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Test
    public void transactions2011_withTransactions_returnsTransactionsSortedAscending() {
        List<Transaction> transactions2011 = TRANSACTION_QUESTIONS.transactions2011();
        assertEquals(2, transactions2011.size(), "Expected transactions2011 to return " +
            "2 transactions for 2011.");
        assertEquals(BRIAN_2011_300, transactions2011.get(0),
            "Expected the first transaction in 2011 to be the lowest of the year.");
        assertEquals(RAOUL_2011_400, transactions2011.get(1),
            "Expected the second transaction in 2011 to be the highest of the year.");
    }

    @Test
    public void uniqueCities_multipleCities_returnsUniqueCities() {
        List<String> uniqueCities = TRANSACTION_QUESTIONS.uniqueCities();
        assertEquals(uniqueCities.size(), 2, "Expected 2 unique cities.");
        assertTrue(uniqueCities.contains("Cambridge"), "Expected Cambridge to be a unique city.");
        assertTrue(uniqueCities.contains("Milan"), "Expected Milan to be a unique city.");
    }

    @Test
    public void cambridgeTraders_withCambridgeTraders_returnsCambridgeTradersSortedAlphabetically() {
        List<Trader> cambridgeTraders = TRANSACTION_QUESTIONS.cambridgeTraders();
        assertEquals(3, cambridgeTraders.size(), "Expected cambridgeTraders to return " +
            "3 Cambridge-based traders.");
        assertEquals(ALAN, cambridgeTraders.get(0));
        assertEquals(BRIAN, cambridgeTraders.get(1));
        assertEquals(RAOUL, cambridgeTraders.get(2));
    }

    @Test
    public void traderNames_multipleTraders_concatenatesNamesSortedAlphabetically() {
        String traderNames = TRANSACTION_QUESTIONS.traderNames();
        assertEquals("AlanBrianMarioRaoul", traderNames);
    }

    @Test
    public void isMilanBased_containsMilanBasedTraders_returnsTrue() {
        assertTrue(TRANSACTION_QUESTIONS.isMilanBased());
    }

    @Test
    public void printCambridgeTransactions_PrintsCambridgeTransactions(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        TRANSACTION_QUESTIONS.printCambridgeTransactions();

        String printString = outContent.toString();
        assertTrue(printString.contains(Integer.toString(ALAN_2012_950.getValue())) &&
                        printString.contains(Integer.toString(BRIAN_2011_300.getValue())) &&
                        printString.contains(Integer.toString(RAOUL_2011_400.getValue())) &&
                        printString.contains(Integer.toString(RAOUL_2012_1000.getValue())),
                "All cambridge transactions should be included in the print out");

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printCambridgeTransactions_doesNotPrintMilanTransactions(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        TRANSACTION_QUESTIONS.printCambridgeTransactions();

        String printString = outContent.toString();
        assertTrue( !printString.contains(Integer.toString(MARIO_2012_700.getValue())) &&
                        !printString.contains(Integer.toString(MARIO_2012_710.getValue())),
                "All cambridge transactions should be included in the print out");

        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void highestValueTrade_multipleTransactions_returnsHighest() {
        assertTrue(TRANSACTION_QUESTIONS.highestValueTrade().isPresent(), "Expected a non-empty " +
            "Optional of the highest value trade.");
        assertEquals(RAOUL_2012_1000.getValue(), TRANSACTION_QUESTIONS.highestValueTrade().get());
    }

    @Test
    public void smallestTransaction_multipleTransactions_returnsLowest() {
        assertTrue(TRANSACTION_QUESTIONS.smallestTransaction().isPresent(), "Expected a non-empty " +
            "Optional of the smallest value trade.");
        assertEquals(BRIAN_2011_300, TRANSACTION_QUESTIONS.smallestTransaction().get());
    }

    //<---------------------- streams testing ------------------------->

    @Test
    public void transactions2011_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.transactions2011();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void uniqueCities_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.uniqueCities();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }


    @Test
    public void cambridgeTraders_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.cambridgeTraders();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void traderNames_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.traderNames();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void isMilanBased_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.isMilanBased();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void printCambridgeTransactions_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.printCambridgeTransactions();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void highestValueTrade_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.highestValueTrade();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

    @Test
    public void smallestTransaction_usesStream(){
        List<Transaction> spylist = spy(List.of());

        TransactionQuestions questions = new TransactionQuestions(spylist);

        questions.smallestTransaction();

        assertDoesNotThrow(() -> verify(spylist).stream(),
                ".stream() and stream methods should be called on the list.\n\n");
        //testing that a for-each loop is not being used
        assertDoesNotThrow(() -> verify(spylist, never()).iterator(),
                "A for loop should not be used on the list,"
                        + " .stream() and stream methods should be used instead.\n\n");
    }

}
