package com.eight.demo.chap7;

import com.eight.demo.chap6.PrimeNumbersCollector;
import com.eight.demo.util.ToolUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 使用分支/合并框架执行并行求和
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    //求和数组
    private final long[] numbers;

    //子任务处理的数组的起始和终止位置
    private final int start;
    private final int end;

    //不在将任务分解为子任务的数据大小
    public static final long THRESHOLD = 10_1000;

    //公共构造函数用于创建主任务
    public ForkJoinSumCalculator(long[] number) {
        this(number, 0, number.length);
    }

    //私有的构造函数用于递归为主任务创建子任务
    private ForkJoinSumCalculator(long[] number, int start, int end) {
        this.numbers = number;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        //当大小小于阈值，顺序计算结果
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        //利用另一个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.compute();
        return rightResult + leftResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static Long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static void main(String[] args) {
        /**
         * 这里被1_000_000L折腾很久，开始传1_000_000,存在ForkJoinSumCalculator::forkJoinSum的Long无法转为R
         * 最后发现forkJoinSum的参数改成 int n就会好了，其他方式不管用
         * 最后发先measureMethod的第二个参数将会是forkJoinSum的入参，类型需要一致
         */
        System.out.println("forkJoinSum->Fastest cost " +
                ToolUtils.measureMethod(ForkJoinSumCalculator::forkJoinSum, 1_000_000L) + " ms");
    }


}
