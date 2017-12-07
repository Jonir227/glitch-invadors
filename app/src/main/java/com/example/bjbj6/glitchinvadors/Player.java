package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL;


/**
 * Created by bjbj6 on 2017-10-06.
 */

public class Player extends GameObject {

    private int MAX_HEALTH;
    private int currentHealth;
    private Point before = new Point();
    private StageView stageView;
    private boolean isHitted = false;
    private Paint paint = new Paint();
    private int mightyTime = 45;
    private int currentFrame = 0;

    private Rect[] glitchBlocks = new Rect[20];
    private boolean[] activeBlock = new boolean[20];
    private Bitmap glitchBitmap;

    public Player(Rect rect, Context context, StageView stageView) {
        super(rect, context);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.ship);
        glitchBitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch1);

        for(int i = 0; i < glitchBlocks.length; i++) {
            glitchBlocks[i] = new Rect();
        }

        this.stageView = stageView;
        MAX_HEALTH = context.getResources().getInteger(R.integer.playerHealth);
        currentHealth = MAX_HEALTH;
        paint.setAlpha(80);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    //Player 기능
    public void addGlitch(Rect rect) {
        for(int i = 0; i < activeBlock.length; i++) {
            if(!activeBlock[i]){
                glitchBlocks[i].set(rect);
                activeBlock[i] = true;
                return;
            }
        }
    }
    public void addHealth(int val) {
        currentHealth += val;
        if(currentHealth > 10) {
            currentHealth = 10;
        }
    }
    public void removeGlitch(int val) {
        for(int i = activeBlock.length-1; i >= 0; i--) {
            if(activeBlock[i]) {
                activeBlock[i] = false;
                if(val-- == 0) return;
            }
        }
    }


    /// player 상호작용
    public void damageDealt(int damage) {
        if(!isHitted) {
            currentHealth -= damage;
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
            canvas.drawBitmap(bitmap, null, rect, paint);
            for(int i = 0; i < activeBlock.length; i++) {
                if(activeBlock[i]){
                    canvas.drawBitmap(glitchBitmap, null, glitchBlocks[i], paint);
                }
            }
        } else {
            canvas.drawBitmap(bitmap, null, rect, null);
            for(int i = 0; i < activeBlock.length; i++) {
                if(activeBlock[i]){
                    canvas.drawBitmap(glitchBitmap, null, glitchBlocks[i], null);
                }
            }
        }
    }

    @Override
    public boolean hitCheck(GameObject gameObject) {
        for(int i = 0; i < activeBlock.length; i++) {
            if(activeBlock[i]){
                if(Rect.intersects(glitchBlocks[i], gameObject.rect))
                    return true;
            }
        }
        return super.hitCheck(gameObject);
    }

    @Override
    public void update() {
    }

    public void update(Point point) {
        //l, t, r , b
        move(point);
        int moveX = point.x - before.x;
        int moveY = point.y - before.y;
        for(int i = 0; i < activeBlock.length; i++) {
            if(activeBlock[i])
                glitchBlocks[i].set(glitchBlocks[i].left + moveX, glitchBlocks[i].top + moveY, glitchBlocks[i].right + moveX, glitchBlocks[i].bottom + moveY);
        }
        if(isHitted) {
            currentFrame++;
            if(currentFrame == mightyTime) {
                isHitted = false;
                currentFrame = 0;
            }
        }
        before.set(point.x, point.y);
    }

    @Override
    public void onDestroy() {
        //플레이어 죽을때
        stageView.gameEnd();
    }
}
