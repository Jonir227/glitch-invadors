package com.example.bjbj6.glitchinvadors;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by bjbj6 on 2017-10-03.
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private StageView stageView;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, StageView stageView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.stageView = stageView;
    }

    @Override
    public void run() {
        long startTime;
        long timeMills = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;



        //30프레임을 유지하면서 기기 부하를 줄임.
        //프레임당 한번 update가 유지
        //그 이상 되면 기다림

        while(running) {
            //namoTime대신 System.currnetTimeInMills사용가능
            //이쪽이 부하가 더 적다.
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    //프레임단위 작업
                    if(frameCount%10 == 0) {
                        this.stageView.fire();
                    }
                    //update에는 게임을 바꾸는 메소드들이 들어감
                    this.stageView.update();
                    //draw는 화면에 그리는 것.
                    this.stageView.draw(canvas);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                timeMills = (System.nanoTime() - startTime)/1000000;
                waitTime = targetTime - timeMills;
                try {
                    if(waitTime > 0){
                        this.sleep(waitTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                totalTime += System.nanoTime() - startTime;
                frameCount++;
                if(frameCount == MAX_FPS) {
                    averageFPS = 1000/((totalTime/frameCount)/1000000);
                    frameCount = 0;
                    totalTime = 0;
                    Log.v("f", averageFPS+"");
                }
            }

        }
    }
}
