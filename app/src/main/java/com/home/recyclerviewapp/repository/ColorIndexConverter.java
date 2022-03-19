package com.home.recyclerviewapp.repository;

import com.home.recyclerviewapp.R;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ColorIndexConverter {
    //Конвертируем индексы цветов в id
    private static Random rnd = new Random();
    private static Object syncObj = new Object();

    private static final int[] colorIndex = {
            R.color.blue,
            R.color.black,
            R.color.purple_200,
            R.color.teal_700,
            R.color.yellow
    };

    public static int randomColorIndex() {
        synchronized (syncObj) {
            return rnd.nextInt(colorIndex.length);
        }
    }

    public static int getColorByIndex(int index){
        if (index < 0 || index >= colorIndex.length){
            index = 0;
        }
        return colorIndex[index];
    }

    public static int getIndexByColor(int color){
        for (int i = 0; i < colorIndex.length; i++ ){
            if (colorIndex[i] == color){
                return i;
            }
        }
        return 0;
    }
}
