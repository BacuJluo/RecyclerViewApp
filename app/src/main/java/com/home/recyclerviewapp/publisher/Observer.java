package com.home.recyclerviewapp.publisher;

import com.home.recyclerviewapp.repository.NoteData;

public interface Observer {
    void receiveMessage(NoteData noteData);
}
