package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by bjbj6 on 2017-12-06.
 */

public class GlitchBlock extends Pickable {

    Bitmap tmp1;
    Bitmap tmp2;
    int frame = 0;

    GlitchBlock(Rect rect, Context gameContext, Point point) {
        super(rect, gameContext, point);
        tmp1 = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch1);
        tmp2 = BitmapFactory.decodeResource(gameContext.getResources(), R.drawable.glitch2);
        bitmap = tmp1;
    }


    @Override
    boolean update(Player player, View view) {
        frame++;
        if (frame % 5 == 0) {
            bitmap = tmp2;
        } else
            bitmap = tmp1;
        return super.update(player, view);
    }


    @Override
    void playerInteract(Player player) {
        player.addGlitch(rect);
    }

}
