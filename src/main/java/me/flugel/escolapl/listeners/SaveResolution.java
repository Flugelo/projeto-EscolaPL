package me.flugel.escolapl.listeners;

import me.flugel.escolapl.Main;
import me.flugel.escolapl.objetos.ResolutionSave;
import me.flugel.escolapl.util.Cuboid;
import me.flugel.escolapl.util.utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;

public class SaveResolution implements Listener {
    @EventHandler
    public void saveReso(PlayerInteractEvent e){
        if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR){
            if(e.getPlayer().hasPermission("staff")){
                if(e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
                    if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eItem para setar as Resoluções")){
                        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                            e.getPlayer().sendMessage("§aLocalização 1. salvada com sucesso!");
                            Main.getInstance().getLocationHashMap().put("loc1", e.getClickedBlock().getLocation());
                            createResolution();
                            e.setCancelled(true);

                        }
                        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                            e.getPlayer().sendMessage("§aLocalização 2 salve com sucesso!");
                            Main.getInstance().getLocationHashMap().put("loc2", e.getClickedBlock().getLocation());
                            createResolution();
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    public void createResolution() {
        if(Main.getInstance().getLocationHashMap().containsKey("loc1")){
            if(Main.getInstance().getLocationHashMap().containsKey("loc2")){
                Location location = Main.getInstance().getLocationHashMap().get("loc1");
                Location location2 = Main.getInstance().getLocationHashMap().get("loc2");
                ResolutionSave resolutionSave = new ResolutionSave(new Cuboid(location,location2));
                Main.getInstance().getLocationHashMap().clear();

                FileConfiguration config = Main.getInstance().getConfiguracoes().getFileConfiguration();
                if(!config.contains(String.valueOf(resolutionSave.getQuadros()))){
                    config.set(resolutionSave.getQuadros()+ ".loc1", utils.serialize(location));
                    config.set(resolutionSave.getQuadros()+ ".loc2", utils.serialize(location2));

                    Main.getInstance().getConfiguracoes().save();
                }

            }
        }
    }
}
