package org.egyse.tapmasters_tutorial.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.egyse.scrates.API.CrateOpenEvent;
import org.egyse.tapmasters_tutorial.Tapmasters_tutorial;
import org.egyse.tapmasters_tutorial.models.StepType;

public class CrateOpenListener implements Listener {
    private final Tapmasters_tutorial pl = Tapmasters_tutorial.getInstance();

    @EventHandler
    public void crateOpen(CrateOpenEvent e) {
        pl.userManager.logAction(e.getPlayer(), StepType.OPEN_CRATE, e.getAmount().doubleValue());
    }
}
