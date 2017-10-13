package com.example.bjbj6.glitchinvadors;

/**
 * Created by bjbj6 on 2017-10-03.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StageView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Point playerPoint;



    ////초기화 블럭
    public void init() {
        getHolder().addCallback(this);

        //MainThread 호출
        thread = new MainThread(getHolder(), this);

        //게임 변수 초기화
        player = new Player(new Rect(100, 100, 300, 250), getContext());
        playerPoint = new Point(150, 150);

        setFocusable(true);
    }

    ///Constructor
    public StageView(Context context) {
        super(context);
        init();
    }

    public StageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StageView(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        init();
    }


    //-------------------------------------
    //|
    //|           플레이어 기능
    //|
    //-------------------------------------

    ///playerMovement
    public void getControlInput(int x, int y, int weaponNo) {
        int targetX = playerPoint.x+ x;
        int targetY = playerPoint.y + y;
        if(targetX < this.getWidth() && targetY < getHeight()) {
            playerPoint.set(targetX, targetY);
        }
        player.setWeapon(weaponNo);
    }




    //플레이어 발사
    // arraylist에 총알 객체를 하나 추가해서 active시킴
    public void fire(long nanoTime) {
        int[] shotData = player.getWeapon();

    }







    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    //Logical Update
    public void update() {
        player.update(playerPoint);
    }


    //View Update
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        player.draw(canvas);
    }
}
