package ru.nabuhiro.java.basic.http.server;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest request, OutputStream output) throws IOException;
}
