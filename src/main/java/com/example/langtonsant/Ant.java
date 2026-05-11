package com.example.langtonsant;

import javafx.scene.paint.Color;

public class Ant {
    public int x, y;
    public int prevX, prevY;
    public Direction direction;
    public Color color;
    public static int idSeq = 1;
    public int id = 1;

    public Ant(int x, int y, Direction direction){
        this.x = x;
        this.y = y;

        this.direction = direction;
        this.color = Color.web("#0ABAB5");
        this.id = idSeq++;
    }

    public static void resetIdSeq(){
        idSeq = 1;
    }

    public void move(Grid grid){
        boolean wasBlack = grid.getAndFlip(x,y);

        this.prevX = this.x;
        this.prevY = this.y;

        if(wasBlack){
//            System.out.println("right");
            direction = direction.turnRight();

        }else {
//            System.out.println("Left");
            direction = direction.turnLeft();
        }

        x += direction.dx;
        y += direction.dy;

        x = (x + grid.getWidth()) % grid.getWidth();
        y = (y + grid.getHeight()) % grid.getHeight();
    }

}
