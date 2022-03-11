package com.home.recyclerviewapp.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.home.recyclerviewapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalRepositoryImplementation implements NotesSource {

    private List<NoteData> dataSource;
    private Resources resources;

    public LocalRepositoryImplementation(Resources resources){
        dataSource = new ArrayList<NoteData>();
        this.resources = resources;
    }

    public LocalRepositoryImplementation init(){
        String[] titles = resources.getStringArray(R.array.titles); //список заголовков
        String[] description = resources.getStringArray(R.array.descriptions);//массив описания
        TypedArray colors = resources.obtainTypedArray(R.array.colors);//список цветов

        for (int i = 0;i< titles.length;i++){
            dataSource.add(new NoteData(titles[i],description[i], colors.getResourceId(i,0),false, Calendar.getInstance().getTime()));
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public List<NoteData> getAllNotesData() {
        return dataSource;
    }

    @Override
    public NoteData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void clearNotesData() {
        dataSource.clear();
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position, newNoteData);
    }

}
