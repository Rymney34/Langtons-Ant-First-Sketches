package com.example.langtonsant;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class RegionTask extends RecursiveAction {
    private List<Ant> ants;
    private Grid grid;
    private final Region region;

    public RegionTask(List<Ant> ants, Grid grid, Region region){
        this.ants = ants;
        this.grid = grid;
        this.region = region;
    }

    @Override
    protected void compute(){
        for(Ant ant : ants){
            if(region.contains(ant)){
                synchronized (ant){
                    ant.move(grid);
                }
            }
        }
    }

}
