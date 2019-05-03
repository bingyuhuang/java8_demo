package com.eight.demo.util;

import java.util.function.Function;

public class ToolUtils {
    //测量工具
    public static <T, R> long measureMethod(Function<T, R> function, T n) {
        long fastest = Long.MAX_VALUE;
        //运行测试10次取最快的毫秒值R
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            function.apply(n);
            //运行毫秒值
            long duration = (System.nanoTime() - start);
            //检查此次执行是否为最快的一次
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }
}
