package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;


/**
 * Created by bjbj6 on 2017-10-06.
 */

public class Player extends GameObject {

    private int MAX_HEALTH;
    private int currentHealth;

    public Player(Rect rect, Context context) {
        super(rect, context);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.ship);
        MAX_HEALTH = context.getResources().getInteger(R.integer.playerHealth);
        currentHealth = MAX_HEALTH;
    }


    //Player 기능


    /// player 상호작용
    private void getDamage(int damage) {
        currentHealth -= damage;
    }

    public void hitboxCheck(Rect hitRect, int damage) {
        if(rect.intersect(hitRect)) {
            this.getDamage(damage);
        }
    }




    //로드, 로직

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }


    @Override
    public void update() {
    }

    public void update(Point point) {
        //l, t, r , b
        move(point);
        if(currentHealth <= 0) {
            onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        //플레이어 죽을때

    }
}
