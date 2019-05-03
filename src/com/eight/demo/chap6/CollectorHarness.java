package com.eight.demo.chap6;

import java.util.List;
import java.util.Map;

import static com.eight.demo.chap6.PrimeNumbersCollector.partitionPrime;
import static com.eight.demo.chap6.PrimeNumbersCollector.partitionPrimesWithCustomCollector;

/**
 * 比较收集器的性能 partitionPrime(310) -> partitionPrimesWithCustomCollector(239)
 */
public class CollectorHarness {
    public static void main(String[] args) {
        long fastest = Long.MAX_VALUE;
        //运行测试10次取最快的毫秒值
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            //将一百万个自然数按质数和非质数分区
            //partitionPrime(1_000_000);
            partitionPrimesWithCustomCollector(1_000_000);
            //运行毫秒值
            long duration = (System.nanoTime() - start) / 1_000_000;
            //检查此次执行是否为最快的一次
            if (duration < fastest) fastest = duration;
        }
        System.out.println("Fastest execution done in " + fastest + " ms");
    }
}
