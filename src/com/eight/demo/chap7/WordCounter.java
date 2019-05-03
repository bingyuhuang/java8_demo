package com.eight.demo.chap7;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 不可并行的单词计数器
 */
public class WordCounter {
    /**
     * 一个迭代式数字统计方法
     */
    public int countWordsIteratively(String s) {
        int count = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c))
                lastSpace = true;
            else if (lastSpace) count++;
            lastSpace = false;
        }
        return count;
    }

    /**
     * -----------以函数式的风格转换上述方法--------------
     */

    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    /**
     * 遍历所有字符
     */
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c))
            return lastSpace ? this : new WordCounter(counter, true);
        else
            return lastSpace ? new WordCounter(counter + 1, false) : this;
    }

    /**
     * 合并两个WordCounter
     */
    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
        return counter;
    }

    public static int wordCount(String s) {
        //String转换成原始类型流
        Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(s::charAt);
        /**
         * 归约 stream.parallel以后结果偏差 证明无法并行
         * 原因：原始的String在任意位置拆分
         */
       WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
       return wordCounter.getCounter();
    }

    public static void main(String[] args) {
        System.out.println(wordCount("aa bdd ca a dfa adf"));
    }
}
