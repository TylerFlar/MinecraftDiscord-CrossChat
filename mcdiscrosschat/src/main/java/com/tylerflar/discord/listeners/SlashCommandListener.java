package com.tylerflar.discord.listeners;

import com.tylerflar.discord.commands.List;
import com.tylerflar.discord.commands.Stats;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    private String _guildID = "";

    public SlashCommandListener(String guildID) {
        this._guildID = guildID;
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getGuild() == null) {
            event.reply("This command can only be used in a server!").queue();
            return;
        }
        if (!event.getGuild().getId().equals(_guildID)) {
            event.reply("This command can only be used in the server with the ID " + _guildID + "!").queue();
            return;
        }
        switch (event.getName()) {
            case "list":
                new List(event);
                break;
            case "stats":
                new Stats(event);
                break;
        }
    }
}
