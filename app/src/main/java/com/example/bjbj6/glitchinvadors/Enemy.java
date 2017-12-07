package com.example.bjbj6.glitchinvadors;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by bjbj6 on 2017-11-14.
 */

public class Enemy extends GameObject {

    protected int maxHealth = 0;
    protected int currentHealth = 0;
    protected int damage = 0;

    protected Point position;
    protected int movementSpeed = 10;

    public Enemy(Rect rect, Context gameContext, Point position) {
        super(rect, gameContext);
        this.position = new Point(position);

        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.enemy_gunship);
        maxHealth = gameContext.getResources().getInteger(R.integer.gunshipHealth);
        damage = gameContext.getResources().getInteger(R.integer.armedDamage);
        currentHealth = maxHealth;
    }


    public boolean damageDealt(int damage) {
        currentHealth -= damage;
;
        if(currentHealth <= 0) {
            onDestroy();
            return true;
        }
        return false;
    }

    @Override
    void update() {
    }

    boolean update(View view, Player player) {
        position.set(position.x + movementSpeed, position.y);
        move(position);
        checkPlayer(player);
        return isOutOfScreen(position.x, position.y, view);
    }

    void checkPlayer(Player player) {
        if(player.hitCheck(this)) {
            player.damageDealt(damage);
        }
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }
    @Override
    void onDestroy() {
        int val = (int)(Math.random()*10) % 4;
        StageView stageView = (((Activity)gameContext).findViewById(R.id.stageView));
        stageView.spawnPickable(val, this.position);
    }
}
