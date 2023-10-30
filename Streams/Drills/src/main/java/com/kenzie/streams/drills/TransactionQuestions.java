package com.kenzie.streams.drills;

import com.kenzie.streams.drills.resources.Trader;
import com.kenzie.streams.drills.resources.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use the provided Trader and Transaction data to implement the class's methods.
 *
 * Do NOT modify the Trader or Transaction data.
 */
public class TransactionQuestions {

    private Trader raoul = new Trader("Raoul", "Cambridge");
    private Trader mario = new Trader("Mario", "Milan");
    private Trader alan = new Trader("Alan", "Cambridge");
    private Trader brian = new Trader("Brian", "Cambridge");

    private List<Transaction> transactions;

    public TransactionQuestions(){
        transactions = List.of(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    public TransactionQuestions(List<Transaction> list){
        transactions = list;
    }

    /**
     * Find all transactions in the year 2011 and sort them by value (small to large).
     * @return a list of all transactions that occurred in 2011
     */
    public List<Transaction> transactions2011() {
       return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

    }

    /**
     * What are all the unique cities where the traders work?
     * @return A list of all unique cities traders work in
     */
    public List<String> uniqueCities() {
       return transactions.stream()
               .map(Transaction::getTrader)
               .map(Trader::getCity)
               .distinct()
               .sorted()
               .collect(Collectors.toList());
    }

    /**
     * Find all traders from Cambridge and sort them by name alphabetically.
     * @return a list of all traders based in cambridge
     */
    public List<Trader> cambridgeTraders() {
        return transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
    }

    /**
     * Return a single string of all traders' names sorted alphabetically.
     *
     * There should be no characters in between each name.
     * @return a concatenated string of all trader names
     */
    public String traderNames() {

        return transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.joining());
    }

    /**
     * Are any traders based in Milan?
     * @return true, if any traders are Milan based
     */
    public boolean isMilanBased() {
        return transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> trader.getCity().equals("Milan"));

    }

    /**
     * Print all transactions' values from the traders living in Cambridge.
     */
    public void printCambridgeTransactions() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * What's the highest value of all the transactions?
     * @return An optional with the highest value of a trade, if a trade occurred.
     */
    public Optional<Integer> highestValueTrade() {
        return Optional.ofNullable(transactions)
                .orElse(Collections.emptyList())
                .stream()
                .map(Transaction::getValue)
                .max(Comparator.naturalOrder());
    }

    /**
     * Find the transaction with the smallest value.
     * @return An optional with the transaction with the smallest value, if a transaction exists.
     */
    public Optional<Transaction> smallestTransaction() {
        return transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
    }
}
