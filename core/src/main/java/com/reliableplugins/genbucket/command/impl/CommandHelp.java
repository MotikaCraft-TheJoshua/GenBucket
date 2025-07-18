package com.reliableplugins.genbucket.command.impl;

import com.reliableplugins.genbucket.command.AbstractCommand;
import com.reliableplugins.genbucket.command.CommandBuilder;
import com.reliableplugins.genbucket.util.Util;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "help", alias = {"h"}, permission = "oregenerator.help", description = "basic help command")
public class CommandHelp extends AbstractCommand {

    private BaseCommand baseCommand;

    public CommandHelp(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        AbstractCommand[] commands = baseCommand.getCommands().toArray(new AbstractCommand[0]);
        Player player = Bukkit.getPlayer(sender.getName());

        int page = 0;

        if (args.length != 0) {
            if (args[0].matches("^[0-9]"))
                page = (Integer.parseInt(args[0]) - 1);
        }

        int maxPage = (int) Math.floor(commands.length / 5) + 1;

        String header = "&7&m----------&7[ &2GenBucket &a%s&7/&a%s &7]&m----------";
        sender.sendMessage(Util.color(String.format(header, (page + 1), maxPage)));

        for (int i = (page * 5); i < (page * 5) + 5; i++) {

            if (i > commands.length - 1) continue;

            AbstractCommand command = commands[i];

            String line = "&2/genbucket %s &a%s";
            TextComponent message = new TextComponent(Util.color(String.format(line, command.getLabel(), command.getDescription())));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + command.getPermission()).create()));

            if (player != null) {
                player.spigot().sendMessage(message);
            } else {
                sender.sendMessage(message.getText());
            }
        }

        String footer = "&7&oHover to view permissions";
        sender.sendMessage(Util.color(footer));
    }
}