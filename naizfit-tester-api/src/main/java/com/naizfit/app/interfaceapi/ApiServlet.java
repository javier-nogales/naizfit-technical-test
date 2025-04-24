package com.naizfit.app.interfaceapi;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ApiServlet", urlPatterns = {"/api/*"})
public class ApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo(); // e.g., /ping

        if ("/ping".equals(path)) {
            resp.setContentType("text/plain");
            resp.getWriter().write("pong");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Unknown GET path: " + path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if ("/tester".equals(path)) {
            handleCreateTester(req, resp); // aún no implementado
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Unknown POST path: " + path);
        }
    }

    private void handleCreateTester(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // Simulación básica
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\": \"tester created\"}");
    }
}
