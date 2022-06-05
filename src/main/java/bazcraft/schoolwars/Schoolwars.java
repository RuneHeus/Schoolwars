package bazcraft.schoolwars;

import bazcraft.schoolwars.command.CommandManager;
import bazcraft.schoolwars.command.PlayerCommandManager;
import bazcraft.schoolwars.minions.MinionManager;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Schoolwars extends JavaPlugin {

    public static final String prefix = "§0[§2Schoolwars§0]§r";
	
    public Schoolwars() {

    }

    @Override
    public void onEnable() {

        GameState.setGamestate(GameState.WAITING);

        getServer().getPluginManager().registerEvents(EventListener.getInstance(), this);
        getServer().getPluginManager().registerEvents(MinionManager.getInstance(), this);

        //COMMANDS
        getCommand("spelers").setExecutor(CommandManager.getInstance());
        getCommand("spawnnpc").setExecutor(CommandManager.getInstance());
        getCommand("despawnnpc").setExecutor(CommandManager.getInstance());
        getCommand("fstart").setExecutor(PlayerCommandManager.getInstance());
        getCommand("endgame").setExecutor(PlayerCommandManager.getInstance());
        getCommand("antwoord").setExecutor(PlayerCommandManager.getInstance());

        //Doe dit altijd als laatste zodat je gemakkelijk in de console kan zien of er een error bij het opstarten is of niet
        getLogger().info(ChatColor.GREEN + getName() + " is enabled!");
    }

    @Override
    public void onDisable() {
        TeamManager.getInstance().clearTeams();
    }
}
