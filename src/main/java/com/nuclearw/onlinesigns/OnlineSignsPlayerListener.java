package com.nuclearw.onlinesigns;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.Math;

public class OnlineSignsPlayerListener implements Listener {
	public static OnlineSigns plugin;

	public OnlineSignsPlayerListener(OnlineSigns instance) {
		plugin = instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.updateSigns();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.updateSigns();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event) {
		plugin.updateSigns();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			//No fisticuffs
			if(!event.hasItem()) return;
			if(event.getItem().getTypeId() == 349) {
				//We just hit a sign, with a fish.
				if(event.getClickedBlock().getTypeId() == 63 || event.getClickedBlock().getTypeId() == 68) {
					Player slapper = event.getPlayer();

					if(!slapper.hasPermission("onlinesigns.create")) {
						slapper.sendMessage(ChatColor.RED + OnlineSigns.language[7]);
						return;
					}

					Boolean redFish = false;
					if(plugin.oneFish.containsKey(slapper)) {
						redFish = plugin.oneFish.get(slapper);
					}
					//Time to man up, we hit a sign with a fish.
					//But, is this the first time for this round?
					if(redFish) {
						//No, it is not, so we are going to add these together and such.
						String blueFish = plugin.blockToString(plugin.twoFish.get(slapper));

						//But is this the first sign?  Are we done with this round?
						if(event.getClickedBlock().equals(plugin.twoFish.get(slapper))) {
							//Not enough signs slapped?
							if(!plugin.board.containsKey(blueFish) || (plugin.maxplayers / 4 > plugin.board.get(blueFish).length)) {
								slapper.sendMessage(ChatColor.RED + OnlineSigns.language[6]);
								String numLeft = Double.toString(Math.ceil(plugin.maxplayers / 4) - plugin.board.get(blueFish).length);
								String msg = OnlineSigns.language[4];
								msg = msg.replace("<NUMBER>", ChatColor.GOLD + numLeft.substring(0, numLeft.length()-2) + ChatColor.RED);
								slapper.sendMessage(ChatColor.RED + msg);
							} else {
								slapper.sendMessage(ChatColor.GREEN + OnlineSigns.language[5]);
								plugin.oneFish.put(slapper, false);
								plugin.updateSigns();
							}
						} else {
							if(Math.ceil(plugin.maxplayers / 4) > plugin.board.get(blueFish).length) {
								plugin.addToBoard(blueFish, plugin.blockToString(event.getClickedBlock()));
								String numLeft = Double.toString(Math.ceil(plugin.maxplayers / 4) - plugin.board.get(blueFish).length);
								String msg = OnlineSigns.language[4];
								msg = msg.replace("<NUMBER>", ChatColor.GOLD + numLeft.substring(0, numLeft.length()-2) + ChatColor.RED);
								slapper.sendMessage(ChatColor.RED + msg);
							} else {
								slapper.sendMessage(ChatColor.RED + OnlineSigns.language[3]);
							}
						}

					} else {
						//Yes, it is, thus, this is our primary sign.
						plugin.twoFish.put(slapper, event.getClickedBlock());
						plugin.oneFish.put(slapper, true);
						plugin.board.put(plugin.blockToString(event.getClickedBlock()), new String[0]);
						slapper.sendMessage(ChatColor.GREEN + OnlineSigns.language[1]);
						slapper.sendMessage(ChatColor.GREEN + OnlineSigns.language[2]);
					}
				}
			}
		}
	}
}
