package com.example.tasktwotinkofftoscala.handler;

import com.example.tasktwotinkofftoscala.client.Client;
import com.example.tasktwotinkofftoscala.dto.Address;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HandlerImpl implements Handler {

    Client client;


    @Override
    public Duration timeout() {
        return Duration.of(2, ChronoUnit.SECONDS);
    }

    @Override
    public void performOperation() {

        var addresses = new HashMap<String, Sender>();
        while (true) {
            var event = client.readData();

            for (var address: event.recipients()) {

                var key = getKey(address);
                if (!addresses.containsKey(key)) {
                    var sender = new Sender(timeout().toMillis(), client, address);
                    sender.startSend();
                    addresses.put(key, sender);
                }
                addresses.get(key).addPayload(event.payload());
            }
        }
    }

    private String getKey(Address address) {

        return String.format("%s_%s", address.datacenter(), address.nodeId());
    }
}
