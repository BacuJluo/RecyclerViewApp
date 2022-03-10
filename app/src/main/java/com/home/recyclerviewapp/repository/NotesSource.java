package com.home.recyclerviewapp.repository;

import java.util.List;

public interface NotesSource {
    int size();
    List<NoteData> getAllCardsData();
    NoteData getCardData(int position);

    //1. добавили новые методы (очистка карточки, добавление, удаление, обновление карточки)
    void clearCardsData();
    void addCardData(NoteData noteData);

    void deleteCardData(int position);
    void updateCardData(int position, NoteData noteData);

    //2.
}
