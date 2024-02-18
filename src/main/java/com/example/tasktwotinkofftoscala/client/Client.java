package com.example.tasktwotinkofftoscala.client;

import com.example.tasktwotinkofftoscala.dto.Address;
import com.example.tasktwotinkofftoscala.dto.Event;
import com.example.tasktwotinkofftoscala.dto.Payload;
import com.example.tasktwotinkofftoscala.dto.Result;

public interface Client {
    //блокирующий метод для чтения данных
    Event readData();

    //блокирующий метод отправки данных
    Result sendData(Address dest, Payload payload);
}
