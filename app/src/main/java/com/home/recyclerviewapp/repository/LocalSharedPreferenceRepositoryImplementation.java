package com.home.recyclerviewapp.repository;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.gson.GsonBuilder;
import com.home.recyclerviewapp.R;

import java.util.ArrayList;
import java.util.List;

public class LocalSharedPreferenceRepositoryImplementation implements NotesSource{

    private List<NoteData> dataSource;
    private SharedPreferences sharedPreferences;
    static final String KEY_CELL_1 = "cell_2";
    public static final String KEY_SP_2 = "key_sp_2";

    public LocalSharedPreferenceRepositoryImplementation(SharedPreferences sharedPreferences){
        dataSource = new ArrayList<NoteData>();
        this.sharedPreferences = sharedPreferences;
    }

    public LocalSharedPreferenceRepositoryImplementation init(){
        String savedNote = sharedPreferences.getString(KEY_CELL_1, null);
        if (savedNote!=null) {
            //работа с библиотекой Json или Gson
            dataSource.add(new GsonBuilder().create().fromJson(savedNote, NoteData.class));
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
        dataSource.add(noteData); //карточка будет добавляться в наш массив
        SharedPreferences.Editor editor = sharedPreferences.edit(); //будет сохранятся в SharedPreference
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(noteData));
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
