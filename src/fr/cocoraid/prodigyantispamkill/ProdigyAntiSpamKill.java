package fr.cocoraid.prodigyantispamkill;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by cocoraid on 20/07/2017.
 */
public class ProdigyAntiSpamKill extends JavaPlugin {

    public static  Economy economy = null;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new KillListener(),this);


        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                KillListener.getSpammers().keySet().forEach(id -> KillListener.getSpammers().get(id).decreaseTime());
                KillListener.getSpammers().keySet().removeIf(id -> KillListener.getSpammers().get(id).getTime() <= 0);
            }
        }.runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {

    }


}
