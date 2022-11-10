package com.cheesecake.tretis;

import android.graphics.Canvas;

public class GameThread extends Thread {
    static final long FPS = 50;
    private final GameView gameView;
    private boolean running = false;

    public GameThread(GameView view) {
        this.gameView = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.onDraw(c);
                }
            } finally {
                if (c != null) {
                    gameView.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) sleep(sleepTime);
                else sleep(10);
            } catch (Exception e) {

            }
        }
    }
}
