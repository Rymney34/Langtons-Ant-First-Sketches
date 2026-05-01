package com.example.langtonsant;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

//import static sun.jvm.hotspot.oops.MethodData.cellSize;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane simulationContainer;
    public long SLEEP_TIME = 100;
    AnimationTimer timer;
    private SimulationView simulationView;
    private Grid grid;
    private sequentialGrid grid2;
    private Ant ant;
    private List<Ant> ants = new ArrayList<>();
//call thread on each initialize
    public void initialize(){
        int width = 100;
        int heigh = 100;
        System.out.println("Gazoz");
        grid = new sequentialGrid(width, heigh);

//        ant = new Ant(width / 2, heigh / 2, Direction.NORTH);
        ants.add(new Ant(width / 2, heigh / 2, Direction.NORTH));
        ants.add(new Ant(25, 35, Direction.SOUTH));
        ants.add(new Ant(width / 3, heigh / 3, Direction.SOUTH));
        simulationView = new SimulationView(width, heigh, ants);


        simulationContainer.getChildren().add(simulationView);


        directionChoice.getItems().addAll(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
        );
        directionChoice.setValue(Direction.NORTH);

        startSimulation();
    }

    private void startSimulation() {
       timer = new AnimationTimer(){
            private long lastUpdate = 0;


            @Override
            public void handle(long l) {

                if(l - lastUpdate >= SLEEP_TIME){
                    for(Ant ant : ants){
                        int oldX = ant.x;
                        int oldY = ant.y;

                        ant.move(grid);
                        simulationView.updateCell(oldX,oldY, grid.isBlack(oldX,oldY));

                    }
                    simulationView.render();


                    lastUpdate = l;
                }
            }

        };
//        timer.start();

    }

    protected void resetSimulation() {
        int width = 100;
        int heigh = 100;
        ants.clear();
        grid = new sequentialGrid(width, heigh);
        ants.add(new Ant(width / 2, heigh / 2, Direction.NORTH));
        simulationView = new SimulationView(width, heigh,ants);

        simulationContainer.getChildren().add(simulationView);

        startSimulation();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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
        SLEEP_TIME = 50000000;
    }
    @FXML
    protected void speedVeryFast() {
        SLEEP_TIME = 1000;
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

           xField.clear();
           yField.clear();
           directionChoice.setValue(Direction.NORTH);

           System.out.println("Ant added at " + x + ',' + y);
       }catch (NumberFormatException e){
           System.out.println("Invalid input");
       }
    }





}
