package com.example.langtonsant;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.List;

public class SimulationView extends StackPane {

    private Canvas canvas;
    private GraphicsContext graphicalContext;
    private int cellSize = 5;
    private List<Ant> ants;

    public SimulationView(int width, int height, List<Ant> ants){
        this.ants = ants;
        canvas = new Canvas(width * cellSize, height * cellSize);
        graphicalContext = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        graphicalContext.setFill(Color.WHITE);
        graphicalContext.fillRect(0,0, canvas.getWidth(), canvas.getHeight());


//        graphicalContext.setFill(ant.color);
//        graphicalContext.fillOval(ant.x * cellSize, ant.y * cellSize, cellSize, cellSize);
    }

    public void render(){


        graphicalContext.setFill(Color.web("#0ABAB5"));
        for(Ant ant : ants){
            graphicalContext.fillOval(ant.x * cellSize, ant.y * cellSize, cellSize, cellSize);
        }
    }

    public void updateCell(int x, int y, boolean isBlack){
        graphicalContext.setFill(isBlack ? Color.BLACK : Color.WHITE);
        graphicalContext.fillRect(x* cellSize, y * cellSize, cellSize, cellSize);
    }

}
