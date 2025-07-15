package org.egyse.tapmasters_tutorial.models;

import java.util.List;

public class Step {
    private StepType type;
    private double req;
    private List<String> message;
    private String title;
    private String subtitle;

    public Step(StepType type, double req, List<String> message, String title, String subtitle) {
        this.type = type;
        this.req = req;
        this.message = message;
        this.title = title;
        this.subtitle = subtitle;
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public double getReq() {
        return req;
    }

    public void setReq(double req) {
        this.req = req;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
