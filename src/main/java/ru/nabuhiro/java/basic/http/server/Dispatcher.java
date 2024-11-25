package ru.nabuhiro.java.basic.http.server;


import ru.nabuhiro.java.basic.http.server.processor.UnknownMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private Set<String> possibleUrls = new HashSet<>();
    private RequestProcessor defaultNotFoundProcessor;
    private RequestProcessor defaultInternalServerErrorProcessor;
    private RequestProcessor defaultBadRequestProcessor;
    private RequestProcessor UnknownMethod;

    public Dispatcher() {
        this.processors = new HashMap<>();
        this.processors.put("/", new HelloWorldProcessor());
        this.processors.put("/calculator", new CalculatorProcessor());
        this.defaultNotFoundProcessor = new DefaultNotFoundProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorProcessor();
        this.defaultBadRequestProcessor = new DefaultBadRequestProcessor();
        this.UnknownMethod = new UnknownMethod();
        for (String url : processors.keySet()) {
            int startIndex = url.indexOf(' ');
            this.possibleUrls.add(url.substring(startIndex + 1));
        }
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            if (possibleUrls.contains(request.getUri()) && !(processors.containsKey(request.getRoutingKey()))) {
                UnknownMethod.execute(request, out);
                return;
            }
            if (!processors.containsKey(request.getUri())) {
                defaultNotFoundProcessor.execute(request, out);
                return;
            }
            processors.get(request.getUri()).execute(request, out);
        } catch (BadRequestException e) {
            request.setException(e);
            defaultBadRequestProcessor.execute(request, out);
        } catch (Exception e) {
            e.printStackTrace();
            defaultInternalServerErrorProcessor.execute(request, out);
        }
    }
}
