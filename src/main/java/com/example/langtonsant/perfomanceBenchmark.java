package com.example.langtonsant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class perfomanceBenchmark {
    public static ForkJoinPool forkJoinPool = new ForkJoinPool();
    public static void main(String[] args){
       long totalStartTime = System.currentTimeMillis();


        List<Ant> warmUp = new ArrayList<>();
        System.out.println("Warming up Java Virt Machine!.");
        warmUp.add(new Ant(50, 50, Direction.NORTH));
        runSequentialTest(new sequentialGrid(100, 100), warmUp, 10000);
        runParallelTest(new parallelGrid(100, 100), warmUp, 10000);
        System.out.println("Warm-up using Seq and Parallel complete.");


        int [] antCounts = {1,100, 1000, 10000};
        int [] gridSize = {100, 1000, 10000};
        int steps = 50000;

        for(int size : gridSize){
            long gridStartTime = System.currentTimeMillis();
            System.out.println("\n--- Testing GridL " + size + "x" + size + "---");
            System.out.println("Ants | Seq(ms) | Parallel (ms) | Efficiency | Speed-up ");

            for(int numAnts : antCounts){
                Grid seqGrid = new sequentialGrid(size, size);
                Grid parallelGrid = new parallelGrid(size, size);

                List<Ant> ants = new ArrayList<>();

                for(int i = 0; i < numAnts; i++){
                    ants.add(new Ant(size /2, size / 2, Direction.NORTH));
                }


                long startSeq = System.currentTimeMillis();
                runSequentialTest(seqGrid, ants, steps);
                long endSeq = System.currentTimeMillis();
                long totalSeq = endSeq - startSeq;

                long startPar = System.currentTimeMillis();
                runParallelTest(parallelGrid, ants, steps);
                long endPar = System.currentTimeMillis();
                long totalPar = endPar - startPar;

                double speedUp = (double) totalSeq / totalPar;
                double efficiency = speedUp / Runtime.getRuntime().availableProcessors();

                System.out.printf("%4d | %13d | %13d | %8.2fx | %10.2f%%%n",
                        numAnts, totalSeq, totalPar, efficiency * 100, speedUp );


                System.gc();
//                long gridEndTime = System.currentTimeMillis();
//                double gridDurationSeconds = (gridEndTime - gridStartTime) / 1000.0;
//                System.out.println("___________________");
//                System.out.printf("Total time for %dx%d test suite: %.2f seconds%n",
//                        size, size, gridDurationSeconds);
//                System.out.println("------------------");
            }
        }
    }
    private static void runSequentialTest(Grid grid, List<Ant> ants, int steps){
        for(int i = 0; i < steps; i++){
            for(Ant ant : ants){
                ant.move(grid);
            }
        }
    }

    private static void runParallelTest(Grid grid, List<Ant> ants, int steps){
        int[] oldX = new int[ants.size()];
        int[] oldY = new int[ants.size()];
        int numProcessors = Runtime.getRuntime().availableProcessors();

        for(int s = 0; s < steps; s++){
            int regionHeight = grid.getHeight() / numProcessors;
            List<RegionTask> task = new ArrayList<>();

            Map<Integer, List<Ant>> regionMap = new HashMap<>();
            for(int i = 0; i < numProcessors; i++) regionMap.put(i, new ArrayList<>());

            for (Ant ant : ants) {
                int regionIndex = Math.min(ant.y/ regionHeight, numProcessors -1);
                regionMap.get(regionIndex).add(ant);
            }

            for (int i = 0; i < numProcessors; i++) {
                int startY = i * regionHeight;
                int endY = (i == numProcessors - 1)
                        ? grid.getHeight()
                        : startY + regionHeight;
                task.add(new RegionTask(regionMap.get(i), grid, new Region(startY, endY)));
                Region region = new Region(startY, endY);
            }

            forkJoinPool.invoke(new RecursiveAction() {
                @Override
                protected void compute() {
                    invokeAll(task);
                }
            });
        }

    }
}
