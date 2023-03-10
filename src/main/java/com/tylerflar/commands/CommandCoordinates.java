package com.tylerflar.commands;

import static com.tylerflar.util.Util.sendWebhookMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import com.tylerflar.MCDisCrossChat;
import com.tylerflar.util.Config;

import net.md_5.bungee.api.chat.TextComponent;

public class CommandCoordinates implements CommandExecutor {
    private Config config;

    public CommandCoordinates(MCDisCrossChat plugin) {
        this.config = plugin.config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        String coords = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();

        String dimension = "Overworld";
        String location = "My current";

        if (args != null) {
            if (!String.join(" ", args).equals("")) {
                location = String.join(" ", args);
            }
        }

        switch (player.getWorld().getEnvironment()) {
            case NETHER:
                dimension = "Nether";
                break;
            case THE_END:
                dimension = "End";
                break;
            default:
                break;
        }

        TextComponent message = new TextComponent("<" + player.getName() + "> " + location + " coordinates are: " + ChatColor.YELLOW + dimension + " " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + coords);
        
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.spigot().sendMessage(message);
        }

        JSONObject json = new JSONObject();
        json.put("content", location + " coordinates are: **" + dimension + "** `" + coords + "`");
        json.put("username", player.getName());
        json.put("avatar_url", "https://mc-heads.net/avatar/" + player.getName());

        sendWebhookMessage(json, config.eventsWebhookUrl);
        return true;
    }
    
}
