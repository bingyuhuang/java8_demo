package com.eight.demo.chap8.design_pattern.observer;

/**
 * 纽约时报观察者
 */
public class NYTime implements Observer {
    @Override
    public void notify(String tweet) {
        // 订阅金钱相关的新闻
        if (tweet != null && tweet.contains("money")) {
            System.out.println("Breaking news in NY!" + tweet);
        }
    }
}
