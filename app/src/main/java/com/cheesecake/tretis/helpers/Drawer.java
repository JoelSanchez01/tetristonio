package com.cheesecake.tretis.helpers;

import android.graphics.Canvas;

public class Drawer {
    public static void init(Canvas canvas) {
        for (int i = 0; i < Tetris.height; i++)
            for (Block block : Tetris.screen[i])
                drawBlock(canvas, block);
    }

    public static void drawBlock(Canvas canvas, Block block) {
        canvas.drawBitmap(Tetris.sprites[block.getValue()], block.x, block.y, Tetris.pen);
    }
}
