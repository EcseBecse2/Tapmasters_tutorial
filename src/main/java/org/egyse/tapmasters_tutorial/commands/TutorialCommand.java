package org.egyse.tapmasters_tutorial.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.egyse.tapmasters_tutorial.Tapmasters_tutorial;
import org.egyse.tapmasters_tutorial.models.StepType;
import org.egyse.tapmasters_tutorial.models.User;

public class TutorialCommand implements CommandExecutor {
    private final Tapmasters_tutorial pl = Tapmasters_tutorial.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage("No permission.");
            }
        }

        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                pl.reload();
                commandSender.sendMessage("Config successfully reloaded!");
            }
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("reset")) {
                User u = pl.userManager.getUserByName(strings[1]);
                if (u == null) {
                    commandSender.sendMessage("This player has never played before!");
                } else {
                    u.setStep(1);
                    u.setProgress(0);
                    pl.userManager.getUsers().put(u.getUuid(), u);
                }
            } else {
                commandSender.sendMessage("/tutorial reset <playername>");
            }
        } else if (strings.length == 3) {
            Player p = Bukkit.getPlayer(strings[0]);
            if (p == null) {
                commandSender.sendMessage("invalid player");
                return true;
            }

            StepType stepType = null;
            try {
                stepType = StepType.valueOf(strings[1]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            double amount;
            try {
                amount = Double.parseDouble(strings[2]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            pl.userManager.logAction(p, stepType, amount);
        } else {
            commandSender.sendMessage("/tutorial reset <playername>");
        }

        return true;
    }
}
