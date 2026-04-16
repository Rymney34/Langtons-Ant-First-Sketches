package com.example.langtonsant;

public enum Direction {

    NORTH(0, -1),
    EAST(1,0),
    SOUTH(0,1),
    WEST(-1,0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnRight(){
        int nextOrdinal = (this.ordinal()+1) % Direction.values().length;
        return Direction.values()[nextOrdinal];
    }

    public  Direction turnLeft(){
        int nextOrdinal = (this.ordinal() - 1 + Direction.values().length) % Direction.values().length;
        return Direction.values()[nextOrdinal];
    }

}
