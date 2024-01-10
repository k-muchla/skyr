package com.github.krystianmuchla.home.api;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseWriter {
    private ResponseWriter() {
    }

    public static void writeJson(final HttpServletResponse response, final Object object) throws IOException {
        final var objectMapper = ObjectMapperHolder.INSTANCE;
        writeJson(response, objectMapper.writeValueAsString(object));
    }

    public static void writeJson(final HttpServletResponse response, final String string) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(string);
    }
}
