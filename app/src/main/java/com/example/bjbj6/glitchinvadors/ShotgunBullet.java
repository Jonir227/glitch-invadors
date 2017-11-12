package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by bjbj6 on 2017-11-12.
 */

public class ShotgunBullet extends Bullet{


    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_FORWARD = 1;
    public static final int DIRECTION_DOWN = 2;


    private int direction = 0;
    private int bulletSpeed = 30;

    public ShotgunBullet(Rect rect, Context context, int direction) {
        super(rect, context);
        this.direction = direction;
        this.damage = context.getResources().getInteger(R.integer.shotgunShotDmg);
        this.shotSpeed = context.getResources().getInteger(R.integer.shotgunShotSpd);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shotgun_bullet);
    }


    public void update(View view) {
        switch (direction) {
            case DIRECTION_FORWARD:
                super.update(view);
                break;
            case DIRECTION_UP:
                bulletPoint.set(bulletPoint.x - calXY(bulletSpeed), bulletPoint.y - calXY(bulletSpeed));
                move(bulletPoint);
                break;
            case DIRECTION_DOWN:
                bulletPoint.set(bulletPoint.x - calXY(bulletSpeed), bulletPoint.y + calXY(bulletSpeed));
                move(bulletPoint);
                break;
        }
        Log.v("i", direction+"");
        if(isOutOfScreen(bulletPoint.x, bulletPoint.y, view)) {
            isFired = false;
            Log.v("i", direction+"");
        }
    }

    private int calXY(int bulletSpeed) {
        return (int)(Math.sqrt((bulletSpeed*bulletSpeed)/2.0));
    }

}
