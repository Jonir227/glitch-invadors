package com.example.bjbj6.glitchinvadors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static Toast toast;

    public void startGame(View view) {
        toast = Toast.makeText(this, "로딩중입니다. 기다려주세요.", Toast.LENGTH_LONG);
        toast.show();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, Stage.class);
        setContentView(R.layout.activity_main);
    }
}
