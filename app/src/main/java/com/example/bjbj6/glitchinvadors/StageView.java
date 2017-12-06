package com.example.bjbj6.glitchinvadors;

/**
 * Created by bjbj6 on 2017-10-03.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StageView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player player;
    private Point playerPoint;
    private int weaponUsing = 1;
    private int shotCounter = 0;
    private int totalFrame = 0;

    //laser shot
    private Bullet[] laserShot = new Bullet[16];
    private ShotgunBullet[] shotgunBullets = new ShotgunBullet[18];

    //적들
    private EnemySpawner enemySpawner;

    private ConcurrentLinkedQueue<Enemy> enemyLinkedList = new ConcurrentLinkedQueue<>();

    private Rect gunshipRect = new Rect(0, 0, 200, 150);
    private Rect armedRect = new Rect(0, 0, 200, 300);



    ////초기화 블럭
    public void init() {

        getHolder().addCallback(this);

        //MainThread 호출
        thread = new MainThread(getHolder(), this);

        //게임 변수 초기화
        player = new Player(new Rect(0, 0, 200, 150), getContext(), this);
        Point display = new Point();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(display);
        playerPoint = new Point(display.x*2/3, display.y/2);

        //bullet 초기화
        for(int i = 0; i < laserShot.length; i++) {
            laserShot[i] = new Bullet(new Rect(0, 0, 50, 25), getContext());
        }


        for(int i = 0; i < shotgunBullets.length; i++) {
            shotgunBullets[i] = new ShotgunBullet(new Rect(0, 0, 50, 25), getContext());
        }

        //Enemy 초기화
        enemySpawner = new EnemySpawner(getContext());

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


    //Logical Update
    public void update() {

        totalFrame++;
        player.update(playerPoint);

        fire();
        spawnFrameCheck(totalFrame);

        updateBullets(laserShot);
        updateBullets(shotgunBullets);

        updateEnemy(enemyLinkedList);
    }

    //View Update
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawBullets(laserShot, canvas);
        drawBullets(shotgunBullets, canvas);

        drawEnemy(enemyLinkedList, canvas);

        player.draw(canvas);
    }



    // ---------------------------------
    //            프레임 계산
    // ---------------------------------

    private int frameDivByTen() {
        return totalFrame/10;
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


    //--------------------------

    //        총알 관련 메소드
    //
    //--------------------------


    // 총알 배열에 총알 객체를 하나 active시킴
    public void fire() {
        if(totalFrame%10 == 0) {
            shotCounter++;
            switch (weaponUsing) {
                case 1:
                    shot(laserShot);
                    break;
                case 2:
                    shot(shotgunBullets);
                    break;
            }
        }
    }

    //모든 불렛 배열을 돌면서 발사 상태가 아닌 것을 발사 상태로 만들고 종료
    private void shot(Bullet[] bullet) {
        if(shotCounter == bullet[0].getShotSpeed()) {
            for(int i = 0; i < bullet.length; i++) {
                if(!bullet[i].getIsFired()) {
                    bullet[i].fire(playerPoint);
                    shotCounter = 0;
                    return;
                }
            }
            Log.v("i", "bullet array is too small!");
        }
    }
    private void shot(ShotgunBullet[] shotgunBullets) {
        if(shotCounter == shotgunBullets[0].getShotSpeed()) {
            int fireCount = 0;
            for(int i = 0; i < shotgunBullets.length; i++) {
                if(!shotgunBullets[i].getIsFired()) {
                    shotgunBullets[i].setDirection(fireCount);
                    shotgunBullets[i].fire(playerPoint);
                    fireCount++;
                    if(fireCount == 3) {
                        shotCounter = 0;
                        return;
                    }
                }

            }
            Log.v("i", "bullet array is too small!");
        }
    }

    private void updateBullets(Bullet[] bullets) {
        for(int i = 0; i < bullets.length; i++) {
            if(bullets[i].getIsFired()){
                bullets[i].update(this);
                bullets[i].dealDamage(enemyLinkedList);
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

    // --------------------------------
    //
    //
    //          적 스폰관련 메소드
    //
    //
    // --------------------------------

    //CSV파일을 읽어서 10프레임 단위로 체크.
    //적을 특정 위치에 스폰시킨다.
    public void spawnFrameCheck(int totalFrame) {
        if(totalFrame%10 != 0)
            return;
        Rect tmp;
        switch (enemySpawner.spawnCheck(frameDivByTen())) {
            case EnemySpawner.GUN_SHIP:
                tmp = new Rect(gunshipRect);
                enemyLinkedList.add(new Enemy(tmp, getContext(), enemySpawner.getSpawnLocation()));
                break;
            case EnemySpawner.ARMED_SHIP:
                tmp = new Rect(armedRect);
                enemyLinkedList.add(new EnemyArmed(tmp, getContext(), enemySpawner.getSpawnLocation()));
                break;
            case EnemySpawner.SPAWN_END:
                Log.v("i", "spawn end!");
                break;
            case EnemySpawner.CSV_INPUT_ERROR:
                Log.v("i", "error!");
                break;
            case EnemySpawner.NO_SPAWN:
                break;
        }
    }

    public void updateEnemy(ConcurrentLinkedQueue<Enemy> enemies) {
        for(Enemy enemy : enemies) {
            if(enemy.update(this, player)) {
                enemies.remove(enemy);
            }
        }
    }

    public void drawEnemy(ConcurrentLinkedQueue<Enemy> enemies, Canvas canvas) {
        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
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

    public void gameEnd() {
        thread.setRunning(false);
        try {
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("i", "game end!");
        Activity activity = (Activity)getContext();
        activity.finish();
    }

}
