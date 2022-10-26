package com.cheesecake.tretis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private final GameThread gameLoopTheread;
    int count = 0, size;
    int x = 5, y = 0;
    Paint pen = new Paint();
    int btn = 0;

    int[][] BTS = new int[][]{
            {50, 1300, 200, 1450},
            {250, 1300, 400, 1450},
            {450, 1300, 600, 1450},
            {650, 1300, 800, 1450},
    };

    Bitmap[] sprites = new Bitmap[8];

    int nf = 0, nr = 0;

    int[][] tetX = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
    };

    int[][] tetY = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},

            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
    };

    int[][] screen = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    public GameView(Context context) {
        super(context);
        gameLoopTheread = new GameThread(this);
        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp20);
                sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp13);
                sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp23);
                sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp33);
                size = sprites[0].getWidth();
                gameLoopTheread.setRunning(true);
                gameLoopTheread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopTheread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopTheread.join();
                        retry = false;
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int i, j;

        pen.setTextSize(20);
        pen.setStrokeWidth(4);
        pen.setColor(Color.GREEN);
        canvas.drawColor(Color.BLACK);

        for (i = 0; i < screen.length; i++) {
            for (j = 0; j < screen[0].length; j++) {
                canvas.drawBitmap(sprites[screen[i][j]], 100 + size * j, 100 + size * i, pen);
            }
        }

        int r = nr * 4;
        canvas = drawSegment(canvas, 3, x + tetX[r][nf], y);
        canvas = drawSegment(canvas, 3, x + tetX[r + 1][nf], y);
        canvas = drawSegment(canvas, 3, x + tetX[r + 2][nf], y);
        canvas = drawSegment(canvas, 3, x + tetX[r + 3][nf], y);

        canvas = drawButtons(canvas);
        y++;
        switch (btn) {
            case 1:
                x--;
                break;
            case 3:
                x++;
                break;
        }

        //  canvas.drawText("hola "+cont,120,620,pen );
        count++;
        //este metodo es el encargado de dibujar  *****
    }

    Canvas drawSegment(Canvas canvas, int n, int c, int f) {
        canvas.drawBitmap(sprites[n], 100 + size * c, 100 + size * f, pen);
        return canvas;
    }

    Canvas drawButtons(Canvas canvas) {
        for (int i = 0; i < BTS.length; i++) {
            canvas.drawRect(BTS[i][0], BTS[i][1], BTS[i][2], BTS[i][3], pen);
        }
        return canvas;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int tchx = (int) event.getX();
        int tchy = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < BTS.length; i++) {
                if (tchx > BTS[i][0] && tchx < BTS[i][2] && tchy > BTS[i][1] && tchy < BTS[i][3]) {
                    btn = i + 1;
                    break;
                }
            }
        }
        return true;
    }
}
