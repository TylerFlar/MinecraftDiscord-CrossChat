package com.tylerflar;

import static com.tylerflar.util.Util.sendServerStartStopMessage;

import org.bukkit.plugin.java.JavaPlugin;

import com.tylerflar.commands.CommandCoordinates;
import com.tylerflar.commands.CommandMCDisCrossChat;
import com.tylerflar.discord.DiscordModule;
import com.tylerflar.listeners.PlayerAdvancementDoneListener;
import com.tylerflar.listeners.PlayerChatListener;
import com.tylerflar.listeners.PlayerDeathListener;
import com.tylerflar.listeners.PlayerJoinListener;
import com.tylerflar.listeners.PlayerQuitListener;
import com.tylerflar.util.Config;
import com.tylerflar.util.Updater;

/*
 * mcdiscrosschat java plugin
 */
public final class MCDisCrossChat extends JavaPlugin {
  public DiscordModule discordModule;
  public Config config = new Config(this);
  public Updater updater = new Updater(this);

  @Override
  public void onEnable()
  {
    PlayerAdvancementDoneListener playerAdvancementDoneListener = new PlayerAdvancementDoneListener(this);
    PlayerChatListener playerChatListener = new PlayerChatListener(this);
    PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);
    PlayerQuitListener playerQuitListener = new PlayerQuitListener(this);
    PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);

    getServer().getPluginManager().registerEvents(playerAdvancementDoneListener, this);
    getServer().getPluginManager().registerEvents(playerChatListener, this);
    getServer().getPluginManager().registerEvents(playerJoinListener, this);
    getServer().getPluginManager().registerEvents(playerQuitListener, this);
    getServer().getPluginManager().registerEvents(playerDeathListener, this);

    sendServerStartStopMessage(config, "start");

    discordModule = new DiscordModule(config);

    getCommand("mcdiscrosschat").setExecutor(new CommandMCDisCrossChat(this));
    getCommand("coordinates").setExecutor(new CommandCoordinates(this));
  }

  public void onDisable()
  {
    // shutdown discord module
    discordModule.shutdown();
    sendServerStartStopMessage(config, "stop");
  }
}
