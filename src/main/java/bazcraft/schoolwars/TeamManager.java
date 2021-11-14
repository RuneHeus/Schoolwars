package bazcraft.schoolwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeamManager {

    private final Schoolwars plugin;

    private final int MAXHEALTH;
    private final Team RED;
    private final Team BLUE;

    public TeamManager(Schoolwars plugin) {

        this.plugin = plugin;

        MAXHEALTH = 100;
        RED = new Team("rood", MAXHEALTH, new Location(Bukkit.getWorld("world"), 0, 0, 0), ChatColor.RED);
        BLUE = new Team("blauw", MAXHEALTH, new Location(Bukkit.getWorld("world"), 0, 0, 0), ChatColor.BLUE);

    }

    public Team getTeam(Player player) {
        if (RED.getTeamHealthBar().getPlayers().contains(player)) {
            return RED;
        } else if (BLUE.getTeamHealthBar().getPlayers().contains(player)) {
            return BLUE;
        }
        return null;
    }

    public void addPlayer(Team team, Player speler) {
        team.addSpeler(speler);
        if (team.equals(RED)) {
            BLUE.getPublicHealthBar().addPlayer(speler);
        } else {
            RED.getPublicHealthBar().addPlayer(speler);
        }
    }

    public void removePlayer(Team team, Player speler) {
        team.removeSpeler(speler);
        if (team.equals(RED)) {
            BLUE.getPublicHealthBar().removePlayer(speler);
        } else {
            RED.getPublicHealthBar().removePlayer(speler);
        }
    }

    public int getMAXHEALTH() {
        return MAXHEALTH;
    }

    public Team getRED() {
        return RED;
    }

    public Team getBLUE() {
        return BLUE;
    }
}
