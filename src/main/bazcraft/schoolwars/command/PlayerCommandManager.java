package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.vragen.VraagType;
import org.bukkit.ChatColor;
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
                                    p.sendMessage(Schoolwars.prefix + " Selected path " + args[1]);
                                }
                                return true;
                            case "activatewall":
                                if (args.length > 1) {
                                    plugin.getMinionManager().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].activate();
                                    p.sendMessage(Schoolwars.prefix + " Wall activated");
                                }
                                return true;
                            case "deactivatewall":
                                if (args.length > 1) {
                                    plugin.getMinionManager().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].deactivate(p);
                                    p.sendMessage(Schoolwars.prefix + " Wall deactivated");
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
                case "antwoord":
                    CustomNPC npc = plugin.getNpcManager().getGeselecteerdeNPC().get(p);

                    if (args.length > 0){
                        String antwoord = "";
                        for(String n: args){
                            antwoord += n + " ";
                        }
                        antwoord = antwoord.substring(0, antwoord.length()-2);
                        boolean juist = plugin.getVragenManager().compareAnswer(antwoord, npc.getActieveVraag());
                        if(juist){
                            p.sendMessage(Schoolwars.prefix + " " + ChatColor.GREEN + "Juist antwoord!");
                            if(npc.getType() == VraagType.NORMAAL){
                                plugin.getKlasLokaal().teleportToMainGame(p, npc);
                            }
                        }else{
                            p.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Fout antoord!");
                        }
                    }
                    return true;
            }
        } else {
            sender.sendMessage(Schoolwars.prefix + " Enkel spelers kunnen deze command uitvoeren!");
        }
        return false;
    }
}
