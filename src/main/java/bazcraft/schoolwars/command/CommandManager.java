package bazcraft.schoolwars.command;

import bazcraft.schoolwars.NPC.CustomNPC;
import bazcraft.schoolwars.NPC.NPCManager;
import bazcraft.schoolwars.NPC.NPCTeam;
import bazcraft.schoolwars.NPC.NPCType;
import bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    private final Schoolwars plugin;

    public CommandManager(Schoolwars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        switch (label) {
            case "spelers":
                String ingame = "";
                String spec = "";
                for (Player n : Bukkit.getOnlinePlayers()) {
                    if (plugin.getGameManager().getIngamePlayers().contains(n)) {
                        ingame += "\n- " + n.getDisplayName();
                    } else {
                        spec += "\n- " +n.getDisplayName();
                    }
                }
                sender.sendMessage("ingamespelers: " + ingame + "\nspectators: " + spec);
                return true;

            case "spawnnpc":
                CustomNPC npc = new CustomNPC("Testing", NPCType.LEERKRACHTNPC, NPCTeam.BLAUW);
                plugin.getNpcManager().addNPC(npc);
                npc.getNpc().spawn(player.getLocation());
                return true;
            case "despawnnpc":
                for(int i = 0; i < plugin.getNpcManager().getNpcList().size(); i++){
                    plugin.getNpcManager().getNpcList().get(i).getNpc().destroy();
                }
                return true;

        }
        return false;
    }
}
