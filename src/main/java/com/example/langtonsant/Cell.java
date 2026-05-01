package com.example.langtonsant;

import java.util.Objects;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Cell other)){
            return false;
        }

        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
}
