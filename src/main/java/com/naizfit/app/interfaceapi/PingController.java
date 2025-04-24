package com.naizfit.app.interfaceapi;

import java.io.IOException;

import com.google.inject.Singleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class PingController {
	public void ping(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("pong");
    }
}
