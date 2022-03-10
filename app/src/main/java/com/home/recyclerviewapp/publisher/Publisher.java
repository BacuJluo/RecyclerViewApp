package com.home.recyclerviewapp.publisher;

import com.home.recyclerviewapp.repository.NoteData;

import java.util.ArrayList;
import java.util.List;

//Создаем слушателя который будет получать и передавать данные тем кто на него подписан(использует его)
//(!)Передача данных между фрагментами через слушатель.

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        this.observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    public void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void sendMessage(NoteData noteData){
        //Когда в паблишер передают сообщения, и что бы их найти
        //Для начала пройдемся по списку наблюдателя (Observer) через цикл
        for (Observer observer:observers){
            //Далее идет рассылка сообщений
            observer.receiveMessage(noteData);
        }
    }
}
