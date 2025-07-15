package org.egyse.tapmasters_tutorial.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.egyse.tapmasters_tutorial.Tapmasters_tutorial;
import org.egyse.tapmasters_tutorial.models.Completed;
import org.egyse.tapmasters_tutorial.models.Step;
import org.egyse.tapmasters_tutorial.models.StepType;
import org.egyse.tapmasters_tutorial.models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager implements Listener {
    private final Tapmasters_tutorial pl = Tapmasters_tutorial.getInstance();
    private ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    public UserManager() {
        loadUsers();
        startSaveTask();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User u = users.getOrDefault(p.getUniqueId(), null);
        if (p.hasPlayedBefore()) {
            if (u == null) return;

            if (u.getStep() > 0) {
                Step step = pl.tutorialManager.getSteps().getOrDefault(u.getStep(), null);
                if (step == null) return;
                if (u.getStep() == 1) {
                    pl.messageStarted(p, pl.tutorialManager.started);
                }
                pl.message(p, step);
            }
        } else {
            if (u == null) {
                User user = new User(
                        p.getUniqueId(),
                        1,
                        0
                );
                users.put(p.getUniqueId(), user);
                pl.messageStarted(p, pl.tutorialManager.started);
                pl.message(p, pl.tutorialManager.getSteps().get(1));
            } else {
                if (u.getStep() > 0) {
                    Step step = pl.tutorialManager.getSteps().getOrDefault(u.getStep(), null);
                    if (step == null) return;
                    if (u.getStep() == 1) {
                        pl.messageStarted(p, pl.tutorialManager.started);
                    }
                    pl.message(p, step);
                }
            }
        }
    }

    public void logAction(Player p, StepType type, Double num) {
        User u = users.getOrDefault(p.getUniqueId(), null);
        if (u == null) return;
        if (u.getStep() <= 0) return;

        Step step = pl.tutorialManager.getSteps().getOrDefault(u.getStep(), null);
        if (step == null) return;

        if (step.getType() == type) {
            u.setProgress(u.getProgress() + num);
            users.put(p.getUniqueId(), u);
            if (u.getProgress() >= step.getReq()) {
                nextStep(u);
            }
        }
    }

    public void nextStep(User u) {
        if (u.getStep() + 1 <= pl.tutorialManager.getSteps().keySet().size()) {
            u.setStep(u.getStep() + 1);
            users.put(u.getUuid(), u);
            pl.message(Bukkit.getPlayer(u.getUuid()), pl.tutorialManager.getSteps().get(u.getStep()));
        } else {
            u.setStep(-1);
            Player p = Bukkit.getPlayer(u.getUuid());
            pl.message(p, pl.tutorialManager.completed);
            for (String cmd : pl.tutorialManager.completed.getCommands()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", p.getName()));
            }
            users.remove(u.getUuid());
        }
    }

    // json loading saving
    public void loadUsers() {
        Gson gson = new Gson();
        File file = new File(pl.getDataFolder().getAbsolutePath() + "/players.json");
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                System.out.println("Loading playerdata...");

                Type type = new TypeToken<HashMap<UUID, User>>(){}.getType();

                HashMap<UUID, User> loaded = gson.fromJson(reader, type);
                if (loaded != null) {
                    users.clear();
                    users.putAll(loaded);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUsers(boolean async) {
        ConcurrentHashMap<UUID, User> dataSnapshot = new ConcurrentHashMap<>(users);

        Runnable saveTask = () -> {
            Gson gson = new Gson();
            File file = new File(pl.getDataFolder(), "players.json");
            try {
                if (!file.exists()) file.createNewFile();
                try (Writer writer = new FileWriter(file, false)) {
                    Type type = new TypeToken<ConcurrentHashMap<UUID, User>>(){}.getType();
                    gson.toJson(dataSnapshot, type, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(pl, saveTask);
        } else {
            saveTask.run();
        }
    }

    public void startSaveTask() {
        Bukkit.getScheduler().runTaskTimer(pl, () -> {
            saveUsers(true);
        }, 120L, 20L*300);
    }
}
