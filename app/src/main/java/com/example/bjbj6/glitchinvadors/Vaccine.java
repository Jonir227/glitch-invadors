package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by bjbj6 on 2017-12-07.
 */

public class Vaccine extends Pickable {
    int removeVal = 2;

    Vaccine(Rect rect, Context gameContext, Point point) {
        super(rect, gameContext, point);
        bitmap = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.vaccine);
    }

    @Override
    void playerInteract(Player player) {
        player.removeGlitch(removeVal);
    }
}
