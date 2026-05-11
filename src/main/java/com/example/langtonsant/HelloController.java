package com.example.langtonsant;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import static com.example.langtonsant.Ant.resetIdSeq;

public class HelloController {
    @FXML private ToggleGroup modeGroup;
    @FXML private RadioMenuItem seqId;
    @FXML private RadioMenuItem parallelId;
    @FXML
    private AnchorPane simulationContainer;
    public long SLEEP_TIME = 100000000;
    AnimationTimer timer;
    private SimulationView simulationView;
    private Grid grid;
    private sequentialGrid grid2;
    private Ant ant;
    private List<Ant> ants = new ArrayList<>();
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    private boolean parallelMode = false;

//call thread on each initialize
    public void initialize(){
        int width = 100;
        int heigh = 100;
        seqId.setSelected(true);
        double winWidth = 1080;
        double winHeight  = 720;

        grid = new sequentialGrid(width, heigh);
        parallelMode = false;
        ants.add(new Ant(width / 2, heigh / 2, Direction.NORTH));
        ants.add(new Ant(25, 35, Direction.SOUTH));
        ants.add(new Ant(width / 3, heigh / 3, Direction.SOUTH));
        updateAntCountUI();
        simulationView = new SimulationView(width, heigh, winWidth, winHeight, ants);

        simulationContainer.getChildren().add(simulationView);

        AnchorPane.setTopAnchor(simulationView, 0.0);
        AnchorPane.setBottomAnchor(simulationView, 0.0);
        AnchorPane.setLeftAnchor(simulationView, 0.0);
        AnchorPane.setRightAnchor(simulationView, 0.0);

        directionChoice.getItems().addAll(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
        );
        directionChoice.setValue(Direction.NORTH);

        startSimulation();
    }
    private void moveAntSequential(){
        for (Ant ant : ants) {
            int oldX = ant.x;
            int oldY = ant.y;

            ant.move(grid);
            System.out.println("Sequential:" + Thread.currentThread().getName());
            simulationView.updateCell(oldX, oldY, grid.isBlack(oldX, oldY));
        }
    }

private void moveAntsParllel() {
    int[] oldX = new int[ants.size()];
    int[] oldY = new int[ants.size()];

    for (int i = 0; i < ants.size(); i++) {
        oldX[i] = ants.get(i).x;
        oldY[i] = ants.get(i).y;
    }

    int numberRegions = Math.min(
            Runtime.getRuntime().availableProcessors(),
            grid.getHeight()
    );

    int regionHeight = grid.getHeight() / numberRegions;

    List<Region> regions = new ArrayList<>();
    Map<Region, List<Ant>> regionAnts = new HashMap<>();

    for (int i = 0; i < numberRegions; i++) {
        int startY = i * regionHeight;
        int endY = (i == numberRegions - 1)
                ? grid.getHeight()
                : startY + regionHeight;

        Region region = new Region(startY, endY);
        regions.add(region);
        regionAnts.put(region, new ArrayList<>());
    }

    for (Ant ant : ants) {
        for (Region region : regions) {
            if (region.contains(ant)) {
                regionAnts.get(region).add(ant);
                break;
            }
        }
    }

    List<RegionTask> tasks = new ArrayList<>();
    for (Region region : regions) {
        tasks.add(new RegionTask(regionAnts.get(region), grid, region));
    }

    forkJoinPool.invoke(new RecursiveAction() {
        @Override
        protected void compute() {
            invokeAll(tasks);
        }
    });

    for (int i = 0; i < ants.size(); i++) {
        simulationView.updateCell(oldX[i], oldY[i],
                grid.isBlack(oldX[i], oldY[i]));

        simulationView.updateCell(ants.get(i).x, ants.get(i).y,
                grid.isBlack(ants.get(i).x, ants.get(i).y));
    }
}

