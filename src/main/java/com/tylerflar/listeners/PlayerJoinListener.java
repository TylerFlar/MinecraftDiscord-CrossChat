package com.tylerflar.listeners;

import static com.tylerflar.util.Util.sendWebhookMessage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tylerflar.MCDisCrossChat;

public class PlayerJoinListener implements Listener {
    private boolean _enabled = true;
    private String _webhoString = "";
    private String _serverName;
    private String _serverIcon;

    public PlayerJoinListener(MCDisCrossChat plugin) {
        this._enabled = plugin.config.enabled;
        this._webhoString = plugin.config.eventsWebhookUrl;
        this._serverName = plugin.config.serverName;
        this._serverIcon = plugin.config.serverIcon;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (_enabled) {
            JSONObject json = new JSONObject();
            json.put("content", "");

            JSONArray embeds = new JSONArray();
            JSONObject embed = new JSONObject();
            JSONObject thumbnail = new JSONObject();
            thumbnail.put("url", "https://mc-heads.net/avatar/" + event.getPlayer().getName());

            embed.put("title", "Player Join");
            embed.put("description", event.getPlayer().getName() + " joined the game");
            embed.put("color", 65280);
            embed.put("thumbnail", thumbnail);
            embeds.add(embed);

            json.put("embeds", embeds);
            json.put("username", _serverName);
            json.put("avatar_url", _serverIcon);

            sendWebhookMessage(json, _webhoString);
        }
    }
}
