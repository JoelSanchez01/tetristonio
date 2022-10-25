package com.cheesecake.tretis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private final SurfaceHolder holder; // I think this might be declared in the Constructor context... but idk
	private final GameThread gameLoopThread;

	Bitmap[] sprites = new Bitmap[8];

	int[][] scene = new int[4][6];

	int size;

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
				sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sq0);
				sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sq1);
				sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sq2);

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
		// Just because I'm pretty fussy about the warnings
		@SuppressLint("DrawAllocation") // You can delete this two lines ^^^
		Paint pen = new Paint();
		pen.setStrokeWidth(4);
		pen.setColor(Color.GREEN);
		canvas.drawColor(Color.BLACK);

		for(i = 0; i < scene.length; i++) {
			for(j = 0; i < scene.length; i++) {
				canvas.drawBitmap(sprites[scene[i][j]], 300 + size * j, 300 + size * i, pen);
			}
		}
	}
}
