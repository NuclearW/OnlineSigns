package com.nuclearw.onlinesigns;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class OnlineSignsPluginListener extends ServerListener {
	public static OnlineSigns plugin;
	
	public OnlineSignsPluginListener(OnlineSigns instance) {
		plugin = instance;
	}
	
    public void onPluginEnable(PluginEnableEvent event) {
        OnlineSignsPermissionsHandler.onEnable(event.getPlugin());
    }
	
}
