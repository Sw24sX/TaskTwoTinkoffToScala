package com.example.tasktwotinkofftoscala.dto;

import java.util.List;

public record Event(List<Address> recipients, Payload payload) {}
