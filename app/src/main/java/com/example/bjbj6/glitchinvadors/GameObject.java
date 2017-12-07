package com.example.bjbj6.glitchinvadors;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by bjbj6 on 2017-10-02.
 *
 * 기본 게임 오브젝트.
 * 모든 게임 오브젝틑의 기초.
 *
 */


public abstract class GameObject {

    protected Rect rect;
    protected Bitmap bitmap;
    protected Context gameContext;

    protected GameObject(Rect rect, Context gameContext) {
        this.rect = rect;
        this.gameContext = gameContext;
    }

    abstract void update();
    abstract void draw(Canvas canvas);
    abstract void onDestroy();

    public Rect getRect() {
        return rect;
    }

    public boolean hitCheck(GameObject gameObject) {
        return Rect.intersects(rect, gameObject.getRect());
    }

    protected boolean isOutOfScreen(int x, int y, View view) {
        return !(x < view.getWidth() - rect.width() / 2
                && y < view.getHeight() - rect.height() / 2
                && x - rect.width() / 2 > 0
                && y - rect.height() / 2 > 0);
    }

    public void move(Point point) {
        rect.set(point.x -rect.width()/2, point.y - rect.height()/2,
                point.x + rect.width()/2, point.y + rect.height()/2);
    }

    public static void move(Rect rect, Point point) {
        rect.set(point.x -rect.width()/2, point.y - rect.height()/2,
                point.x + rect.width()/2, point.y + rect.height()/2);
    }

}
