package com.example.langtonsant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class RegionTask extends RecursiveAction {

    private final List<Ant> ants;
    private final Grid grid;
    private final Region region;

    public RegionTask(List<Ant> ants, Grid grid, Region region){
        this.ants = ants;
        this.grid = grid;
        this.region = region;
    }

    @Override
    protected void compute(){
        for (Ant ant : ants) {

//            System.out.println(
//                    "Thread: " + Thread.currentThread().getName() +
//                            " | Region: " + region.startAxis() + "-" + region.endAxis() +
//                            " | Ant ID: " + ant.id +
//                            " | Position: (" + ant.x + "," + ant.y + ")"
//            );

            ant.move(grid);
        }
    }
}
