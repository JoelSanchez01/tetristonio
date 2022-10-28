package com.cheesecake.tretis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView vista = new GameView(this);
        super.onCreate(savedInstanceState);
        setContentView(vista);
    }
}
