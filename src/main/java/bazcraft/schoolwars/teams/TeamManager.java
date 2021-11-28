package bazcraft.schoolwars.teams;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.entity.Player;

public class TeamManager {

    private final Schoolwars plugin;

    private final int MAXHEALTH;
    private final Team RED;
    private final Team BLUE;

    public TeamManager(Team red, Team blue, Schoolwars plugin) {
        this.plugin = plugin;
        MAXHEALTH = 100;
        RED = red;
        BLUE = blue;
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
