package com.example.langtonsant;

public record Region(int startAxis, int endAxis) {
    public boolean contains(Ant ant){
        return ant.y >= startAxis && ant.y < endAxis;
    }
}
