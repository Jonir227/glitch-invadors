package com.example.bjbj6.glitchinvadors;


import android.graphics.Canvas;

/**
 * Created by bjbj6 on 2017-10-02.
 */



public interface GameObject {
    void update();
    void draw(Canvas canvas);
    void onDestroy();
}
