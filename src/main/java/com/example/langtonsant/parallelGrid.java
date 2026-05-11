package com.example.langtonsant;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class parallelGrid implements Grid{
    private int width;
    private int height;
    private final ConcurrentHashMap<Cell, Boolean> blackCells;

    public parallelGrid(int width, int height){
        this.width = width;
        this.height = height;
        this.blackCells = new ConcurrentHashMap<>();
    }

    @Override
    public  boolean isBlack(int x, int y){
        return blackCells.containsKey(new Cell(x,y));
    }

    @Override
    public void flipCell(int x, int y){
        Cell cell = new Cell(x,y);
        blackCells.compute(cell, (key, value) -> {
            if(value == null){
                return true;
            }

            return null;
        });
    }

    @Override
    public boolean getAndFlip(int x, int y){
        Cell cell = new Cell(x,y);
        final boolean [] wasBlack = new boolean[1];
//safe in parallel no race conditions
        blackCells.compute(cell, (key, value) ->{
            wasBlack[0] = value != null;

            if(value == null) {
                return true;
            }else {
                return null;
            }
        });
        return wasBlack[0];
    }

    @Override
    public int getWidth(){
        return width;
    }
    @Override
    public int getHeight(){
        return height;
    }
    public int getBlackCellCount(){
        return blackCells.size();
    }
}
