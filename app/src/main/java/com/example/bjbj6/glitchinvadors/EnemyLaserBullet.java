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

public class EnemyLaserBullet extends GameObject {

    protected Point bulletPoint;
    protected int bulletSpeed = 25;
    protected int damage;
    protected boolean isFired = false;

    EnemyLaserBullet(Rect rect, Context context, int damage) {
        super(rect, context);
        this.damage = damage;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet);
        bulletPoint = new Point();
    }

    void fire(Point point) {
        bulletPoint.set(point.x, point.y);
        isFired = true;
    }

    int getDamage() {
        return damage;
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    void update() {
    }

    void update(View view, Player player) {
        bulletPoint.set(bulletPoint.x + bulletSpeed, bulletPoint.y);
        move(bulletPoint);
        checkPlayer(player);
        if(isOutOfScreen(bulletPoint.x, bulletPoint.y, view)) {
            isFired = false;
        }
    }

    private void checkPlayer(Player player) {
        if(player.hitCheck(this)) {
            player.damageDealt(damage);
        }
    }

    protected boolean isOutOfScreen(int x, int y, View view) {
        return !(x < view.getWidth() - rect.width() / 2
                && y < view.getHeight() - rect.height() / 2
                && x - rect.width() / 2 > 0
                && y - rect.height() / 2 > 0);
    }

    @Override
    void onDestroy() {

    }
}
