package com.example.langtonsant;

import java.util.HashSet;
import java.util.Set;

public class sequentialGrid implements Grid{
    private int width;
    private int height;
    private Set<Cell> blackCells = new HashSet<>();//hashset for parallel and sequnital

    public sequentialGrid(int width, int height){
        this.width = width;
        this.height = height;
    }
    @Override
    public boolean isBlack(int x, int y){
        return blackCells.contains(new Cell(x,y));
    }
    @Override
    public void flipCell(int x, int y){
        Cell cell = new Cell(x,y);

        if(!blackCells.remove(cell)){
            blackCells.add(cell);
        }
    }

    @Override
    public int getWidth(){
        return width;
    }

    @Override
    public int getHeight(){
        return height;
    }
//    private  long key(int x, int y) {
//        long key = x;
//        key = key << 32;
//        key = key | (y & 0xffffffffL);
//        return key;
//    }
//
//    public int getBlackCellCount(){
//        return blackCells.size();
//    }
//    public int getWidth(){
//        return width;
//    }
//
//    public int getHeight(){
//        return height;
//    }
}

