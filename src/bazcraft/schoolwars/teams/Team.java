package bazcraft.schoolwars.teams;

import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.minions.Path;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Team {

    private final org.bukkit.scoreboard.Team scoreboard;
    private final BossBar teamHealthBar;
    private final BossBar publicHealthBar;
    private final Location spawn;
    private int minionPoints;
    private final Path path;
    private boolean beantwoordenVragenN; //N = normale vragen
    private boolean beantwoordenVragenS; //S = speciale vragen

    public Team(String naam, Location spawn, ChatColor color, Path path) {

        org.bukkit.scoreboard.Team temp = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(naam);
        if (temp == null) {
            temp = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(naam);
        }
        scoreboard = temp;

        teamHealthBar = Bukkit.createBossBar(color + naam.toUpperCase() + " §e(jij)", BarColor.valueOf(color.name()), BarStyle.SOLID);
        publicHealthBar = Bukkit.createBossBar(color + naam.toUpperCase(), teamHealthBar.getColor(), teamHealthBar.getStyle());
        this.spawn = spawn;
        this.path = path;

        scoreboard.setAllowFriendlyFire(false);
        scoreboard.setColor(color);
    }

    public void addSpeler(Player speler) {
        scoreboard.addEntry(speler.getName());
        teamHealthBar.addPlayer(speler);
    }

    public void removeSpeler(Player speler) {
        scoreboard.removeEntry(speler.getName());
        teamHealthBar.removePlayer(speler);
    }

    public void teleportSpelers() {
        for (String n : scoreboard.getEntries()) {
            Bukkit.getPlayer(n).teleport(spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    public void removeHealth(int damage) {
        double convertedDamage = (damage*1.0)/100;
        double remainingHealth = teamHealthBar.getProgress()-convertedDamage;
        if (remainingHealth < 0) {
            teamHealthBar.setProgress(0);
            publicHealthBar.setProgress(0);
        } else {
            teamHealthBar.setProgress(remainingHealth);
            publicHealthBar.setProgress(remainingHealth);
        }
        if (teamHealthBar.getProgress() == 0) {
            Schoolwars.getPlugin(Schoolwars.class).getGameManager().endGame(this);
        }
    }

    public void setHealth(int health) {
        teamHealthBar.setProgress((health*1.0)/100);
    }

    public int getHealth() {
        return ((int) (teamHealthBar.getProgress())*100);
    }

    protected BossBar getTeamHealthBar() {
        return teamHealthBar;
    }

    public BossBar getPublicHealthBar() {
        return publicHealthBar;
    }

    public org.bukkit.scoreboard.Team getScoreboard() {
        return scoreboard;
    }

    public Location getSpawn() {
        return spawn;
    }

    public int getMinionPoints() {
        return minionPoints;
    }

    public void setMinionPoints(int minionPoints) {
        this.minionPoints = minionPoints;
    }

    public Path getPath() {
        return path;
    }

    public boolean isBeantwoordenVragenN() {
        return beantwoordenVragenN;
    }

    public boolean isBeantwoordenVragenS() {
        return beantwoordenVragenS;
    }

    public void setBeantwoordenVragenS(boolean beantwoordenVragenS) {
        this.beantwoordenVragenS = beantwoordenVragenS;
    }

    public void setBeantwoordenVragenN(boolean beantwoordenVragenN) {
        this.beantwoordenVragenN = beantwoordenVragenN;
    }
}
