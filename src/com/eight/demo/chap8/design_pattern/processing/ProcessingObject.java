package com.eight.demo.chap8.design_pattern.processing;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 责任链模式验证
 */
public abstract class ProcessingObject<T> {
    // 后继者：后续进行操作的对象
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handel(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handel(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTestProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        String result = p1.handel("Aren't labda really sexy?!!");
        System.out.println(result);

        /**
         * 该模式类似连接函数 使用 Function andThen 方法连接两个方法
         */
        UnaryOperator<String> headerProcessing = (String input) -> "From Raoul, Mario and Alan: " + input;
        UnaryOperator<String> spellCheckProcessing = (String input) -> input.replaceAll("labda", "lambda");

        Function<String, String> pipeline  = headerProcessing.andThen(spellCheckProcessing);
        String re = pipeline.apply("Aren't labda really sexy?!!");
        System.out.println(re);

    }
}

