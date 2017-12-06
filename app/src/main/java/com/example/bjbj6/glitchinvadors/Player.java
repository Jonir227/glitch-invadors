package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;


/**
 * Created by bjbj6 on 2017-10-06.
 */

public class Player extends GameObject {

    private int MAX_HEALTH;
    private int currentHealth;
    private StageView stageView;
    private boolean isHitted = false;
    private Paint paint = new Paint();
    private int mightyTime = 45;
    private int currentFrame = 0;

    public Player(Rect rect, Context context, StageView stageView) {
        super(rect, context);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.ship);
        this.stageView = stageView;
        MAX_HEALTH = context.getResources().getInteger(R.integer.playerHealth);
        currentHealth = MAX_HEALTH;
        paint.setAlpha(80);
    }

    //Player 기능


    /// player 상호작용
    public void damageDealt(int damage) {
        if(!isHitted) {
            currentHealth -= damage;
            Log.v("i", currentHealth+"");
            isHitted = true;
            if(currentHealth <= 0) {
                onDestroy();
            }
        }
    }


    //로드, 로직

    @Override
    public void draw(Canvas canvas) {
        if(currentFrame % 5 == 0 && currentFrame != 0) {
            Log.v("i", "alapha affected");
            canvas.drawBitmap(bitmap, null, rect, paint);
        } else {
            canvas.drawBitmap(bitmap, null, rect, null);
        }
    }


    @Override
    public void update() {
    }

    public void update(Point point) {
        //l, t, r , b
        move(point);
        if(isHitted) {
            currentFrame++;
            if(currentFrame == mightyTime) {
                isHitted = false;
                currentFrame = 0;
            }
        }
    }

    @Override
    public void onDestroy() {
        //플레이어 죽을때
        stageView.gameEnd();
    }
}
