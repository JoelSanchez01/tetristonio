package com.cheesecake.tretis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameView gameView = new GameView(this);
		this.setContentView(gameView);
	}
}
