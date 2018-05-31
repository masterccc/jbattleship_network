package com.controler;

import com.model.AbstractModel;

public abstract class AbstractControler {

    protected AbstractModel model;

    public AbstractControler(AbstractModel cal){
        model = cal ;
    }

    abstract void control();
}
