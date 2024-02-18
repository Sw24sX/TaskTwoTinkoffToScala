package com.example.tasktwotinkofftoscala.handler;

import java.time.Duration;

public interface Handler {

    Duration timeout();

    void performOperation();
}
