package bazcraft.schoolwars;

import bazcraft.schoolwars.NPC.NPCManager;
import bazcraft.schoolwars.command.CommandManager;
import bazcraft.schoolwars.command.ConsoleCommandManager;
import bazcraft.schoolwars.command.PlayerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public final class Schoolwars extends JavaPlugin {

    private final EventListener eventListener;
    private final GameManager gameManager;
    private final CommandManager commandManager;
    private final PlayerCommandManager playerCommandManager;
    private final ConsoleCommandManager consoleCommandManager;
    private final TeamManager teamManager;
    private final NPCManager npcManager;

    public Schoolwars() {
        this.npcManager = new NPCManager();
        eventListener = new EventListener(this);
        gameManager = new GameManager(this, 12, new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5));
        commandManager = new CommandManager(this);
        playerCommandManager = new PlayerCommandManager(this);
        consoleCommandManager = new ConsoleCommandManager(this);
        teamManager = new TeamManager(this);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(eventListener, this);
        getCommand("spelers").setExecutor(commandManager);
        getCommand("spawnnpc").setExecutor(commandManager);
        getCommand("despawnnpc").setExecutor(commandManager);

        GameState.setGamestate(GameState.WAITING);

        //Doe dit altijd als laatste zodat je gemakkelijk in de console kan zien of er een error bij het opstarten is of niet
        getLogger().info(ChatColor.GREEN + getName() + " is enabled!");
    }

    @Override
    public void onDisable() {
        Team temp = teamManager.getRED().getScoreboard();
        for (String n : temp.getEntries()) {
            temp.removeEntry(n);
        }

        temp = teamManager.getBLUE().getScoreboard();
        for (String n : temp.getEntries()) {
            temp.removeEntry(n);
        }
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }
}
