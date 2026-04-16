package com.example.langtonsant;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane simulationContainer;

    private SimulationView simulationView;
    private Grid grid;
    private Ant ant;
    private List<Ant> ants = new ArrayList<>();

    public void initialize(){
        int width = 100;
        int heigh = 100;
        System.out.println("Gazoz");
        grid = new Grid(width, heigh);
        ant = new Ant(width / 2, heigh / 2, Direction.NORTH);
        simulationView = new SimulationView(width, heigh);

        simulationContainer.getChildren().add(simulationView);

        startSimulation();
    }

    private void startSimulation() {
        AnimationTimer timer = new AnimationTimer(){
            private long lastUpdate = 0;

            private final long SLEEP_TIME = 100;
            @Override
            public void handle(long l) {

                if(l - lastUpdate >= SLEEP_TIME){
                    int oldX = ant.x;
                    int oldY = ant.y;

                    ant.move(grid);

                    simulationView.updateCell(oldX,oldY, grid.isBlack(oldX,oldY));
                    lastUpdate = l;
                }

            }

        };
        timer.start();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
