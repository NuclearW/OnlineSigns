package com.nuclearw.onlinesigns;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class OnlineSignsBlockListener extends BlockListener {
	public static OnlineSigns plugin;
	
	public OnlineSignsBlockListener(OnlineSigns instance) {
		plugin = instance;
	}
	
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
