package com.example.tasktwotinkofftoscala;

import com.example.tasktwotinkofftoscala.handler.Handler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class Start implements CommandLineRunner {

    Handler handler;

    @Override
    public void run(String... args) {
        handler.performOperation();
    }
}
