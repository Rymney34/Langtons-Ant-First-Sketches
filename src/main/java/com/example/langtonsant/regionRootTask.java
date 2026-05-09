package com.example.langtonsant;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class regionRootTask extends RecursiveAction {
    private final List<RegionTask> tasks;

    public regionRootTask(List<RegionTask> tasks){
        this.tasks = tasks;
    }

    @Override
    protected void compute(){
        invokeAll(tasks);
    }
}
