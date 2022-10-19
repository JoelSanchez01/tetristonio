package com.cheesecake.tretis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private final SurfaceHolder holder; // I think this might be declared in the Constructor context... but idk
	private final GameThread gameLoopThread;

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
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
		// Just because I'm pretty fussy about the warnings
		@SuppressLint("DrawAllocation") // You can delete this two lines ^^^
		Paint pen = new Paint();
		pen.setStrokeWidth(4);
		pen.setColor(Color.GREEN);
		canvas.drawColor(Color.BLACK);
		canvas.drawRect(100, 400, 500, 300, pen);
	}
}
