package fr.cocoraid.prodigyantispamkill;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cocoraid on 20/07/2017.
 */
public class KillListener implements Listener {

    private static Map<UUID,SpammerPlayer> spammers = new HashMap<>();

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if(killer.hasPermission("prodigyantispamkill.bypass")) return;
            spammers.putIfAbsent(killer.getUniqueId(), new SpammerPlayer(killer));
            SpammerPlayer sp = spammers.get(killer.getUniqueId());
            if(sp.getLastKill() != null && sp.getLastKill().equals(p))
                sp.addSpamTime();
            sp.setLastKill(p);

            if(sp.getSpamTime() == 6) {
                Bukkit.getOnlinePlayers().stream().filter(cur -> cur.hasPermission("prodigyantispamkill.check")).forEach(cur -> {
                    cur.sendMessage("§c§l" + killer.getName() + "§cest suspecté de spam kill un joueur pour gagner de l'argent");
                });
                killer.sendMessage("§4[Attention] §f§lVous êtes suspecté de spamkill un joueur pour de l'argent,  si vous continuez votre argent sera reset.");
                killer.playSound(killer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE,1,0);
            } else if(sp.getSpamTime() == 10) {
                ProdigyAntiSpamKill.economy.withdrawPlayer(killer,ProdigyAntiSpamKill.economy.getBalance(killer));
                killer.sendMessage("§bVotre argent a été supprimé puisque vous n'avez pas respecté l'avertissement");
                killer.playSound(killer.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE,1,2);
            }

        }
    }

    public static Map<UUID, SpammerPlayer> getSpammers() {
        return spammers;
    }
}
