package com.example.langtonsant;

import javafx.scene.paint.Color;

public class Ant {
    public int x, y;
    public Direction direction;
    public Color color;

    public Ant(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = Color.web("#0ABAB5");
    }

//    public void move(Grid grid){
//        if(grid.isBlack(x,y)){
//            System.out.println("Left");
//            direction = direction.turnLeft();
//
//        }else {
//            System.out.println("right");
//            direction = direction.turnRight();
//
//        }
//        grid.flipCell(x,y);
//
//        x += direction.dx;
//        y += direction.dy;
//
//        x = (x + grid.getWidth()) % grid.getWidth();
//        y = (y + grid.getHeight()) % grid.getHeight();
//    }
    public void move(Grid grid){
        boolean wasBlack = grid.getAndFlip(x,y);


        if(wasBlack){

            System.out.println("right");
            direction = direction.turnRight();

        }else {
            System.out.println("Left");
            direction = direction.turnLeft();

        }


        x += direction.dx;
        y += direction.dy;

        x = (x + grid.getWidth()) % grid.getWidth();
        y = (y + grid.getHeight()) % grid.getHeight();
    }

}
