package bazcraft.schoolwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

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
