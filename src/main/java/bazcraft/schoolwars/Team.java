package bazcraft.schoolwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Team {

    private final org.bukkit.scoreboard.Team scoreboard;
    private int health;
    private final Location spawn;

    public Team(String naam, int health, Location spawn, ChatColor color) {

        org.bukkit.scoreboard.Team temp = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(naam);
        if (temp == null) {
            temp = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(naam);
        }
        scoreboard = temp;

        this.health = health;
        this.spawn = spawn;

        scoreboard.setAllowFriendlyFire(false);
        scoreboard.setColor(color);

    }

    public void addSpeler(Player speler) {
        scoreboard.addEntry(speler.getName());
    }

    public void teleportSpelers() {
        for (String n : scoreboard.getEntries()) {
            Bukkit.getPlayer(n).teleport(spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    public org.bukkit.scoreboard.Team getScoreboard() {
        return scoreboard;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Location getSpawn() {
        return spawn;
    }
}
