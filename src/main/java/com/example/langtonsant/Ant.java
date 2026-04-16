package com.example.langtonsant;

public class Ant {
    public int x, y;
    public Direction direction;

    public Ant(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move(Grid grid){
        if(grid.isBlack(x,y)){
            System.out.println("Left");
            direction = direction.turnLeft();

        }else {
            System.out.println("right");
            direction = direction.turnRight();

        }
        grid.flipCell(x,y);

        x += direction.dx;
        y += direction.dy;

        x = (x + grid.getWidth()) % grid.getWidth();
        y = (y + grid.getHeight()) % grid.getHeight();
    }
}
