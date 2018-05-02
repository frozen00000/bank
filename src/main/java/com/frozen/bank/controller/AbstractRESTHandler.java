package com.frozen.bank.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.frozen.bank.repository.UpdateOfMissingEntityException;
import com.frozen.bank.service.EntityNotFoundException;
import com.frozen.bank.service.NotEnoughMoneyException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Common class for all REST handlers. Encapsulates common logic.
 * Each handler is responsible for some Entity and supports Create and Read operations.
 * @param <T> type of incoming object for post method.
 */
@Slf4j
public abstract class AbstractRESTHandler<T> implements HttpHandler {

    static final int OK = 200;
    static final int CREATED = 201;
    static final int BAD_REQUEST = 400;
    static final int NOT_FOUND = 404;
    static final int METHOD_NOT_ALLOWED = 405;
    static final int NOT_ACCEPTABLE = 406;
    static final int INTERNAL_SERVER_ERROR = 500;

    static final String GET = "GET";
    static final String POST = "POST";

    // Jackson is used for object (de)serialization to JSON format.
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Each handler manages request with specific context path.
     * @return return context path that is handled by this instance of handler.
     */
    public abstract String getContextPath();

    /**
     * Each handler exposes entities by identifier.
     * @param id identifier of entity.
     * @return object for specified identifier.
     */
    abstract Object get(UUID id);

    /**
     * Creates a new entity.
     * @param body body of request that is represented by object of class T.
     * @return identifier of new entity.
     */
    abstract UUID post(T body);

    /**
     * Meta information for mapper that deserializes JSON (body) to object of class T.
     * @return class of T.
     */
    abstract Class<T> getBodyClass();

    /**
     * Extracts identifier from URI path. The identifier should be right after context path and following slash.
     * Example: "/mycontext/1234". 1234 is the identifier.
     * @param path URI path of current request.
     * @return parsed identifier from path.
     */
    private UUID extractId(String path) {
        // Probably, this logic should be a little bit complicated and handle more cases.
        // Leave it as it for now because it is just test task.
        return UUID.fromString(path.substring(getContextPath().length()).split("/")[1]);
    }

    /**
     * Handle the given request and generate an appropriate response.
     * Supports only 'GET' and 'POST' methods. Will write response with code 405 (METHOD_NOT_ALLOWED) for all other
     * methods.
     * If new entity is created then writes an identifier as a result with code 201 (CREATED).
     * @see     com.sun.net.httpserver.HttpHandler#handle(HttpExchange)
     * @param exchange the exchange containing the request from the client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case GET:
                    Object value = get(extractId(exchange.getRequestURI().getPath()));
                    writeResponse(exchange, mapper.writeValueAsString(value));
                    break;
                case POST:
                    T body = mapper.readValue(exchange.getRequestBody(), getBodyClass());
                    UUID id = post(body);
                    writeResponse(exchange, id.toString(), CREATED);
                    break;
                default:
                    writeResponse(exchange, String.format("Method '%s' is not allowed.", exchange.getRequestMethod()), METHOD_NOT_ALLOWED);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            writeResponse(exchange, e);
        }
    }

    private void writeResponse(HttpExchange exchange, String response, int code) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(code, bytes.length);
            os.write(bytes);
        }
    }

    private void writeResponse(HttpExchange exchange, String response) throws IOException {
        writeResponse(exchange, response, OK);
    }

    /**
     * Writes a response according to specified exception.
     * @param exchange the exchange containing the request from the client and used to send the response
     * @param exception occurred exception during handling of request.
     * @throws IOException if an I/O error occurs.
     */
    private void writeResponse(HttpExchange exchange, Exception exception) throws IOException {
        int code = INTERNAL_SERVER_ERROR;
        if (exception instanceof EntityNotFoundException || exception instanceof UpdateOfMissingEntityException) {
            code = NOT_FOUND;
        } else if (exception instanceof NotEnoughMoneyException) {
            code = NOT_ACCEPTABLE;
        } else if (exception instanceof MismatchedInputException || exception instanceof JsonParseException) {
            code = BAD_REQUEST;
        }
        writeResponse(exchange, exception.getMessage(), code);
    }

}
