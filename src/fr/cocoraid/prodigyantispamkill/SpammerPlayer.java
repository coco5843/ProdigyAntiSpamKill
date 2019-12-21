package fr.cocoraid.prodigyantispamkill;

import org.bukkit.entity.Player;

/**
 * Created by cocoraid on 20/07/2017.
 */
public class SpammerPlayer {

    private Player p;
    private int spamTime = 1;
    private Player lastKill;
    private int time = 120;

    public SpammerPlayer(Player p) {
        this.p = p;
    }

    public void setLastKill(Player lastKill) {
        this.lastKill = lastKill;
    }

    public void addSpamTime() {
        spamTime++;
    }

    public Player getLastKill() {
        return lastKill;
    }

    public int getSpamTime() {
        return spamTime;
    }

    public void decreaseTime() {
        time--;
    }

    public int getTime() {
        return time;
    }
}
