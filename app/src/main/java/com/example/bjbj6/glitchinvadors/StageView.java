package com.example.bjbj6.glitchinvadors;

/**
 * Created by bjbj6 on 2017-10-03.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class StageView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Point playerPoint;
    private int weaponUsing = 1;
    private int frameCounter;



    //laser shot
    private Bullet[] laserShot = new Bullet[16];
    private ShotgunBullet[] shotgunBullets = new ShotgunBullet[18];



    ////초기화 블럭
    public void init() {
        getHolder().addCallback(this);

        //MainThread 호출
        thread = new MainThread(getHolder(), this);

        //게임 변수 초기화
        player = new Player(new Rect(0, 0, 200, 150), getContext());
        Point display = new Point();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(display);
        playerPoint = new Point(display.x*2/3, display.y/2);

        for(int i = 0; i < laserShot.length; i++) {
            laserShot[i] = new Bullet(new Rect(0, 0, 50, 25), getContext());
        }

        int direction;
        for(int i = 0; i < shotgunBullets.length; i++) {
            direction = i % 3;
            shotgunBullets[i] = new ShotgunBullet(new Rect(0, 0, 50, 25), getContext(), direction);
        }

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
    public void getControlInput(int x, int y) {
        int targetX = playerPoint.x+ x;
        int targetY = playerPoint.y + y;
        if(targetX < this.getWidth()-player.getRect().width()/2
                && targetY < getHeight()-player.getRect().height()/2
                && targetX - player.getRect().width()/2 > 0
                && targetY - player.getRect().height()/2 > 0) {
            playerPoint.set(targetX, targetY);
        }
    }


    //playerWeaponChange
    public void changeFire(int weaponNo) {
        weaponUsing = weaponNo;
        Log.v("i", weaponUsing+" is activated!");
    }


    // 총알 관련 메소드
    // 총알 배열에 총알 객체를 하나 active시킴
    public void fire() {
        frameCounter++;
        switch (weaponUsing) {
            case 1:
                shot(laserShot);
                break;
            case 2:
                shot(shotgunBullets);
                break;
        }
    }


    private void shot(Bullet[] bullet) {
        if(bullet[0].getShotSpeed() == frameCounter) {
            for(int i = 0; i < bullet.length; i++) {
                if(!bullet[i].getIsFired()) {
                    bullet[i].fire(playerPoint);
                    frameCounter = 0;
                    return;
                }
            }
            Log.v("i", "bullet array is too small!");
        }
    }
    private void shot(ShotgunBullet[] shotgunBullets) {
        if(shotgunBullets[0].getShotSpeed() == frameCounter) {
            for(int i = 0; i < shotgunBullets.length; i+=3) {
                if(!shotgunBullets[i].getIsFired()) {
                    // TODO: 수정할 것. false된 것을 기준으로 3칸씩 다시 active하고 있음.
                    shotgunBullets[i].fire(playerPoint);
                    shotgunBullets[i+1].fire(playerPoint);
                    shotgunBullets[i+2].fire(playerPoint);
                    frameCounter = 0;
                    return;
                }

            }
            Log.v("i", "bullet array is too small!");
        }
    }

    private void updateBullets(Bullet[] bullets) {
        for(int i = 0; i < bullets.length; i++) {
            if(bullets[i].getIsFired()){
                bullets[i].update(this);
            }
        }
    }

    private void drawBullets(Bullet[] bullets, Canvas canvas) {
        for(int i = 0; i < bullets.length; i++) {
            if(bullets[i].getIsFired()){
                bullets[i].draw(canvas);
            }
        }
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
        updateBullets(laserShot);
        updateBullets(shotgunBullets);
    }

    //View Update
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawBullets(laserShot, canvas);
        drawBullets(shotgunBullets, canvas);

        player.draw(canvas);
    }
}
