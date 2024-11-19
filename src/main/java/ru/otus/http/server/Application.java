package ru.otus.http.server;

public class Application {
    public static void main(String[] args) {
        new HttpServer(8990).start();
    }
}
// Домашнее задание:
// 1. Добавить обработку запросов в цикле
// 2. Добавить обработку запросов в тред пуле
