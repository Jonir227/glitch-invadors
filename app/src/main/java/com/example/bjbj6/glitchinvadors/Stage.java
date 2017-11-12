package com.example.bjbj6.glitchinvadors;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Stage extends Activity {


    final int LASER_GUN = 1;
    final int SHOT_GUN = 2;
    final int ROCKET_GUN = 3;

    ImageView controller;
    StageView stageView;
    ImageView laserBtn;
    ImageView shotgunBtn;
    ImageView rocketBtn;


    float[] moveValue = new float[2];

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        //View 할당
        controller = findViewById(R.id.imageView4);
        stageView = findViewById(R.id.stageView);
        laserBtn = findViewById(R.id.imageView5);
        shotgunBtn = findViewById(R.id.imageView2);
        rocketBtn = findViewById(R.id.imageView3);


        controller.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int[] loc = new int[2];
                view.getLocationOnScreen(loc);
                float touchX = motionEvent.getRawX();
                float touchY = motionEvent.getRawY();
                loc[0] += view.getWidth()/2;
                loc[1] += view.getHeight()/2;
                moveValue[0] = touchX - loc[0];
                moveValue[1] = touchY - loc[1];
                stageView.getControlInput((int)(moveValue[0]/10), (int)(moveValue[1]/10));
                return true;
            }
        });
    }

    void setLaser(View view) {
        Log.v("i", "Laser Shot");
        stageView.changeFire(LASER_GUN);
    }

    void setShotgun(View view) {
        Log.v("i", "Shotgun shot");
        stageView.changeFire(SHOT_GUN);
    }

    void setRocket(View view) {
        Log.v("i", "Rocket shot");
    }



}
