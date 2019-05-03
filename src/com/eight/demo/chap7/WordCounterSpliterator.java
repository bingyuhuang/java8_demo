package com.eight.demo.chap7;

import com.eight.demo.util.ToolUtils;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;

    //当前字符位置
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    /**
     * 处理当前字符串如果还有要处理的返回true
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        // 获取需要解析字符串的大小，若小于10则不进行拆分，顺序处理
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null;
        }
        // 尝试拆分位置设定为string的中间位置，系统拆分位置到下一个空格
        for (int splitPos = currentSize/2 + currentChar; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

    public static int wordCount(String s) {
        //String转换成原始类型流
        Spliterator<Character> spliterator = new WordCounterSpliterator(s);
        /**
         *  第二个boolean表示创建并行流
         *  这里发现使用并行true
         *  但对于少量的数据并行执行时间会变得更长了
         */
        Stream<Character> stream = StreamSupport.stream(spliterator, false);
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    public static void main(String[] args) {
        String s =  "a f d";
        System.out.println(wordCount(s));
        System.out.println(ToolUtils.measureMethod(WordCounterSpliterator::wordCount, s));
    }
}
