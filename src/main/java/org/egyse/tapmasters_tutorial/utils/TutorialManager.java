package org.egyse.tapmasters_tutorial.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.egyse.tapmasters_tutorial.Tapmasters_tutorial;
import org.egyse.tapmasters_tutorial.models.Completed;
import org.egyse.tapmasters_tutorial.models.Step;
import org.egyse.tapmasters_tutorial.models.StepType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorialManager {
    private final Tapmasters_tutorial pl = Tapmasters_tutorial.getInstance();

    private HashMap<Integer, Step> steps = new HashMap<>();
    public List<String> started = new ArrayList<>();
    public Completed completed = null;

    public TutorialManager() {
        initialize();
    }

    public void initialize() {
        // steps
        steps = new HashMap<>();
        ConfigurationSection configurationSection = pl.getConfig().getConfigurationSection("steps");
        if (configurationSection != null) {
            for (String item : configurationSection.getKeys(false)) {
                ConfigurationSection section = configurationSection.getConfigurationSection(item);

                int num = Integer.parseInt(item);
                StepType type = StepType.valueOf(section.getString("type"));
                double req = section.getDouble("req");
                List<String> message = section.getStringList("message");
                String title = pl.getConfig().getString("step-title").replace("{step}", String.valueOf(num));
                String subtitle = pl.getConfig().getString("step-subtitle").replace("{step}", String.valueOf(num));

                steps.put(num,
                        new Step(type, req, message, title, subtitle)
                        );
            }
        }

        // started
        started = new ArrayList<>(pl.getConfig().getStringList("started.message"));

        // completed
        completed = new Completed(
                pl.getConfig().getString("title"),
                pl.getConfig().getString("subtitle"),
                pl.getConfig().getStringList("message"),
                pl.getConfig().getStringList("commands")
        );
    }

    public HashMap<Integer, Step> getSteps() {
        return steps;
    }
}
