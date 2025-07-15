package org.egyse.tapmasters_tutorial.models;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String name;
    private int step;
    private double progress;

    public User(UUID uuid, String name, int step, double progress) {
        this.uuid = uuid;
        this.name = name;
        this.step = step;
        this.progress = progress;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
