package com.eight.demo.chap5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Page98:交易员和交易问题
 */
public class Solution {
    /**
     * 1.查找2011年所有的交易，按交易额排序(低到高)
     */
    public static List<Transaction> sortTransactionIn2011 (List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }


    /**
     * 2.交易员都在哪些不同的城市工作过
     */


    /**
     * 3.查找来自剑桥的交易员，按姓名排序
     */

    /**
     * 4.返回所有交易员的姓名字符串，按字母顺序排序
     */

    /**
     * 5.有没有交易员在米兰
     */

    /**
     * 6.打印生活在剑桥的交易员的所有交易额
     */

    /**
     * 7.所有交易中，最高交易额
     */

    /**
     * 8.找到交易额最小的交易
     */


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
                new  Transaction(alan, 2012, 950)
        );

        Solution.sortTransactionIn2011(transactions).stream().forEach(System.out::println);


    }
}
