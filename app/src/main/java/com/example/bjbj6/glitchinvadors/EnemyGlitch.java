package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by bjbj6 on 2017-12-06.
 */

public class EnemyGlitch extends Enemy {

    private int moveX = 0;
    private int moveY = 0;
    private int frame = 0;
    private Bitmap bitmapTmp;


    public EnemyGlitch(Rect rect, Context gameContext, Point position, Point playerPoint) {
        super(rect, gameContext, position);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch1);
        bitmapTmp = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch2);
        maxHealth = gameContext.getResources().getInteger(R.integer.glitchHealth);
        damage = gameContext.getResources().getInteger(R.integer.glitchDamage);
        currentHealth = maxHealth;
        movementSpeed = 15;
        int xVal = playerPoint.x - position.x;
        int yVal = playerPoint.y - position.y;
        double crossVal = Math.sqrt(xVal*xVal + yVal*yVal);
        double sinVal = xVal/crossVal;
        double cosVal = yVal/crossVal;
        moveX = (int)(movementSpeed*sinVal);
        moveY = (int)(movementSpeed*cosVal);
    }

    @Override
    boolean update(View view, Player player) {
        frame++;
        if(frame % 5 == 0) {
            bitmap = bitmapTmp;
        } else {
            bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch1);
        }
        position.set(position.x + moveX, position.y + moveY);
        move(position);
        checkPlayer(player);
        return isOutOfScreen(position.x, position.y, view);
    }
}
