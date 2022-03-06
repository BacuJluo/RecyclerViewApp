package com.home.recyclerviewapp.publisher;

import com.home.recyclerviewapp.repository.CardData;

public interface Observer {
    void receiveMessage(CardData cardData);
}
