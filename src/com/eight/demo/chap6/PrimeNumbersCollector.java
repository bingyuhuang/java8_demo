package com.eight.demo.chap6;

import com.eight.demo.util.ToolUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

/**
 * 1.定义Collector类的签名Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>>
 * Integer:流中元素类型
 * Map<Boolean, List<Integer>>：累积器类型
 */
public class PrimeNumbersCollector
        implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {


    /**
     * --------------------a.描述：测试数是否为质数-----------------------------------------
     * 将前n个自然数按质数和非质数区分
     */
    public static Map<Boolean, List<Integer>> partitionPrime(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    /**
     * 判断是否为质数，限制除数不超过被限制数的平方根
     *
     * @param candidate
     * @return true 质数
     */
    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }


    /**
     * 仅用质数做除数
     * 1.一个质数list、和一个candidate
     * 2.takeWhile方法收集小于candidate的列表
     * 3.isPrime 只用不大于平方根的质数去测试
     */
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) { //这个地方被坑了半天：主要还是理解这个返回逻辑当，质数中出现大于候选人平方根的数，则返回
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    /**
     * ----------------------c.定义完整收集器----------------------------------------------
     * 2.实现归约过程
     * supplier() 返回累加器并初始化两个空列表
     * accumulate() 收集流中元素的逻辑 任何一次迭代都可以访问收集过程的部分结果
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
//        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
//            acc.get(isPrime(acc.get(true), candidate))
//                    .add(candidate);
//        };

        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(isPrime(acc.get(true),
                    candidate))
                    .add(candidate);
        };
    }

    /**
     * 让收集器并行工作，将两个部分累加器合并(次算法本身顺序，不能并行)
     * 留空 或者最好的抛UnsupportedOperationException异常
     *
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    /**
     * 自定义收集器
     *
     * @param candidate
     * @return
     */
    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int candidate) {
        return IntStream.rangeClosed(2, candidate).boxed()
                .collect(new PrimeNumbersCollector());
    }

    public static void main(String[] args) {

        System.out.println("partitionPrime->Fastest execution done in " +
                ToolUtils.measureMethod(PrimeNumbersCollector::partitionPrime, 1_000_000) + " ms");

        System.out.println("WithCustomCollector->Fastest execution done in " +
                ToolUtils.measureMethod(PrimeNumbersCollector::partitionPrimesWithCustomCollector, 1_000_000) + " ms");
    }


}
