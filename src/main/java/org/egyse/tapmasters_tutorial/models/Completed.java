package org.egyse.tapmasters_tutorial.models;

import java.util.List;

public class Completed {
    private String title;
    private String subtitle;
    private List<String> message;
    private List<String> commands;

    public Completed(String title, String subtitle, List<String> message, List<String> commands) {
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;
        this.commands = commands;
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

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
