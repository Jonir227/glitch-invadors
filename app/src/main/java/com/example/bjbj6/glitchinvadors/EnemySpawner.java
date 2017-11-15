package com.example.bjbj6.glitchinvadors;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * Created by bjbj6 on 2017-11-14.
 */

public class EnemySpawner {

    public static final int SPAWN_END = -3;
    public static final int NO_SPAWN = -2;
    public static final int CSV_INPUT_ERROR = -1;
    public static final int GUN_SHIP = 0;


    private BufferedReader reader;
    private LinkedList<int[]> spawnData;

    public EnemySpawner(Context context) {
        //GET CSV DATA
        try{

            spawnData = new LinkedList<>();

            InputStream csv = context.getResources().openRawResource(R.raw.spawn_data);
            reader = new BufferedReader(
                    new InputStreamReader(csv, Charset.forName("UTF-8"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                Log.v("l", line);
                int[] intToken = new int[4];
                for(int i = 0; i < token.length; i++) {
                    intToken[i] = Integer.parseInt(token[i]);
                }
                spawnData.add(intToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int spawnCheck(int frameCounter) {
        if(spawnData.isEmpty()) {
            return SPAWN_END;
        }
        int spawnDataInput = spawnData.peekFirst()[0];
        if(frameCounter == spawnDataInput){
            spawnDataInput = spawnData.peekFirst()[1];
            switch (spawnDataInput) {
                case 0:
                    return GUN_SHIP;
                default:
                    return CSV_INPUT_ERROR;
            }
        }
        return NO_SPAWN;
    }

    public Point getSpawnLocation() {
        int[] tmp = spawnData.pop();
        return new Point(tmp[2], tmp[3]);
    }

}
