package com.eight.demo.chap8.design_pattern.observer;

/**
 * 观察模式的主题：待事务变化用于通知观察者
 */
public interface Subject {
    // 注册一个新的观察者
    void registerObserver(Observer o);
    // 通知它的观察者一个新闻的到来
    void notifyObservers(String tweet);
}
