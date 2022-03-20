package com.home.recyclerviewapp.repository;


import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields {

        //Создаем названия для всех полей в Заметках, которые будут хранится в ячейке Firestore
        public final static String COLOR = "color";
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String LIKE = "like";

    }

    public static NoteData toNoteData(String id, Map<String, Object> doc){
        //Перевод документа (NoteData) в NoteData
        long indexColor = (long) doc.get(Fields.COLOR);

        /*Timestamp(ВРЕМЕННАЯ МЕТКА) - это последовательность символов или
        закодированной информации, показывающей, когда произошло определённое событие.*/

        Timestamp timestamp = (Timestamp) doc.get(Fields.DATE);
        //Создаем новую NoteData и передаем в нее из документа
        //аналогичным образом данные (Ключ, Значение)
        NoteData answer = new NoteData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                ColorIndexConverter.getColorByIndex((int) indexColor),
                (boolean) doc.get(Fields.LIKE),
                timestamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteData noteData){
        //Переводим NoteData в документ
        Map<String, Object> answer = new HashMap<>(); //создаем документ с помощью Map
        answer.put(Fields.TITLE, noteData.getTitle()); //загружаем (Ключ, Значение)
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.COLOR, ColorIndexConverter.getIndexByColor(noteData.getColors()));
        answer.put(Fields.LIKE, noteData.isLike());
        answer.put(Fields.DATE, noteData.getDate());
        return answer;
    }

}