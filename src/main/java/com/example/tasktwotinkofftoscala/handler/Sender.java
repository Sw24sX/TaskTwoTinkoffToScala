package com.example.tasktwotinkofftoscala.handler;

import com.example.tasktwotinkofftoscala.client.Client;
import com.example.tasktwotinkofftoscala.dto.Address;
import com.example.tasktwotinkofftoscala.dto.Payload;
import com.example.tasktwotinkofftoscala.dto.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Sender {

    private static final Integer RETRIES_COUNT = 5;

    BlockingQueue<Payload> data = new LinkedBlockingQueue<>();
    long timeout;
    Client client;
    Address address;

    @SneakyThrows
    public void addPayload(Payload payload) {
        log.info("For address {} add payload with origin {}", address, payload.origin());
        data.put(payload);
    }

    @SneakyThrows
    public void startSend() {
        new Thread(() -> {

            log.info("For address {} sender started", address);

            while (true) {
                Payload payload = null;
                try {
                    payload = data.take();
                } catch (InterruptedException e) {
                    log.warn(e.getMessage(), e);
                }
                var response = client.sendData(address, payload);

                log.info("For address {} was send payload with origin {}", address, payload.origin());

                if (response == Result.REJECTED) {
                    log.info("For address {} response REJECT", address);
                    var retries = 0;
                    while (response != Result.ACCEPTED || retries < RETRIES_COUNT) {
                        try {
                            Thread.sleep(timeout);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        response = client.sendData(address, payload);
                        retries++;
                    }

                    log.info("For address {} was retries {} and result {}", address, retries, response);

                } else {
                    log.info("For address {} response SUCCESS", address);
                }
            }
        }).start();
    }
}
