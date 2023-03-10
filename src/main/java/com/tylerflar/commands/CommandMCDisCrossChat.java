package com.tylerflar.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.tylerflar.MCDisCrossChat;
import com.tylerflar.util.Config;
import com.tylerflar.util.Updater;


public class CommandMCDisCrossChat implements CommandExecutor {
    Config _config;
    Updater _updater;

    public CommandMCDisCrossChat(MCDisCrossChat plugin) {
        this._config = plugin.config;
        this._updater = plugin.updater;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0]) {
            case "on":
            case "enable":
                _config.setEnabled(true);
                sender.sendMessage("MCDisCrossChat enabled!");
                return true;
            case "off":
            case "disable":
                _config.setEnabled(false);
                sender.sendMessage("MCDisCrossChat disabled!");
                return true;
            case "update":
                _updater.checkForUpdates();
                _updater.downloadUpdate();
                sender.sendMessage("MCDisCrossChat updated!");
                return true;
            default:
                return false;
        }
    }
    
}
