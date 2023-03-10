package com.tylerflar.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
    public static void sendWebhookMessage(JSONObject data, String url) {
        HttpURLConnection connection = null;
        try {
            JSONObject allowedMentions = new JSONObject();
            allowedMentions.put("parse", new JSONArray());
            data.put("allowed_mentions", allowedMentions);
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(data.toJSONString().getBytes());
            }
            connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendServerStartStopMessage(Config config, String event) {
        JSONObject data = new JSONObject();
        data.put("content", "");

        JSONArray embeds = new JSONArray();
        JSONObject embed = new JSONObject();

        if (event.equals("start")) {
            embed.put("title", "Server Started");
            embed.put("description", "Server started at " + config.serverName);
            embed.put("color", 0x00ff00);
        } else if (event.equals("stop")) {
            embed.put("title", "Server Stopped");
            embed.put("description", "Server stopped at " + config.serverName);
            embed.put("color", 0xff0000);
        }

        embeds.add(embed);

        data.put("embeds", embeds);
        data.put("username", config.serverName);
        data.put("avatar_url", config.serverIcon);

        sendWebhookMessage(data, config.eventsWebhookUrl);
    }
}
