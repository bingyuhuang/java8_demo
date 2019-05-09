package com.eight.demo.chap8.design_pattern.template;


import java.util.function.Consumer;

/**
 * Lambda表达式式优化
 */
public class OnlineBankingLambda {
    // processCustomer使用Lambda表达式
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        // TODO 1.基础操作
        // 2.改进 ：让客户满意的优化
        makeCustomerHappy.accept(new Customer().getCustomerById(id));
    }
    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello " + c));
    }
}


