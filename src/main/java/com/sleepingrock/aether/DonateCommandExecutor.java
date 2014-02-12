package com.sleepingrock.aether;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by Chris on 2/9/14.
 */
public class DonateCommandExecutor implements CommandExecutor {

    private ADonate plugin;

    public DonateCommandExecutor(ADonate plugin) {
        this.plugin = plugin;
    }
    //TODO cleanup and make the item part more modular
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] param) {
        if (command.getName().equalsIgnoreCase("donate")) {
            if (param.length >= 1) {
                if (param[0].equalsIgnoreCase("buy")) {
                    if (param.length >= 2) {
                        switch (param[1].toLowerCase()) {
                            case "vip":
                                buyVip(1, commandSender);
                                break;
                            default:
                                commandSender.sendMessage("Invalid item use: /donate info");
                        }
                        return true;
                    } else {
                        commandSender.sendMessage("Not enough arguments use: /donate buy vip");
                        return true;
                    }
                } else if (param[0].equalsIgnoreCase("info")) {
                    if (param.length >= 2 && param[1].equalsIgnoreCase("kit")) {
                        commandSender.sendMessage("The vip kit contains");
                        commandSender.sendMessage("1 Leather armour set with enchants");
                        commandSender.sendMessage("The helmet is enchanted with Aqua Affinity I and Respiration III");
                        commandSender.sendMessage("The boots are enchanted with Feather Falling IV");
                        commandSender.sendMessage("One of each diamond tools and sword");
                        commandSender.sendMessage("Cooldown for kit is one day");
                    } else {
                        commandSender.sendMessage("Purchase with 1000 donate points: /donate buy vip");
                        commandSender.sendMessage("VIP bonus -----");
                        commandSender.sendMessage("50% more MCMMO experience");
                        commandSender.sendMessage("The ability to use tpa (teleport ask)");
                        commandSender.sendMessage("The ability to use home, you still have to use a bed to set it");
                        commandSender.sendMessage("The vip kit (/kit vip) use /donate info kit for more information");
                        commandSender.sendMessage("VIP bonus -----");
                    }
                    return true;
                }
            }
            commandSender.sendMessage("Not enough arguments use: /donate (buy, info)");
            return true;
        }
        return false;
    }
    private void buyVip(int level, CommandSender sender) {
        if (plugin.dbConnection.getPlayerVipLevel(sender.getName()) <= 0) {
            if (plugin.dbConnection.getPlayerPoints(sender.getName()) >= 1000) {
                plugin.dbConnection.setPlayerPoints(sender.getName(), plugin.dbConnection.getPlayerPoints(sender.getName()) - 1000);
                plugin.dbConnection.setPlayerVipLevel(sender.getName());
                plugin.permission.playerAddGroup((Player)sender, "VIP");
                sender.sendMessage("You are now VIP!");
                ADonate.log.info(sender.getName() + " Bought vip!");
            } else {
                sender.sendMessage("Sorry you do not have enough donate points! Please visit <SITE> to get more.");
            }
        } else {
            sender.sendMessage("You are already VIP");
        }
    }

    //TODO this later
    private void buyItem(String name, CommandSender sender) {

    }
}
