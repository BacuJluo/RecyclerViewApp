package com.home.recyclerviewapp.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.home.recyclerviewapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FireStoreImplementation implements NotesSource{

    private static final String NOTES_COLLECTION = "notes";// Имя Базы Данных Firestore

    private List<NoteData> dataSource;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();//Создаем Instance нашей удаленной базы данных.

    CollectionReference collectionReference = firebaseFirestore.collection(NOTES_COLLECTION);//Создаем ссылку, на коллекцию документов в базе данных

    public FireStoreImplementation(){
        dataSource = new ArrayList<NoteData>();


    }

    //TODO обдумать этот метод(!)
    public FireStoreImplementation init(FireStoreResponse fireStoreResponse){
        /*Обращаемся к коллекции подключенной к Farebase и просим
         вернуть нам все записи отсортированные по возрастанию - DATE*/
        collectionReference.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    dataSource = new ArrayList<NoteData>();
                    for ( QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        Map<String, Object> document =  queryDocumentSnapshot.getData();
                        String id = queryDocumentSnapshot.getId();
                        dataSource.add(NoteDataMapping.toNoteData(id, document));
                    }
                }
                fireStoreResponse.initialized(FireStoreImplementation.this);
            }
        });
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
        collectionReference.add(NoteDataMapping.toDocument(noteData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                noteData.setId(documentReference.getId());
            }
        });

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
