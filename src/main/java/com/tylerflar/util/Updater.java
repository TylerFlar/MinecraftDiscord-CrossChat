package com.tylerflar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tylerflar.MCDisCrossChat;

public class Updater {
    private Config _config;
    private MCDisCrossChat _plugin;
    private String _apiURL = "https://api.github.com/repos/tylerflar/MinecraftDiscord-CrossChat/releases/latest";

    public boolean updateAvailable;
    public String currentVersion;
    public String newVersion;
    public String downloadURL;
    public String newFileName;

    public Updater(MCDisCrossChat plugin) {
        this._plugin = plugin;
        this._config = plugin.config;
        checkForUpdates();
        deleteOldFiles();
        if (_config.autoUpdate && updateAvailable) {
            downloadUpdate();
        }
    }

    public void downloadUpdate() {
        if (newVersion != null && downloadURL != null) {
            _plugin.getLogger().info("Downloading update to " + newFileName + "...");
            URL url = null;
            try {
                url = new URL(downloadURL);
                InputStream in = url.openStream();
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream("./plugins/" + newFileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                _plugin.getLogger().info("Downloaded update to " + newFileName + "! Please restart the server to apply the update.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete old file in plugins folder named mcdiscrosschat-x.x.x.jar where x.x.x is less than currentVersion 
    private void deleteOldFiles() {
        File folder = new File("./plugins/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.startsWith("mcdiscrosschat-") && fileName.endsWith(".jar")) {
                    String version = fileName.replace("mcdiscrosschat-", "").replace(".jar", "");
                    if (isNewerVersion(currentVersion, version)) {
                        file.delete();
                    }
                }
            }
        }
    }

    public void checkForUpdates() {
        String currentVersion = _plugin.getDescription().getVersion();
        setCurrentVersion(currentVersion);

        try {
            URL url = new URL(_apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                Scanner scanner = new Scanner(url.openStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject data = (JSONObject) parser.parse(response);

                String api_version = data.get("tag_name").toString().replace("v", "");

                if (isNewerVersion(api_version, currentVersion)) {
                    setUpdateAvailable(true);
                    setNewVersion(api_version);

                    JSONObject newAsset = (JSONObject) ((JSONArray) data.get("assets")).get(0);
                    setDownloadURL((String) newAsset.get("browser_download_url"));
                    setNewFileName((String) newAsset.get("name"));

                    _plugin.getLogger().info("New version available! Downloading update...");
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //Helper funciton to check if versionA is newer than versionB in the format xx.xx.xx
    public static boolean isNewerVersion(String versionA, String versionB) {
        String[] versionAArray = versionA.split("\\.");
        String[] versionBArray = versionB.split("\\.");
        for (int i = 0; i < versionAArray.length; i++) {
            if (Integer.parseInt(versionAArray[i]) > Integer.parseInt(versionBArray[i])) {
                return true;
            }
        }
        return false;
    }

    

    private void setNewFileName(String string) {
        this.newFileName = string;
    }

    private void setDownloadURL(String string) {
        this.downloadURL = string;
    }

    private void setNewVersion(String string) {
        this.newVersion = string;
    }

    private void setUpdateAvailable(boolean b) {
        this.updateAvailable = b;
    }

    private void setCurrentVersion(String string) {
        this.currentVersion = string;
    }
    
}
