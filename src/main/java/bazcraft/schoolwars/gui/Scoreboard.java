package bazcraft.schoolwars.gui;

import bazcraft.schoolwars.GameManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.tools.CounterRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Scoreboard {
    private final Player player;

    public Scoreboard(Player player) {
        this.player = player;
    }

    public void createBoard() {

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("tsb", "dummy", "§0§l<< §2§lSCHOOLWARS §0§l>> §r");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team team = TeamManager.getInstance().getTeam(player);

        CounterRunnable specialEventTimer = GameManager.getInstance().getSpecialEventTimer();

        if (specialEventTimer != null && !specialEventTimer.isCancelled()) {
            if (specialEventTimer.getRemaining() > 0) {
                if (specialEventTimer.getRemaining() <= 60) {
                    obj.getScore("§cspecial event start binnen: §e" + specialEventTimer.getRemaining() + "s").setScore(3);
                } else {
                    int rest = specialEventTimer.getRemaining() %60;
                    obj.getScore("§cspecial event start binnen: §e" + (((specialEventTimer.getRemaining()- rest)/60) + 1) + "m").setScore(3);
                }
            } else {
                obj.getScore("§cspecial event bezig...").setScore(3);
            }
        } else {
            obj.getScore("§cGeen special events").setScore(3);
        }

        obj.getScore(team.getPublicHealthBar().getTitle()).setScore(2);
        obj.getScore("§a" + team.getHealth() + "§c❤").setScore(1);
        obj.getScore("§eMinionpunten: §l" + team.getMinionPoints()).setScore(0);


        player.setScoreboard(board);
    }
}
