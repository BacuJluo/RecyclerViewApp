package com.home.recyclerviewapp.repository;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.home.recyclerviewapp.R;

import java.lang.reflect.Type;
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
            Type type = new TypeToken<ArrayList<NoteData>>(){}.getType();//Получили тип сохраненных данных
            dataSource = (new GsonBuilder().create().fromJson(savedNote, type)); // Извлечение данных по их типу
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
        saveNoteState();
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData); //карточка будет добавляться в наш массив
        saveNoteState();
    }
    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
        saveNoteState();
    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position, newNoteData);
        saveNoteState();
    }

    private void saveNoteState(){
        SharedPreferences.Editor editor = sharedPreferences.edit(); //будет сохранятся в SharedPreference
        editor.putString(KEY_CELL_1, new GsonBuilder().create().toJson(dataSource)).apply();//Сохранили полностью данные dataSource
    }
}
