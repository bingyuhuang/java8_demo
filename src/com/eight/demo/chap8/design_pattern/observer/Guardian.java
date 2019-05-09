package com.eight.demo.chap8.design_pattern.observer;

/**
 * 英国时报观察者
 */
public class Guardian implements Observer {
    @Override
    public void notify(String tweet) {
        // 订阅关于女王的新闻
        if (tweet != null && tweet.contains("queen"))
            System.out.println("Yet another new in London..." + tweet);
    }
}
