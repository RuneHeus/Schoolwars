package bazcraft.schoolwars;

import bazcraft.schoolwars.Kit.KitManager;
import bazcraft.schoolwars.command.CommandManager;
import bazcraft.schoolwars.command.ConsoleCommandManager;
import bazcraft.schoolwars.command.PlayerCommandManager;
import bazcraft.schoolwars.minions.Wall;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.vragen.KlasLokaal;
import bazcraft.schoolwars.vragen.VragenManager;
import bazcraft.schoolwars.minions.MinionManager;
import bazcraft.schoolwars.minions.Path;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

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
    private final KitManager kitManager;

    public static final String prefix = "§0[§2Schoolwars§0]§r";
	
    public Schoolwars() {
        TeamManager teamManager1;
        eventListener = new EventListener(this);
        gameManager = new GameManager(this, 8, new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5));
        commandManager = new CommandManager(this);
        playerCommandManager = new PlayerCommandManager(this);
        consoleCommandManager = new ConsoleCommandManager(this);
        kitManager = new KitManager(this);
        //MINIONS
        World world = Bukkit.getWorld("world");
        Path redPath = new Path(
            new Wall[]{
                    new Wall(new Location(world, 32.5, 34, -92.5)),
                    new Wall(new Location(world, 58.5, 31, -91.5)),
                    new Wall(new Location(world, 84.5, 28, -89.5)),
                    new Wall(new Location(world, 100.5, 28, -96.5)),
                    new Wall(new Location(world, 111.5, 28, -108.5)),
                    new Wall(new Location(world, 130.5, 28, -101.5)),
                    new Wall(new Location(world, 149.5, 28, -102.5)),
                    new Wall(new Location(world, 164.5, 28, -112.5)),
                    new Wall(new Location(world, 195.5, 28, -112.5)),
                    new Wall(new Location(world, 232.5, 28, -89.5)),
                    new Wall(new Location(world, 262.5, 28, -90.5)),
                    new Wall(new Location(world, 278.5, 28, -99.5)),
                    new Wall(new Location(world, 297.5, 28, -100.5)),
                    new Wall(new Location(world, 316.5, 28, -93.5)),
                    new Wall(new Location(world, 343.5, 28, -112.5)),
                    new Wall(new Location(world, 370.5, 31, -110.5)),
                    new Wall(new Location(world, 395.5, 34, -109.5)),
            }
        );
        Path bluePath = new Path(
                new Wall[]{
                        new Wall(new Location(world, 395.5, 34, -109.5)),
                        new Wall(new Location(world, 370.5, 31, -110.5)),
                        new Wall(new Location(world, 343.5, 28, -112.5)),
                        new Wall(new Location(world, 316.5, 28, -93.5)),
                        new Wall(new Location(world, 297.5, 28, -100.5)),
                        new Wall(new Location(world, 278.5, 28, -99.5)),
                        new Wall(new Location(world, 262, 28, -90)),
                        new Wall(new Location(world, 232.5, 28, -89.5)),
                        new Wall(new Location(world, 195.5, 28, -112.5)),
                        new Wall(new Location(world, 164.5, 28, -112.5)),
                        new Wall(new Location(world, 149.5, 28, -102.5)),
                        new Wall(new Location(world, 130.5, 28, -101.5)),
                        new Wall(new Location(world, 111.5, 28, -108.5)),
                        new Wall(new Location(world, 100.5, 28, -96.5)),
                        new Wall(new Location(world, 84.5, 28, -89.5)),
                        new Wall(new Location(world, 58.5, 31, -91.5)),
                        new Wall(new Location(world, 32.5, 34, -92.5))
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
        getServer().getPluginManager().registerEvents(minionManager, this);
        getCommand("spelers").setExecutor(commandManager);
        getCommand("spawnnpc").setExecutor(commandManager);
        getCommand("despawnnpc").setExecutor(commandManager);
        getCommand("pad").setExecutor(playerCommandManager);
        getCommand("fstart").setExecutor(playerCommandManager);
        getCommand("endgame").setExecutor(playerCommandManager);
        getCommand("antwoord").setExecutor(playerCommandManager);

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

    public KitManager getKitManager(){
        return this.kitManager;
    }
}
