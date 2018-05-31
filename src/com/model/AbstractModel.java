package com.model;

import com.observer.Observable;
import com.observer.Observer;

import java.util.ArrayList;

public abstract class AbstractModel implements Observable {

    private ArrayList<Observer> listObserver = new ArrayList<Observer>();

    public AbstractModel() {
    }

    public void addObserver(Observer obs){
        this.listObserver.add(obs);
    }

    public void notifyObserver(String str){
        for(Observer obs : listObserver){
            obs.update(str);
        }
    }

    public void removeObserver(){
        listObserver = new ArrayList<Observer>();
    }

}
