package com.eight.demo.chap5;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Page98:交易员和交易问题
 */
public class Solution<T> {
    /**
     * 1.查找2011年所有的交易，按交易额排序(低到高)
     */
    public static List<Transaction> sortTransactionIn2011(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
    }


    /**
     * 2.交易员都在哪些不同的城市工作过
     */
    public static List<String> getAllCitysOfTrader(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
    }

    /**
     * 3.查找来自剑桥的交易员，按姓名排序
     */
    public static List<Trader> sortAllTradersInCambridge(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
    }

    /**
     * 4.返回所有交易员的姓名字符串，按字母顺序排序
     */
    public static String getAllTraderNameString(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining(","));
    }

    /**
     * 5.有没有交易员在米兰
     */
    public static boolean haveTraderInMilan(List<Transaction> transactions) {
        return transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
    }

    /**
     * 6.打印生活在剑桥的交易员的所有交易额
     */
    public static void printValueOfTraderInCambridege(List<Transaction> transactions) {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

    }

    /**
     * 7.所有交易中，最高交易额
     */
    public static Optional<Integer> getMaxValue(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
    }

    /**
     * 8.找到交易额最小的交易
     */
    public static Optional<Transaction> getMinTransaction(List<Transaction> transactions) {
        return transactions.stream().min(comparing(Transaction::getValue));
    }


    public void output(T collection) {
        if (collection instanceof Collection) {
            ((Collection) collection).stream().forEach(System.out::println);
        } else {
            System.out.println(collection);
        }

        System.out.println();

    }

    public static void main(String[] args) {
        /**
         * 交易员
         */
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        /**
         * 交易
         */
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        Solution<Object> solution = new Solution<>();

        solution.output(sortTransactionIn2011(transactions));

        solution.output(getAllCitysOfTrader(transactions));

        solution.output(sortAllTradersInCambridge(transactions));

        solution.output(getAllTraderNameString(transactions));

        solution.output("haveTraderInMilan:" + haveTraderInMilan(transactions));

        printValueOfTraderInCambridege(transactions);
        System.out.println();

        solution.output(getMaxValue(transactions));

        solution.output(getMinTransaction(transactions));
    }
}
