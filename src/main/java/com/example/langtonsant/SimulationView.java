package com.example.langtonsant;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.List;

public class SimulationView extends StackPane {

    private Canvas canvas;
    private GraphicsContext graphicalContext;
    private double cellSize = 5;
    private List<Ant> ants;
    private Grid grid;

    public SimulationView(int gridWidth, int gridHeight, double viewWidth, double viewHeight, List<Ant> ants){
        this.ants = ants;

        double totalWidth = gridWidth * cellSize;
        double totalHeight = gridHeight * cellSize;


            double scaleX = viewWidth / gridWidth;
            double scaleY = viewHeight / gridHeight;

            this.cellSize = Math.min(scaleX, scaleY);

//            this.setScaleX(scale);
//            this.setScaleY(scale);

        canvas = new Canvas(gridWidth * cellSize, gridHeight * cellSize);
        graphicalContext = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
        graphicalContext.setFill(Color.WHITE);
        graphicalContext.fillRect(0,0, canvas.getWidth(), canvas.getHeight());


//        graphicalContext.setFill(ant.color);
//        graphicalContext.fillOval(ant.x * cellSize, ant.y * cellSize, cellSize, cellSize);
    }

    public void render(Grid grid){

        graphicalContext.setFill(Color.web("#0ABAB5"));
        for (Ant ant : ants) {
            updateCell(ant.prevX, ant.prevY, grid.isBlack(ant.prevX, ant.prevY));
            updateCell(ant.x, ant.y, grid.isBlack(ant.x, ant.y));

            graphicalContext.setFill(ant.color);
            double antDisplaySize = Math.max( cellSize,1.0);
            graphicalContext.fillOval(
                    ant.x * cellSize,
                    ant.y * cellSize,
                    antDisplaySize,
                    antDisplaySize
            );
        }
    }

    public void updateCell(int x, int y, boolean isBlack){
        graphicalContext.setFill(isBlack ? Color.BLACK : Color.WHITE);
        double visibleSize = Math.max(1.0, cellSize);
        graphicalContext.fillRect(x* cellSize, y * cellSize, visibleSize, visibleSize);
    }

}
