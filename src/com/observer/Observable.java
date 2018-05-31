package com.observer;

public interface Observable {
    void addObserver(Observer obs);
    void notifyObserver(String str);
}