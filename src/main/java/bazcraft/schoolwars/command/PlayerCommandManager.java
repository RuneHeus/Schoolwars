package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerCommandManager implements CommandExecutor {

    private final Schoolwars plugin;
    private HashMap<Player, Integer> selectedPathCache;

    public PlayerCommandManager(Schoolwars plugin) {
        this.plugin = plugin;
        selectedPathCache = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (cmd.getName()) {
                case "pad":
                    if (args.length > 0) {
                        switch (args[0]) {
                            case "select":
                                if (args.length > 1) {
                                    selectedPathCache.put(p, Integer.parseInt(args[1]));
                                    p.sendMessage("Selected path " + args[1]);
                                }
                                return true;
                            case "activatewall":
                                if (args.length > 1) {
                                    plugin.getMinionManager().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].activate();
                                    p.sendMessage("Wall activated");
                                }
                                return true;
                            case "deactivatewall":
                                if (args.length > 1) {
                                    plugin.getMinionManager().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].deactivate(p);
                                    p.sendMessage("Wall deactivated");
                                    plugin.getMinionManager().moveMinionsFromPath(plugin.getMinionManager().getPaths()[selectedPathCache.get(p)]);
                                }
                                return true;
                        }
                    }
                    break;
                case "fstart":
                    plugin.getGameManager().forceStart();
                    return true;
                case "endgame":
                    plugin.getGameManager().endGame(plugin.getTeamManager().getBLUE());return true;
            }
        } else {
            sender.sendMessage("Enkel spelers kunnen deze command uitvoeren!");
        }
        return false;
    }
}
