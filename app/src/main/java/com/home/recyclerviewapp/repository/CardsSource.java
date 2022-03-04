package com.home.recyclerviewapp.repository;

import java.util.List;

public interface CardsSource {
    int size();
    List<CardData> getAllCardsData();
    CardData getCardData(int position);

    //1. добавили новые методы (очистка карточки, добавление, удаление, обновление карточки)
    void clearCardsData();
    void addCardData(CardData cardData);

    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);

    //2.
}
