package com.mohamedarsad.connectbot;

import java.io.*;
import javax.security.auth.login.LoginException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

@WebServlet(name = "botServlet", value = "/bot")
public class BotServlet extends HttpServlet {
    final private static String TOKEN = "<!-- DISCORD BOT TOKEN -->";
    final private static String API_AUTH_TOKEN = "<!-- API AUTH TOKEN -->";
    final private static String CHANNEL_ID = "<!-- CHANNEL ID TO POST MESSAGE -->";

    private JDA discordBotApi = null;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("I'm Up!");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String auth = request.getHeader("Authorization");
        auth = auth.split(" ")[1];
        if(!auth.equals(API_AUTH_TOKEN)) {
            out.println("{status: 403, message: 'Unauthorized'}");
            return;
        }

        try {
            discordBotApi = JDABuilder.createDefault(TOKEN)
                    .build();
            discordBotApi.awaitReady();
        } catch (Exception e) {
            out.println("{status: 500, message: 'Internal Server Error'}");
            return;
        }

        String name = request.getParameter("name");
        String from = request.getParameter("from");
        String message = request.getParameter("message");

        String data = name + "<" + from + ">\n\n" + message;

        TextChannel channel = discordBotApi.getTextChannelById(CHANNEL_ID);
        if (channel != null) {
            channel.sendMessage(data).queue();
            out.println("{status: 200, message: 'Success'}");
        } else {
            out.println("{status: 500, message: 'Internal Server Error'}");
        }
    }

    public void destroy() {
    }
}