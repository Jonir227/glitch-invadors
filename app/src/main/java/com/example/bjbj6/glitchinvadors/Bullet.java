package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by bjbj6 on 2017-10-12.
 */

public class Bullet implements GameObject{

    private Rect rect;
    private Bitmap bitmap;

    public Bullet(Rect rect, Context context) {
        this.rect = rect;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.)
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onDestroy() {

    }
}
