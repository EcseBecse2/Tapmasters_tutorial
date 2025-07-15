package org.egyse.tapmasters_tutorial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.egyse.tapmasters_tutorial.Tapmasters_tutorial;
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

        if (strings.length == 2) {
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
        } else {
            commandSender.sendMessage("/tutorial reset <playername>");
        }

        return true;
    }
}
