package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 *
 * Created by bjbj6 on 2017-10-12.
 *
 */

public class Bullet extends GameObject{


    protected Point bulletPoint;
    protected int bulletSpeed = 40;
    protected int damage;
    protected int shotSpeed;
    protected boolean isFired = false;

    Bullet(Rect rect, Context context) {

        super(rect, context);
        bulletPoint = new Point();

        this.damage = context.getResources().getInteger(R.integer.laserShotDmg);
        this.shotSpeed = context.getResources().getInteger(R.integer.laserShotSpd);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_bullet);
    }

    boolean getIsFired() {
        return isFired;
    }

    public int getDamage() {
       return damage;
    }

    int getShotSpeed() {
        return shotSpeed;
    }


    void fire(Point playerPoint) {
        isFired = true;
        bulletPoint.set(playerPoint.x, playerPoint.y);
    }


    public void update() {
    }

    public void update(View view) {
        bulletPoint.set(bulletPoint.x - bulletSpeed, bulletPoint.y);
        move(bulletPoint);
        if(isOutOfScreen(bulletPoint.x, bulletPoint.y, view)) {
            isFired = false;
        }
    }

    protected boolean isOutOfScreen(int x, int y, View view) {
        if(x < view.getWidth()-rect.width()/2
                && y < view.getHeight()- rect.height()/2
                && x - rect.width()/2 > 0
                && y - rect.height()/2 > 0) {
            return false;
        }
        Log.v("i", x+"---"+y);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public void onDestroy() {

    }
}
