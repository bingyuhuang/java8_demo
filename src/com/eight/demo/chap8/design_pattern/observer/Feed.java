package com.eight.demo.chap8.design_pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现Subject接口的列
 * 1.维护观察者列表
 * 2.新闻到达时，进行通知
 */
public class Feed implements Subject{
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }

    public static void main(String[] args) {

        Feed f = new Feed();

        // 类的方式
        f.registerObserver(new NYTime());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favorite book is Java 8 in Action");

        /**
         *  无需显示实例化三个观察者
         */
        f.registerObserver((String tweet) -> {if(tweet != null && tweet.contains("money")) System.out.println("Breaking news in NY!" + tweet);});

        f.registerObserver((String tweet) -> {if(tweet != null && tweet.contains("queen")) System.out.println("Yet another new in London..." + tweet);});

        f.registerObserver((String tweet) -> {if(tweet != null && tweet.contains("wine")) System.out.println("Breaking news in NY!" + tweet);});

        f.notifyObservers("The money is evil");
    }
}
