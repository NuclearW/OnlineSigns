package com.nuclearw.onlinesigns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class OnlineSignsPluginListener implements Listener {
	public static OnlineSigns plugin;

	public OnlineSignsPluginListener(OnlineSigns instance) {
		plugin = instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        OnlineSignsPermissionsHandler.onEnable(event.getPlugin());
    }
}
