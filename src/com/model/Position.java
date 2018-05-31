package com.model;

/**
 * Class position permettant de repérer la position de bateaux et bombardements
 */
public class Position {

    int x;
    int y;

    public Position(){}

    public Position(int x, int y){
        this.x = x ;
        this.y = y ;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incY(int v){
        y += v;
    }
    public void incX(int v){
        x+=v;
    }
    public void decY(int v){
        x -= v;
    }
    public void decX(int v){
        y-=v;
    }

}
