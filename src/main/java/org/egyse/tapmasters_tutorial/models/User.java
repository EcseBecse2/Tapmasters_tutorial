package org.egyse.tapmasters_tutorial.models;

import java.util.UUID;

public class User {
    private UUID uuid;
    private int step;
    private double progress;

    public User(UUID uuid, int step, double progress) {
        this.uuid = uuid;
        this.step = step;
        this.progress = progress;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
