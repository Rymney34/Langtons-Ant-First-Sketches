package com.example.langtonsant;

public class Grid {
    private int width;
    private int height;
    private boolean [][] cells;

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        this.cells = new boolean[width][height];
    }

    public boolean isBlack(int x, int y){
        return cells[x][y];
    }

    public void flipCell(int x, int y){
        cells[x][y] = !cells[x][y];
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

