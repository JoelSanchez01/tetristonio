package com.cheesecake.tretis.helpers;

import android.graphics.Point;

public class Block extends Point {
    private int value;

    public Block() {
        super();
    }

    public Block(int value) {
        setValue(value);
    }

    public Block(int x, int y) {
        super(x, y);
    }

    public Block(int x, int y, int value) {
        super(x, y);
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