    private void startSimulation() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (now - lastUpdate >= SLEEP_TIME) {

                    if (SLEEP_TIME == 0) {
                        for (int i = 0; i < 100; i++) {
                            if (parallelMode) moveAntsParllel();
                            else moveAntSequential();
                        }
                    } else {
                        if (parallelMode) moveAntsParllel();
                        else moveAntSequential();
                    }
                    simulationView.render(grid);
                    lastUpdate = now;
                }
            }
        };
    }

    protected void resetSimulation() {
        int width = 100;
        int heigh = 100;

        double winWidth = simulationContainer.getWidth();
        double winHeight  = simulationContainer.getHeight();
        ants.clear();
        resetIdSeq();
        if(parallelMode){
            grid = new parallelGrid(width, heigh);

        }else{
            grid = new sequentialGrid(width, heigh);
        }
        ants.add(new Ant(width / 2, heigh / 2, Direction.NORTH));
        updateAntCountUI();
        simulationView = new SimulationView(width, heigh, winWidth, winHeight, ants);

        AnchorPane.setTopAnchor(simulationView, 10.0);
        AnchorPane.setBottomAnchor(simulationView, 0.0);
        AnchorPane.setLeftAnchor(simulationView, 0.0);
        AnchorPane.setRightAnchor(simulationView, 0.0);

        simulationContainer.getChildren().clear();
        simulationContainer.getChildren().add(simulationView);

        startSimulation();
    }

    @FXML
    protected void startSim() {
        timer.start();
    }

    @FXML
    protected void pauseSim() {
        timer.stop();
    }

    @FXML
    protected void resumeSim() {
        timer.start();
    }

    @FXML
    protected void resetSim() {
        timer.stop();
        resetSimulation();
        timer.start();
    }

    @FXML
    protected void speedSlow() {
        SLEEP_TIME  = 200000000;
    }

    @FXML
    protected void speedNormal() {
        SLEEP_TIME = 100000000;
    }
    @FXML
    protected void speedFast() {
        SLEEP_TIME = 5000000;
    }
    @FXML
    protected void speedVeryFast() {
        SLEEP_TIME = 0;
    }

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private ChoiceBox<Direction> directionChoice;



    @FXML
    protected void addAnt() {
       try{
           int x = Integer.parseInt(xField.getText());
           int y = Integer.parseInt(yField.getText());
           Direction dir = directionChoice.getValue();

           ants.add(new Ant(x, y, dir));
            updateAntCountUI();
           xField.clear();
           yField.clear();
           directionChoice.setValue(Direction.NORTH);

           System.out.println("Ant added at " + x + ',' + y);
       }catch (NumberFormatException e){
           System.out.println("Invalid input");
       }
    }

    private String mode = "SEQUENTIAL";

    @FXML
    protected void useSequentialMode() {

        mode = "SEQUENTIAL";

            timer.stop();
            resetSimulation();
            int width = grid.getWidth();
            int height = grid.getHeight();

            grid = new sequentialGrid(width, height);
            parallelMode = false;

            ants.clear();
            ants.add(new Ant(width / 2, height / 2, Direction.NORTH));
            updateAntCountUI();

            System.out.println("Sequential mode enabled");

            timer.start();

    }

    @FXML
    protected void useParallelMode() {
        timer.stop();
        mode = "PARALLEL";
        resetSimulation();
        int width = grid.getWidth();
        int height = grid.getHeight();

        grid = new parallelGrid(width, height);

        parallelMode = true;

            ants.clear();
            ants.add(new Ant(width / 2, height / 2, Direction.NORTH));
            System.out.println("Parallel mode enabled");
            updateAntCountUI();
            timer.start();

    }

    @FXML
    private javafx.scene.control.MenuItem gridSmall;
    @FXML
    private  javafx.scene.control.MenuItem gridMedium;
    @FXML
    private  javafx.scene.control.MenuItem gridLarge;

    @FXML
    public void handleGridResize (javafx.event.ActionEvent event){
        MenuItem source = (MenuItem) event.getSource();

        int newWidth, newHeight;

        if(source == gridSmall){
            newWidth = 100;
            newHeight = 100;
        } else if (source == gridMedium) {
            newWidth = 1000;
            newHeight = 1000;
        } else{
            newWidth = 10000;
            newHeight = 10000;
        }

        applyNewGridSize(newWidth, newHeight);
    }

    private void applyNewGridSize(int width, int height){
        timer.stop();

        double fixedCellSize = 2.0;


        this.grid = new sequentialGrid(width, height);

        ants.clear();
        ants.add(new Ant(width / 2, height / 2, Direction.NORTH));
        updateAntCountUI();
        double totalWidth = width * fixedCellSize;
        double totalHeight = height * fixedCellSize;

        double currentWinW = simulationContainer.getWidth();
        double currentWinH = simulationContainer.getHeight();

        simulationView = new SimulationView(width, height, currentWinW, currentWinH, ants);
        AnchorPane.setTopAnchor(simulationView, 0.0);
        AnchorPane.setBottomAnchor(simulationView, 0.0);
        AnchorPane.setLeftAnchor(simulationView, 0.0);
        AnchorPane.setRightAnchor(simulationView, 0.0);

        simulationContainer.getChildren().clear();
        simulationContainer.getChildren().add(simulationView);
    }

    @FXML
    private Label antCountLabel;

    private void updateAntCountUI(){
        if(antCountLabel != null){
            antCountLabel.setText(String.valueOf(ants.size()));
        }
    }





}
