package com.cheesecake.tretis;


import android.annotation.SuppressLint;
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
    private final GameThread gameLoopThread;
    int cont = 0, size;
    Paint pen = new Paint();
    int x = 5, y = 0, btn;
    int nf = 0, nr = 0, clk = 0;

    int[][] tetX = {
            {0, 0, 0, 2, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {2, 2, 2, 1, 2, 0, 1},
            {2, 1, 0, 0, 3, 1, 2},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 1, 1},
            {1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 0},
            {2, 2, 2, 2, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 2, 0, 1},
            {0, 1, 2, 0, 3, 1, 2},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 1, 0, 1, 0}
    };

    int[][] tetY = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 1},
            {1, 1, 1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 0, 1},
            {2, 2, 2, 1, 2, 1, 1},
            {2, 1, 0, 2, 3, 1, 2},
            {1, 1, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 1, 1},
            {0, 0, 0, 1, 0, 1, 1},
            {2, 2, 2, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 1, 2, 1, 1},
            {0, 1, 2, 2, 3, 1, 2}
    };

    int[][] btns = new int[][]{
            {50, 1300, 200, 1450},
            {250, 1300, 400, 1450},
            {450, 1300, 600, 1450},
            {650, 1300, 800, 1450},
    };

    Bitmap[] sprites = new Bitmap[8];
    int[][] scn = new int[][]{
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
            {1, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0},
    };


    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameThread(this);
        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp20);
                sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp13);
                sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp23);
                sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp33);
                sprites[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp43);
                sprites[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp53);
                sprites[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp63);
                sprites[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp73);
                size = sprites[0].getWidth();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int i, j;
        int r;
        pen.setTextSize(80);
        pen.setStrokeWidth(4);
        pen.setColor(Color.GREEN);
        canvas.drawColor(Color.BLACK);
        for (i = 0; i < scn.length; i++) {
            for (j = 0; j < scn[0].length; j++) {
                canvas.drawBitmap(sprites[scn[i][j]], 300 + size * j, 300 + size * i, pen);
            }
        }
        r = nr * 4;
        dibujarSegmento(canvas, nf + 1, x + tetX[r][nf], y + tetY[r][nf]);
        dibujarSegmento(canvas, nf + 1, x + tetX[r + 1][nf], y + tetY[r + 1][nf]);
        dibujarSegmento(canvas, nf + 1, x + tetX[r + 2][nf], y + tetY[r + 2][nf]);
        dibujarSegmento(canvas, nf + 1, x + tetX[r + 3][nf], y + tetY[r + 3][nf]);


        DibujarBotones(canvas);

        clk++;
        if (clk == 20) {
            if (esLibre(0, 1)) {
                y++;
            } else {

                establecer_bloque();
                verficarLineas();
                nf = (int) (Math.random() * 7);
                x = 5;
                y = 0;
            }
            clk = 0;
        }

        switch (btn) {
            case 1:
                if (x > 0)
                    if (esLibre(-1, 0)) {
                        x--;
                    }
                break;
            case 2:
                nf = (int) (Math.random() * 7);
                break;
            case 3:
                nr = nextRot(nr);
                break;
            case 4:
                if (x + getMax(tetX, nr, nf) < 11)
                    x++;
                break;
        }
        cont++;
    }

    public int nextRot(int nr) {
        switch (nr) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 0;
            default:
                return nr;
        }
    }


    void establecer_bloque() {
        scn[y + tetY[nr * 4][nf]][x + tetX[nr * 4][nf]] = nf + 1;
        scn[y + tetY[nr * 4 + 1][nf]][x + tetX[nr * 4 + 1][nf]] = nf + 1;
        scn[y + tetY[nr * 4 + 2][nf]][x + tetX[nr * 4 + 2][nf]] = nf + 1;
        scn[y + tetY[nr * 4 + 3][nf]][x + tetX[nr * 4 + 3][nf]] = nf + 1;

    }


    boolean esCompleta(int r) {
        int i;
        for (i = 0; i < scn[0].length; i++) {
            if (scn[r][i] == 0) {
                return false;
            }
        }
        return true;
    }

    void verficarLineas() {
        int i;
        for (i = 0; i < scn.length; i++) {
            if (esCompleta(i)) {
                //recorrer las filas es metodo
                recorrerFilas(i);
            }
        }
    }

    void recorrerFilas(int n) {
        int i, j;
        for (i = n; i > 0; i--) {
            for (j = 0; j < scn[0].length; j++) {
                scn[i][j] = scn[i - 1][j];
            }
        }
    }

    int getMax(int[][] A, int f, int r) {
        int m1 = Math.max(A[r * 4][f], A[r * 4 + 1][f]);
        int m2 = Math.max(A[r * 4 + 2][f], A[r * 4 + 3][f]);
        return Math.max(m1, m2);
    }

    void dibujarSegmento(Canvas canvas, int n, int c, int f) {
        canvas.drawBitmap(sprites[n], 300 + size * c, 300 + size * f, pen);
    }

    boolean esLibre(int ix, int iy) {
        int vx = x + ix, vy = y + iy;
        if (vy + getMax(tetY, nf, nr) > 14)
            return false;
        if (scn[vy + tetY[nr * 4][nf]][vx + tetX[nr * 4][nf]] > 0)
            return false;
        if (scn[vy + tetY[nr * 4 + 1][nf]][vx + tetX[nr * 4 + 1][nf]] > 0)
            return false;
        if (scn[vy + tetY[nr * 4 + 2][nf]][vx + tetX[nr * 4 + 2][nf]] > 0)
            return false;
        return scn[vy + tetY[nr * 4 + 3][nf]][vx + tetX[nr * 4 + 3][nf]] <= 0;
    }

    void DibujarBotones(Canvas canvas) {
        for (int[] ints : btns) {
            canvas.drawRect(ints[0], ints[1], ints[2], ints[3], pen);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int tchx = (int) event.getX();
        int tchy = (int) event.getY();
        btn = 0;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < btns.length; i++) {
                if (tchx > btns[i][0] && tchx < btns[i][2] && tchy > btns[i][1] && tchy < btns[i][3]) {
                    btn = i + 1;
                    break;
                }
            }
        }
        return true;
    }
}