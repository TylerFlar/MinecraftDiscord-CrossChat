package com.tylerflar.discord;


import javax.security.auth.login.LoginException;

import com.tylerflar.discord.listeners.MessageListener;
import com.tylerflar.discord.listeners.ReadyListener;
import com.tylerflar.discord.listeners.SlashCommandListener;
import com.tylerflar.util.Config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordModule extends ListenerAdapter {
    private Config _config;

    JDA jda;
    MessageListener messageListener;

    public DiscordModule(Config config) {
        this._config = config;

        if (_config.enabled) {
            JDABuilder builder = JDABuilder.createDefault(_config.discordToken);

            try {
                messageListener = new MessageListener(_config.channelID);
                builder.addEventListeners(new ReadyListener());
                builder.addEventListeners(new SlashCommandListener(_config.guildID));
                builder.addEventListeners(messageListener);
                builder.enableIntents(GatewayIntent.GUILD_MESSAGES);

                jda = builder.build();
                jda.awaitReady();

                jda.getGuildById(_config.guildID).upsertCommand("stats", "Get stats about the server").queue();

                jda.getGuildById(_config.guildID).upsertCommand("list", "Get a list of players on the server").queue();
            } catch (LoginException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
    }
    
}
