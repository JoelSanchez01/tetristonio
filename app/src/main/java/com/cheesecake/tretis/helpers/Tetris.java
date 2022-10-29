package com.cheesecake.tretis.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import com.cheesecake.tretis.R;

public class Tetris {
    public static double score;
    public static Block[][] screen;

    public static final int widht = 12;
    public static final int height = 15;
    public static final int offset = 100;

    public static final Bitmap[] sprites = new Bitmap[8];
    public static int tetrominoSize;

    public static Paint pen = new Paint();

    public static void init(Context context) {
        screen = new Block[height][widht];

        sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp20);
        sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp13);
        sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp23);
        sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp33);
        sprites[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp43);
        sprites[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp53);
        sprites[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp63);
        sprites[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp73);

        tetrominoSize = sprites[0].getWidth();

        for(int i = 0; i < height; i++)
            for(int j = 0; j < widht; j++)
                screen[i][j] = new Block(
                        offset + j * tetrominoSize,
                        offset + i * tetrominoSize,
                        0);
    }
}
