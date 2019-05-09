package com.eight.demo.chap8.design_pattern.observer;

/**
 * Lemonde新闻观察者
 */
public class LeMonde implements Observer {
    @Override
    public void notify(String tweet) {
        // 订阅关于酒的新闻
        if (tweet != null && tweet.contains("wine")) {
            System.out.println("Today cheese, wine and news " + tweet);
        }
    }
}
