package com.example.langtonsant;

import java.util.List;
import java.util.concurrent.RecursiveAction;


public class AntChunkMoveTask extends RecursiveAction {

    private static int LIMIT = 1;

    private List<Ant> ants;
    private Grid grid;
    private int end;
    private int start;

    public AntChunkMoveTask(List<Ant> ants, Grid grid, int start, int end){
        this.ants = ants;
        this.grid = grid;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute(){
//        System.out.println(Thread.currentThread().getName());
        if(end - start <= LIMIT){
            System.out.println(Thread.currentThread().getName());
            for(int i = start; i< end; i++){
                ants.get(i).move(grid);
            }
        }else {
            int middle = (start + end) / 2;

            AntChunkMoveTask left = new AntChunkMoveTask(ants, grid, start, middle);
            AntChunkMoveTask right = new AntChunkMoveTask(ants, grid, middle, end);

            invokeAll(left, right);
        }
    }
}
