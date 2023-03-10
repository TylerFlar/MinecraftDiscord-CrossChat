package com.tylerflar.discord.commands;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class List {
    public List(SlashCommandEvent event) {
        StringBuilder list = new StringBuilder();
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        if (players.size() == 0) {
            event.reply("There are no players online!").setEphemeral(true).queue();;
            return;
        }

        for (Player player : players) {
            list.append(player.getName() + ", ");
        }

        event.reply("There are " + players.size() + " players online: " + list.toString()).setEphemeral(true).queue();
    }
}
