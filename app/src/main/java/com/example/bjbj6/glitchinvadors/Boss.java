package com.example.bjbj6.glitchinvadors;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by bjbj6 on 2017-12-07.
 */

public class Boss extends Enemy{

    int frameCounter = 0;
    boolean spawning = false;
    int spawnCounter = 0;
    EnemyLaserBullet[] enemyLaserBullets = new EnemyLaserBullet[20];
    ConcurrentLinkedQueue<EnemyGlitch> enemyGlitches = new ConcurrentLinkedQueue<>();
    Point playerPoint;
    StageView stageView;

    Boss(Rect rect, Context gameContext, Point positsion, Point playerPoint) {
        super(rect, gameContext, positsion);
        maxHealth = gameContext.getResources().getInteger(R.integer.bossHealth);
        this.playerPoint = playerPoint;
        damage = gameContext.getResources().getInteger(R.integer.bossDmg);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitched_earth);
        stageView = ((Activity)gameContext).findViewById(R.id.stageView);
        currentHealth = maxHealth;
        for(int i = 0; i < enemyLaserBullets.length; i++) {
            enemyLaserBullets[i] = new EnemyLaserBullet(new Rect(0,0,60,30), gameContext, damage);
        }
    }


    @Override
    public boolean damageDealt(int damage) {
        return super.damageDealt(damage);
    }

    @Override
    boolean update(View view, Player player) {
        frameCounter++;
        for(int i = 0; i < enemyLaserBullets.length; i+=4) {
            if((frameCounter/10)%10 == 0 && !enemyLaserBullets[i].isFired) {
                enemyLaserBullets[i].fire(new Point(position.x, position.y + 25));
                enemyLaserBullets[i+1].fire(new Point(position.x, position.y +75));
                enemyLaserBullets[i+2].fire(new Point(position.x, position.y -25));
                enemyLaserBullets[i+3].fire(new Point(position.x, position.y - 75));
            }
            if(frameCounter%110 == 0) {
                spawning = true;
            }
            if(spawning && frameCounter % 10 == 0) {
                stageView.addEnemy(new EnemyGlitch(new Rect(0,0,75,75), gameContext, position, playerPoint));
                spawnCounter++;
                if(spawnCounter > 5) {
                    spawnCounter = 0;
                    spawning = false;
                }
            }
            enemyLaserBullets[i].update(view, player);
            enemyLaserBullets[i+1].update(view, player);
            enemyLaserBullets[i+2].update(view, player);
            enemyLaserBullets[i+3].update(view, player);
        }
        if(position.x > 400) {
            movementSpeed = 0;
        }
        return super.update(view, player);
    }

    @Override
    void draw(Canvas canvas) {
        super.draw(canvas);
        for(int i = 0; i < enemyLaserBullets.length; i++) {
            enemyLaserBullets[i].draw(canvas);
        }
    }

    @Override
    void onDestroy() {
        stageView.gameEnd();
    }
}
