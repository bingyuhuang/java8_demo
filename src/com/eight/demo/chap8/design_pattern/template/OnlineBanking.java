package com.eight.demo.chap8.design_pattern.template;

import java.util.function.Consumer;

/**
 * 验证模板方法
 * 在线银行: 基础操作 + 让客户满意的优化
 * 不同的支行可以继承OnlineBanking对优化提供差异实现
 */
abstract class OnlineBanking {
    public void processCustomer(int id) {
        // TODO 1.基础操作
        Customer c = new Customer();
        // 2.改进 ：让客户满意的优化
        makeCustomerHappy(c.getCustomerById(id));
    }

    abstract void makeCustomerHappy(Customer c);





}

