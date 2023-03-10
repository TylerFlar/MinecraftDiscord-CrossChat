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
    private String _apiURL = "https://api.github.com/repos/tylerflar/mcdiscrosschat/releases/latest";

    public boolean updateAvailable;
    public String[] currentVersion;
    public Float newVersion;
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
            _plugin.getLogger().info("Downloading update...");
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

    private void deleteOldFiles() {
        File folder = new File("./plugins");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith("mcdiscrosschat")) {
                    if (file.getName().endsWith(".jar")) {
                        if (!file.getName().endsWith(currentVersion + ".jar")) {
                            String[] ver = file.getName().replace("mcdiscrosschat-", "").replace(".jar", "").split("\\.");
                            if (isCurrentNewer(ver)) {
                                file.delete();
                                _plugin.getLogger().info("Deleted old version " + file.getName());
                            }
                        }
                    }
                } 
            }
        }
    }

    private boolean isCurrentNewer(String[] ver) {
        if (Integer.parseInt(this.currentVersion[0]) > Integer.parseInt(ver[0])) {
            if (Integer.parseInt(this.currentVersion[1]) > Integer.parseInt(ver[1])) {
                return Integer.parseInt(this.currentVersion[2]) > Integer.parseInt(ver[2]);
            }
        }

        return false;
    }

    public void checkForUpdates() {
        String[] currentVersion = _plugin.getDescription().getVersion().split("\\.");
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

                Float api_version = Float.parseFloat(data.get("tag_name").toString().replace("v", ""));

                if (!api_version.equals(currentVersion)) {
                    setUpdateAvailable(true);
                    setNewVersion(api_version);

                    JSONObject newAsset = (JSONObject) ((JSONArray) data.get("assets")).get(0);
                    setDownloadURL((String) newAsset.get("browser_download_url"));
                    setNewFileName((String) newAsset.get("name"));

                    _plugin.getLogger().info("Update to version " + api_version + " is available");
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void setNewFileName(String string) {
        this.newFileName = string;
    }

    private void setDownloadURL(String string) {
        this.downloadURL = string;
    }

    private void setNewVersion(Float newVersion2) {
        this.newVersion = newVersion2;
    }

    private void setUpdateAvailable(boolean b) {
        this.updateAvailable = b;
    }

    private void setCurrentVersion(String[] currentVersion2) {
        this.currentVersion = currentVersion2;
    }
    
}
