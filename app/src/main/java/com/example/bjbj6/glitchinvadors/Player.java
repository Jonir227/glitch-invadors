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

public class Player implements GameObject {

    private Rect rect;
    private Bitmap bitmap;
    private int MAX_HEALTH;
    private int currentHealth;
    public int LASER_SHOT_SPEED;
    public int LASER_SHOT_DAMAGE;
    public int SHOTGUN_SHOT_SPEED;
    public int SHOTGUN_SHOT_DAMAGE;
    public int ROCKET_SHOT_SPEED;
    public int ROCKET_SHOT_DAMAGE;
    private int weaponUsing;

    public Player(Rect rect, Context context) {
        this.rect = rect;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        MAX_HEALTH = context.getResources().getInteger(R.integer.playerHealth);
        currentHealth = MAX_HEALTH;
        LASER_SHOT_DAMAGE = context.getResources().getInteger(R.integer.laserShotDmg);
        LASER_SHOT_SPEED = context.getResources().getInteger(R.integer.laserShotSpd);
        SHOTGUN_SHOT_DAMAGE = context.getResources().getInteger(R.integer.shotgunShotDmg);
        SHOTGUN_SHOT_SPEED = context.getResources().getInteger(R.integer.shotgunShotSpd);
        ROCKET_SHOT_DAMAGE = context.getResources().getInteger(R.integer.rocketShotDmg);
        ROCKET_SHOT_SPEED = context.getResources().getInteger(R.integer.rocketShotSpd);
        setWeapon(1);
    }


    //Player 기능

    //무기 Setter & getter
    public void setWeapon(int weaponNo) {
        weaponUsing = weaponNo;
    }

    public int[] getWeapon() {
        int[] weaponData = new int[3];
        weaponData[0] = weaponUsing;
        switch (weaponUsing) {
            case 1:
                weaponData[1] = LASER_SHOT_SPEED;
                weaponData[2] = LASER_SHOT_DAMAGE;
                break;
            case 2:
                weaponData[1] = SHOTGUN_SHOT_SPEED;
                weaponData[2] = SHOTGUN_SHOT_DAMAGE;
                break;
            case 3:
                weaponData[1] = ROCKET_SHOT_SPEED;
                weaponData[2] = ROCKET_SHOT_DAMAGE;
                break;
            default:
                Log.v("e", "err! weapon data is incorrcnt!");
        }
        return weaponData;
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
        rect.set(point.x -rect.width()/2, point.y - rect.height()/2, point.x + rect.width()/2, point.y + rect.height()/2);
        if(currentHealth <= 0) {
            onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        //플레이어 죽을때

    }
}
