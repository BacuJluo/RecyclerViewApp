package com.home.recyclerviewapp.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.home.recyclerviewapp.R;

import java.util.ArrayList;
import java.util.List;

public class LocalRepositoryImplementation implements CardSource{

    private List<CardData> dataSource;
    private Resources resources;

    public LocalRepositoryImplementation(Resources resources){
        dataSource = new ArrayList<CardData>();
        this.resources = resources;
    }

    public LocalRepositoryImplementation init(){
        String[] titles = resources.getStringArray(R.array.titles); //список заголовков
        String[] description = resources.getStringArray(R.array.descriptions);//массив описания
        TypedArray colors = resources.obtainTypedArray(R.array.colors);//список цветов

        for (int i = 0;i< titles.length;i++){
            dataSource.add(new CardData(titles[i],description[i], colors.getResourceId(i,0),false));
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<CardData> getAllCardsData() {
        return dataSource;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }
}
