package com.frozen.bank.controller;

import com.sun.net.httpserver.HttpExchange;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

abstract class AbstractHandlerTest {

    @Mock
    HttpExchange exchange;

    abstract AbstractRESTHandler<?> getHandler();

    void test(String method, int expectedCode, String expectedResult) throws URISyntaxException, IOException {
        test(method, "", expectedCode, expectedResult);
    }

    void test(String method, String extraPath, int expectedCode, String expectedResult) throws URISyntaxException, IOException {
        test(method, extraPath, expectedCode, actualResult -> assertEquals(expectedResult, actualResult));
    }

    @SuppressWarnings("SameParameterValue")
    void test(String method, int expectedCode, Consumer<String> resultVerifier) throws URISyntaxException, IOException {
        test(method, "", expectedCode, resultVerifier);
    }

    private void test(String method, String extraPath, int expectedCode, Consumer<String> resultVerifier) throws URISyntaxException, IOException {
        when(exchange.getRequestMethod()).thenReturn(method);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);
        String baseUrl = "http://localhost:8080";
        when(exchange.getRequestURI()).thenReturn(new URI(baseUrl + getHandler().getContextPath() + extraPath));
        getHandler().handle(exchange);
        resultVerifier.accept(outputStream.toString(StandardCharsets.UTF_8.name()));
        verify(exchange).sendResponseHeaders(expectedCode, outputStream.size());
    }

}
