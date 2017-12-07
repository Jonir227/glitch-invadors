package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;


public abstract class Pickable extends GameObject {

    int movementSpeeed = 10;
    Point positon;

    Pickable(Rect rect, Context gameContext, Point point) {
        super(rect, gameContext);
        positon = new Point(point);
    }

    abstract void playerInteract(Player player);

    @Override
    void update() {
    }

    boolean update(Player player, View view) {
        positon.set(positon.x + movementSpeeed, positon.y);
        move(positon);
        if(player.hitCheck(this)) {
            playerInteract(player);
            return true;
        }
        return isOutOfScreen(positon.x, positon.y, view);
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    void onDestroy() {
    }
}
