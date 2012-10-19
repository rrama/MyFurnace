package me.rrama.MyFurnace;

/**
 *
 * @author Ben
 * All rights reserved to rrama.
 * No copying/stealing any part of the code (Exceptions; You are from the Bukkit team, you have written consent from rrama).
 * No copying/stealing ideas from the code (Exceptions; You are from the Bukkit team, you have written consent from rrama).
 * 
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyFurnace extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        InventoryType IT = event.getInventory().getType();
        if (IT == InventoryType.BREWING || IT == InventoryType.CHEST || IT == InventoryType.DISPENSER || IT == InventoryType.FURNACE) {
            if ((!event.getPlayer().hasPermission("myfurnace.override")) && event.getInventory().getViewers().size() > 1) {
                if (event.getPlayer() instanceof Player) {
                    Player P = (Player)event.getPlayer();
                    P.sendMessage(ChatColor.RED + "You cannot open this container block as someone else is using it.");
                }
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (event.getBlock() instanceof InventoryHolder) {
            if ((!event.getPlayer().hasPermission("myfurnace.override")) && (!((InventoryHolder)event.getBlock()).getInventory().getViewers().isEmpty())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot break this container block as someone is using it.");
            }
        }
    }
}
