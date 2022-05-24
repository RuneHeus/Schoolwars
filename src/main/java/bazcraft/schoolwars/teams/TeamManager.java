package bazcraft.schoolwars.teams;

import bazcraft.schoolwars.GameManager;
import bazcraft.schoolwars.minions.Path;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeamManager {

    private static final TeamManager INSTANCE = new TeamManager();

    private final int MAXHEALTH;
    private final Team RED;
    private final Team BLUE;

    private TeamManager() {
        MAXHEALTH = 100;

        RED = new Team("rood", new Location(Bukkit.getWorld("world"), 37.5, 40.2, -91.5, -90f, 0f), ChatColor.RED, GameManager.getInstance().getPaths()[0]);
        BLUE = new Team("blauw", new Location(Bukkit.getWorld("world"), 390.5, 40.2, -110.5, 90f, 0f), ChatColor.BLUE, GameManager.getInstance().getPaths()[1]);

        RED.setHealth(MAXHEALTH);
        BLUE.setHealth(MAXHEALTH);
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
        if (team != null) {
            team.removeSpeler(speler);
            if (team.equals(RED)) {
                BLUE.getPublicHealthBar().removePlayer(speler);
            } else {
                RED.getPublicHealthBar().removePlayer(speler);
            }
        }
    }

    public int getMaxHealth() {
        return MAXHEALTH;
    }

    public Team getRED() {
        return RED;
    }

    public Team getBLUE() {
        return BLUE;
    }

    public Team getTeam(Path path) {
        if (RED.getPath() == path) {
            return RED;
        } else if (BLUE.getPath() == path) {
            return BLUE;
        }
        return null;
    }

    public void clearTeams() {
        org.bukkit.scoreboard.Team temp = BLUE.getScoreboard();
        for (int i = 0; i < 2; i++) {
            for (String n : temp.getEntries()) {
                temp.removeEntry(n);
            }
            temp = RED.getScoreboard();
        }
    }

    public static TeamManager getInstance() {
        return INSTANCE;
    }

}
