package com.cheesecake.tretis;

import android.graphics.Canvas;

public class GameThread extends Thread {
    private static final byte FPS = 24;
    private final GameView gameView;
    private boolean running = false;

    public GameThread(GameView view) {
        this.gameView = view;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        byte ticksPS = 1000 / FPS;
        long startTime = 0;
        long sleepTime;

        while(running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();

            try {
                c = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.onDraw(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(c != null) {
                    gameView.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

            try {
                if(sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
