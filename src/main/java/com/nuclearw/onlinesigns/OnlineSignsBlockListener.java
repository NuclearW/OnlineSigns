package com.nuclearw.onlinesigns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnlineSignsBlockListener implements Listener {
	public static OnlineSigns plugin;

	public OnlineSignsBlockListener(OnlineSigns instance) {
		plugin = instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		int changedMaterial = event.getBlock().getTypeId();
		if(changedMaterial == 63 || changedMaterial == 68) {
		//	plugin.log.info("Block broken was a sign.");
			if(plugin.board.containsKey(plugin.blockToString(event.getBlock()))) {
				plugin.board.remove(plugin.blockToString(event.getBlock()));
			}
		}
	}
}
