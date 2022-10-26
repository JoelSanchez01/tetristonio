package com.cheesecake.tretis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		GameView vista = new GameView(this);
		super.onCreate(savedInstanceState);
		setContentView(vista);


	}
}
