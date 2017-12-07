package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by bjbj6 on 2017-11-30.
 */

public class EnemyArmed extends Enemy {

    EnemyLaserBullet[] enemyLaserBullets = new EnemyLaserBullet[10];
    int shotcounter = 0;

    EnemyArmed(Rect rect, Context gameContext, Point position) {
        super(rect, gameContext, position);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.enemy_armed);
        maxHealth = gameContext.getResources().getInteger(R.integer.armedHealth);
        currentHealth = maxHealth;
        damage = gameContext.getResources().getInteger(R.integer.armedDamage);
        for(int i = 0; i < enemyLaserBullets.length; i++) {
            enemyLaserBullets[i] = new EnemyLaserBullet(new Rect(0, 0, 50, 25), gameContext, damage);
        }
    }

    @Override
    boolean update(View view,Player player) {
        shotcounter++;
        for(EnemyLaserBullet bullet : enemyLaserBullets) {
            if(shotcounter%10 == 3 && !bullet.isFired) {
                bullet.fire(position);
            }
            bullet.update(view, player);
        }
        return super.update(view, player);
    }

    @Override
    void draw(Canvas canvas) {
        super.draw(canvas);
        for(EnemyLaserBullet bullet: enemyLaserBullets) {
            bullet.draw(canvas);
        }
    }
}
