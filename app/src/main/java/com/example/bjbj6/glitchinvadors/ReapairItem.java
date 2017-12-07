package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by bjbj6 on 2017-12-07.
 */

public class ReapairItem extends Pickable {

    int value = 3;

    ReapairItem(Rect rect, Context gameContext, Point point) {
        super(rect, gameContext, point);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.fix_item);
    }

    @Override
    void playerInteract(Player player) {
        player.addHealth(3);
    }
}
