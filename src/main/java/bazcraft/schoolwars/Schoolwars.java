package bazcraft.schoolwars;

import bazcraft.schoolwars.command.CommandManager;
import bazcraft.schoolwars.command.ConsoleCommandManager;
import bazcraft.schoolwars.command.PlayerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Schoolwars extends JavaPlugin {

    private final EventListener eventListener;
    private final GameManager gameManager;
    private final CommandManager commandManager;
    private final PlayerCommandManager playerCommandManager;
    private final ConsoleCommandManager consoleCommandManager;

    public Schoolwars() {
        eventListener = new EventListener(this);
        gameManager = new GameManager(this, 12, new Location(Bukkit.getWorld("world"), 0, 200, 0));
        commandManager = new CommandManager(this);
        playerCommandManager = new PlayerCommandManager(this);
        consoleCommandManager = new ConsoleCommandManager(this);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(eventListener, this);
        getCommand("spelers").setExecutor(commandManager);

        GameState.setGamestate(GameState.WAITING);

        //Doe dit altijd als laatste zodat je gemakkelijk in de console kan zien of er een error bij het opstarten is of niet
        getLogger().info(getName() + " is enabled!");
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
