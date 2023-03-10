package com.tylerflar.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tylerflar.MCDisCrossChat;


public class Config {
    private MCDisCrossChat _plugin;
    public String pluginPath;
    public String configPath;
    public JSONObject defaultConfig = new JSONObject();
    
    public boolean enabled;
    public boolean autoUpdate;
    public String chatWebhookUrl; // https://discord.com/api/webhooks/...
    public String eventsWebhookUrl;
    public String serverName;
    public String serverIcon;
    public String discordToken;
    public String channelID;
    public String guildID;

    public Config(MCDisCrossChat plugin) {
        this._plugin = plugin;
        this.pluginPath = plugin.getDataFolder().getPath();
        this.configPath = pluginPath + "/config.json";

        setDefaults();
        checkConfigPath();
        checkConfigUpdates();
        getConfig();
    }

    private void getConfig() {
        JSONObject config = new JSONObject();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            config = (JSONObject) parser.parse(fileReader);
            fileReader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        setEnabled((boolean) config.get("enabled"));
        setAutoUpdate((boolean) config.get("autoUpdate"));
        setChatWebhookUrl((String) config.get("chatWebhookUrl"));
        setEventsWebhookUrl((String) config.get("eventsWebhookUrl"));
        setServerName((String) config.get("serverName"));
        setServerIcon((String) config.get("serverIcon"));
        setDiscordToken((String) config.get("discordToken"));
        setChannelID((String) config.get("channelID"));
        setGuildID((String) config.get("guildID"));
    }

    private void setGuildID(String string) {
        this.guildID = string;
        setConfig();
    }

    private void setChannelID(String string) {
        this.channelID = string;
        setConfig();
    }

    private void setDiscordToken(String string) {
        this.discordToken = string;
        setConfig();
    }

    private void setServerIcon(String string) {
        this.serverIcon = string;
        setConfig();
    }

    private void setServerName(String string) {
        this.serverName = string;
        setConfig();
    }

    private void setEventsWebhookUrl(String string) {
        this.eventsWebhookUrl = string;
        setConfig();
    }

    private void setChatWebhookUrl(String string) {
        this.chatWebhookUrl = string;
        setConfig();
    }

    public void setEnabled(boolean b) {
        this.enabled = b;
        setConfig();
    }

    private void setAutoUpdate(boolean b) {
        this.autoUpdate = b;
        setConfig();
    }

    private void setConfig() {
        JSONObject config = new JSONObject();
        config.put("enabled", enabled);
        config.put("autoUpdate", autoUpdate);
        config.put("chatWebhookUrl", chatWebhookUrl);
        config.put("eventsWebhookUrl", eventsWebhookUrl);
        config.put("serverName", serverName);
        config.put("serverIcon", serverIcon);
        config.put("discordToken", discordToken);
        config.put("channelID", channelID);
        config.put("guildID", guildID);

        File file = new File(configPath);
        if (file.delete()) {
            if(!Files.exists(Paths.get(configPath))) {
                try {
                    Files.write(Paths.get(configPath), config.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkConfigUpdates() {
        JSONObject config = new JSONObject();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            config = (JSONObject) parser.parse(fileReader);
            fileReader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        for (Object o : defaultConfig.keySet()) {
            String key = (String) o;
            if (!config.containsKey(key)) {
                _plugin.getLogger().info("Added " + key + " to config.json");
                config.put(key, defaultConfig.get(key));
            }
        }

        File file = new File(configPath);
        if (file.delete()) {
            if(!Files.exists(Paths.get(configPath))) {
                try {
                    Files.write(Paths.get(configPath), config.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setDefaults() {
        defaultConfig.put("enabled", true);
        defaultConfig.put("autoUpdate", true);
        defaultConfig.put("chatWebhookUrl", "");
        defaultConfig.put("eventsWebhookUrl", "");
        defaultConfig.put("serverName", "My Server");
        defaultConfig.put("serverIcon", "https://packpng.com/static/pack.png");
        defaultConfig.put("discordToken", "...");
        defaultConfig.put("channelID", "...");
        defaultConfig.put("guildID", "...");
    }

    private void checkConfigPath() {
        if (!Files.exists(Paths.get("./plugins/mcdiscrosschat"))) {
            File file = new File("./plugins/mcdiscrosschat");
            if (file.mkdir()) {
                System.out.println("Created mcdiscrosschat directory");
            } else {
                System.out.println("Failed to create mcdiscrosschat directory");
            }
        }

        if (!Files.exists(Paths.get(configPath))) {
            try {
                Files.write(Paths.get(configPath), defaultConfig.toJSONString().getBytes());
                System.out.println("Created config.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
