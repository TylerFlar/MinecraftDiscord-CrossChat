package com.tylerflar.listeners;

import static com.tylerflar.util.Util.sendWebhookMessage;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONObject;

import com.tylerflar.MCDisCrossChat;

public class PlayerChatListener implements Listener {
    private boolean _enabled = true;
    private String _webhoString = "";

    public PlayerChatListener(MCDisCrossChat plugin) {
        this._enabled = plugin.config.enabled;
        this._webhoString = plugin.config.chatWebhookUrl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (_enabled) {
            JSONObject json = new JSONObject();
            json.put("content", ChatColor.stripColor(event.getMessage()));
            json.put("username", event.getPlayer().getName());
            json.put("avatar_url", "https://mc-heads.net/avatar/" + event.getPlayer().getName());

            sendWebhookMessage(json, _webhoString);
        }
    }
}
