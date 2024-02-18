package com.example.tasktwotinkofftoscala.client;

import com.example.tasktwotinkofftoscala.dto.Address;
import com.example.tasktwotinkofftoscala.dto.Event;
import com.example.tasktwotinkofftoscala.dto.Payload;
import com.example.tasktwotinkofftoscala.dto.Result;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClientImpl implements Client {

    AtomicInteger counter = new AtomicInteger();

    @SneakyThrows
    @Override
    public Event readData() {
        Thread.sleep(1000);
        var firstAddresses = List.of(new Address("1", "1"), new Address("1", "2"));
        var secondAddresses = List.of(new Address("2", "1"), new Address("2", "2"));
        var payload = new Payload(UUID.randomUUID().toString(), new byte[0]);
        return counter.getAndIncrement() % 2 == 0 ? new Event(firstAddresses, payload) : new Event(secondAddresses, payload);
    }

    @SneakyThrows
    @Override
    public synchronized Result sendData(Address dest, Payload payload) {

        Thread.sleep(100);
        return counter.getAndIncrement() % 2 == 0 ? Result.REJECTED : Result.ACCEPTED;
    }
}
