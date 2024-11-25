package ru.nabuhiro.java.basic.http.server;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}