package com.tylerflar.discord.listeners;


import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.md_5.bungee.api.ChatColor;

public class MessageListener extends ListenerAdapter{
    private String _channelID = "";

    public MessageListener(String channelID) {
        this._channelID = channelID;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!_channelID.equals("")) {
            if (event.getChannel().getId().equals(_channelID)) {
                if (!event.getMessage().isWebhookMessage()) {
                    StringBuilder attachment = new StringBuilder();
                    boolean isEmpty = event.getMessage().getContentDisplay().equals("");
                    
                    for(MessageEmbed embed : event.getMessage().getEmbeds()) {
                        if (!isEmpty) {
                            attachment.append("\n[Sent ");
                        } else {
                            attachment.append("[Attached ");
                        }

                        attachment.append("an embed");
                    }

                    for (MessageSticker sticker : event.getMessage().getStickers()) {
                        if (!isEmpty) {
                            attachment.append("\n[Sent ");
                        } else {
                            attachment.append("[Attached ");
                        }

                        attachment.append("a sticker");
                    }

                    for (ActionRow row : event.getMessage().getActionRows()) {
                        if (!isEmpty) {
                            attachment.append("\n[Sent ");
                        } else {
                            attachment.append("[Attached ");
                        }

                        attachment.append("an action row");
                    }

                    for (Message.Attachment attachment2 : event.getMessage().getAttachments()) {
                        if (!isEmpty) {
                            attachment.append("\n[Sent ");
                        } else {
                            attachment.append("[Attached ");
                        }

                        attachment.append(attachment2.getContentType()).append("]");
                    }

                    String message = ChatColor.DARK_AQUA + event.getAuthor().getAsTag() + ": " + ChatColor.WHITE + event.getMessage().getContentDisplay();
                    Bukkit.broadcastMessage(message + ChatColor.RED + attachment);
                }
            }
        }
    }


    public void setChannelID(String channelID) {
        this._channelID = channelID;
    }
}
