package ru.nabuhiro.java.basic.http.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HttpServer {
    private int port;
    private final Dispatcher dispatcher;
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер запущен на порту: {}", port);
            try (Socket socket = serverSocket.accept()) {
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                String rawRequest = new String(buffer, 0, n);
                HttpRequest request = new HttpRequest(rawRequest);
                request.info();
                dispatcher.execute(request, socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
