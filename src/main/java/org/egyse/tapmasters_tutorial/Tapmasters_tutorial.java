package org.egyse.tapmasters_tutorial;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.egyse.tapmasters_tutorial.models.Completed;
import org.egyse.tapmasters_tutorial.models.DefaultFontInfo;
import org.egyse.tapmasters_tutorial.models.Step;
import org.egyse.tapmasters_tutorial.models.User;
import org.egyse.tapmasters_tutorial.utils.TutorialManager;
import org.egyse.tapmasters_tutorial.utils.UserManager;

import java.util.List;

public final class Tapmasters_tutorial extends JavaPlugin {
    private static Tapmasters_tutorial instance;

    public TutorialManager tutorialManager;
    public UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;

        tutorialManager = new TutorialManager();
        userManager = new UserManager();

        getServer().getPluginManager().registerEvents(userManager, this);
    }

    @Override
    public void onDisable() {
        userManager.saveUsers(false);
    }

    public void reload() {
        reloadConfig();
        tutorialManager.initialize();
    }

    int fadeIn = 20;
    int stay = 60;
    int fadeOut = 20;

    public void message(Player p, Step step) {
        for (String l : step.getMessage()) {
            sendCenteredMessage(p, ChatColor.translateAlternateColorCodes('&', l));
        }
        p.sendTitle(
                ChatColor.translateAlternateColorCodes('&', step.getTitle()),
                ChatColor.translateAlternateColorCodes('&', step.getSubtitle()),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public void message(Player p, Completed c) {
        for (String l : c.getMessage()) {
            sendCenteredMessage(p, ChatColor.translateAlternateColorCodes('&', l));
        }
        p.sendTitle(
                ChatColor.translateAlternateColorCodes('&', c.getTitle()),
                ChatColor.translateAlternateColorCodes('&', c.getSubtitle()),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public void messageStarted(Player p, List<String> l) {
        for (String s : l) {
            sendCenteredMessage(p, ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message){
        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb + message);
    }

    public static Tapmasters_tutorial getInstance() {
        return instance;
    }
}
