package com.home.recyclerviewapp.repository;

import java.util.List;

public interface NotesSource {
    int size();
    List<NoteData> getAllNotesData();
    NoteData getCardData(int position);

    //1. добавили новые методы (очистка карточки, добавление, удаление, обновление карточки)
    void clearNotesData();
    void addNoteData(NoteData noteData);

    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);


    //2.
}
