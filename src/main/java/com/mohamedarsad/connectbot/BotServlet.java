package com.mohamedarsad.connectbot;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "botServlet", value = "/bot")
public class BotServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("I'm Up!");
    }

    public void destroy() {
    }
}