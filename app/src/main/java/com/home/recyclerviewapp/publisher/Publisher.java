package com.home.recyclerviewapp.publisher;

import com.home.recyclerviewapp.repository.CardData;

import java.util.ArrayList;
import java.util.List;

//Создаем слушателя который будет получать и передавать данные тем кто на него подписан(использует его)

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

    public void sendMessage(CardData cardData){
        //Когда в паблишер передают сообщения, и что бы их найти
        //Для начала пройдемся по списку наблюдателя (Observer) через цикл
        for (Observer observer:observers){
            //Далее идет рассылка сообщений
            observer.receiveMessage(cardData);
        }
    }
}