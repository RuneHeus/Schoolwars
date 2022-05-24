package bazcraft.schoolwars.gui;

import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Scoreboard {
    private Player player;

    public Scoreboard(Player player) {
        this.player = player;
    }

    public void createBoard() {

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("tsb", "dummy", "§0§l<< §2§lSCHOOLWARS §0§l>> §r");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team team = TeamManager.getInstance().getTeam(player);

        obj.getScore(team.getPublicHealthBar().getTitle()).setScore(2);
        obj.getScore("§a" + team.getHealth() + "§c❤").setScore(1);
        obj.getScore("§eMinionpunten: §l" + team.getMinionPoints()).setScore(0);


        player.setScoreboard(board);
    }
}
