package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.CHAT;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

public class MessageManager
{
    public static void SendChatForm(Player player)
    {
        Component messageForm = Component.text("======================").color(TextColor.color(0, 0,0)).decorate(TextDecoration.BOLD);
        player.sendMessage(messageForm);
    }
    public static void SendChatContent(Player player, String content, TextColor textColor)
    {
        Component messageContent = Component.text(content).color(textColor).decorate(TextDecoration.BOLD);
        player.sendMessage(messageContent);
    }
    public static void SendTitle(Player player, String title, String subTitle, TextColor titleTextColor, TextColor subTextColor)
    {
        Component titleContent = Component.text(title).color(titleTextColor).decorate(TextDecoration.BOLD);
        Component subContent = Component.text(subTitle).color(subTextColor);
        player.showTitle(Title.title(titleContent, subContent, Title.DEFAULT_TIMES));
    }
}
