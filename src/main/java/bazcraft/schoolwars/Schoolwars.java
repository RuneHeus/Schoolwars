package bazcraft.schoolwars;

import bazcraft.schoolwars.NPC.NPCManager;
import bazcraft.schoolwars.Vragen.KlasLokaal;
import bazcraft.schoolwars.Vragen.VragenManager;
import bazcraft.schoolwars.command.CommandManager;
import bazcraft.schoolwars.command.ConsoleCommandManager;
import bazcraft.schoolwars.command.PlayerCommandManager;
import bazcraft.schoolwars.minions.MinionManager;
import bazcraft.schoolwars.minions.Path;
import bazcraft.schoolwars.minions.Wall;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.tools.CounterRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class Schoolwars extends JavaPlugin {

    private final EventListener eventListener;
    private final GameManager gameManager;
    private final CommandManager commandManager;
    private final PlayerCommandManager playerCommandManager;
    private final ConsoleCommandManager consoleCommandManager;
    private final TeamManager teamManager;
    private final VragenManager vragenManager;
    private final KlasLokaal klasLokaal;
    private final MinionManager minionManager;
    private NPCManager npcManager;
	
    public Schoolwars() {
        TeamManager teamManager1;
        eventListener = new EventListener(this);
        gameManager = new GameManager(this, 12, new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5));
        commandManager = new CommandManager(this);
        playerCommandManager = new PlayerCommandManager(this);
        consoleCommandManager = new ConsoleCommandManager(this);
        //MINIONS
        World world = Bukkit.getWorld("world");
        Path redPath = new Path(
            new Wall[]{
                    new Wall(new Location(world, -326.5, 64, 166.5)),
                    new Wall(new Location(world, -333.5, 64, 178.5)),
                    new Wall(new Location(world, -325.5, 64, 189.5)),
                    new Wall(new Location(world, -325.5, 64, 198.5))
            }
        );
        Path bluePath = new Path(
                new Wall[]{
                    new Wall(new Location(world, -324.5, 69, 144.5)),
                    new Wall(new Location(world, -317.5, 69, 147.5)),
                    new Wall(new Location(world, -323.5, 63, 125.5)),
                    new Wall(new Location(world, -344.5, 63, 139.5)),
                    new Wall(new Location(world, -342.5, 89, 123.5))
                }
        );
        minionManager = new MinionManager(
                new Path[]{
                        redPath,
                        bluePath
                }, this
        );

        teamManager1 = new TeamManager(new Team("rood", new Location(Bukkit.getWorld("world"), 37.5, 40.2, -91.5, -90f, 0f), ChatColor.RED,
                redPath), new Team("blauw", new Location(Bukkit.getWorld("world"), 390.5, 40.2, -110.5, 90f, 0f), ChatColor.BLUE,
                bluePath), this);
        teamManager = teamManager1;

        this.vragenManager = new VragenManager(teamManager1, this);
        this.klasLokaal = new KlasLokaal(this.vragenManager, this);

    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(eventListener, this);
        getCommand("spelers").setExecutor(commandManager);
        getCommand("spawnnpc").setExecutor(commandManager);
        getCommand("despawnnpc").setExecutor(commandManager);
        getCommand("pad").setExecutor(playerCommandManager);
        getCommand("antwoord").setExecutor(commandManager);

        GameState.setGamestate(GameState.WAITING);

        npcManager = new NPCManager(this);

        //Doe dit altijd als laatste zodat je gemakkelijk in de console kan zien of er een error bij het opstarten is of niet
        getLogger().info(ChatColor.GREEN + getName() + " is enabled!");
    }

    @Override
    public void onDisable() {
        org.bukkit.scoreboard.Team temp = teamManager.getRED().getScoreboard();
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

    public VragenManager getVragenManager() {
        return vragenManager;
    }

    public MinionManager getMinionManager() {
        return minionManager;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public KlasLokaal getKlasLokaal() {
        return klasLokaal;
    }
}
