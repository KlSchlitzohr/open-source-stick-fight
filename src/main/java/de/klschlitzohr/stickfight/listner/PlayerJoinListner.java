package de.klschlitzohr.stickfight.listner;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerJoinListner implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration cfg = YamlConfiguration
                .loadConfiguration(new File("plugins//Stickfight//playerdata.yml"));
        if (cfg.contains(player.getUniqueId() + ".inventory")) {
            player.teleport(cfg.getLocation(player.getUniqueId() + ".location"));
            List<ItemStack> inventory = (List<ItemStack>) cfg.get(player.getUniqueId() + ".inventory");
            player.getInventory().clear();
            inventory.forEach(itemStack -> {
                if (itemStack != null)
                player.getInventory().addItem(itemStack);
            });
            cfg.set(player.getUniqueId() + ".inventory",null);
            cfg.set(player.getUniqueId() + ".location",null);
            try {
                cfg.save(new File("plugins//Stickfight//playerdata.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
