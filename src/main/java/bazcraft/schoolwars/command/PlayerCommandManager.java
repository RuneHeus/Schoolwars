package bazcraft.schoolwars.command;

import bazcraft.schoolwars.GameManager;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.minions.MinionManager;
import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.vragen.KlasLokaal;
import bazcraft.schoolwars.vragen.VraagType;
import bazcraft.schoolwars.vragen.VragenManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class PlayerCommandManager implements CommandExecutor {

    private static final PlayerCommandManager INSTANCE = new PlayerCommandManager();
    private HashMap<Player, Integer> selectedPathCache;

    private PlayerCommandManager() {
        selectedPathCache = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            switch (cmd.getName()) {
                case "pad":
                    if (args.length > 0) {
                        switch (args[0]) {
                            case "select" -> {
                                if (args.length > 1) {
                                    selectedPathCache.put(p, Integer.parseInt(args[1]));
                                    p.sendMessage(Schoolwars.prefix + " Selected path " + args[1]);
                                }
                                return true;
                            }
                            case "activatewall" -> {
                                if (args.length > 1) {
                                    GameManager.getInstance().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].activate();
                                    p.sendMessage(Schoolwars.prefix + " Wall activated");
                                }
                                return true;
                            }
                            case "deactivatewall" -> {
                                if (args.length > 1) {
                                    GameManager.getInstance().getPaths()[selectedPathCache.get(p)].getWalls()[Integer.parseInt(args[1])].deactivate(p);
                                    p.sendMessage(Schoolwars.prefix + " Wall deactivated");
                                    //MinionManager.getInstance().moveMinionsFromPath(GameManager.getInstance().getPaths()[selectedPathCache.get(p)]);
                                }
                                return true;
                            }
                        }
                    }
                    break;
                case "fstart":
                    if (p.isOp()) {
                        GameManager.getInstance().forceStart();
                        return true;
                    }
                case "endgame":
                    if (p.isOp()) {
                        GameManager.getInstance().endGame(TeamManager.getInstance().getBLUE());
                        return true;
                    }
                case "antwoord":
                    Team team = TeamManager.getInstance().getTeam(p);
                    CustomNPC npc = NPCManager.getInstance().getGeselecteerdeNPC().get(p);
                    if(npc != null){
                        if (args.length > 0){
                            String antwoord = "";
                            for(String n: args){
                                antwoord += n + " ";
                            }
                            antwoord = antwoord.substring(0, antwoord.length()-1);
                            boolean juist = VragenManager.getInstance().compareAnswer(antwoord, npc.getActieveVraag());
                            if(juist){
                                p.sendMessage(Schoolwars.prefix + " " + ChatColor.GREEN + "Juist antwoord!");
                                npc.getActieveVraag().getTeamsBeantwoord().put(npc.getTeam(), true);
                                npc.setNieuweVraag();

                                if(npc.getType() == VraagType.NORMAAL){
                                    KlasLokaal.getInstance().teleportToMainGame(p, npc);

                                    if (team.equals(TeamManager.getInstance().getRED())) {
                                        TeamManager.getInstance().getBLUE().removeHealth(10);
                                    } else if (team.equals(TeamManager.getInstance().getBLUE())) {
                                        TeamManager.getInstance().getRED().removeHealth(10);
                                    }

                                } else if (npc.getType() == VraagType.SPECIAAL) {
                                    for (int i = 0; i < team.getMinionPoints(); i++) {

                                        if (team.equals(TeamManager.getInstance().getRED())) {
                                            TeamManager.getInstance().getBLUE().removeHealth(20);
                                        } else if (team.equals(TeamManager.getInstance().getBLUE())) {
                                            TeamManager.getInstance().getRED().removeHealth(20);
                                        }

                                    }
                                }
                                team.setMinionPoints(0);
                                HashMap<Player, CustomNPC> temp = NPCManager.getInstance().getGeselecteerdeNPC();
                                try{
                                    for(Player playerInNpc: temp.keySet()){
                                        if(TeamManager.getInstance().getTeam(playerInNpc).equals(team)){
                                            if(NPCManager.getInstance().getGeselecteerdeNPC().get(playerInNpc).getNpc().getUniqueId().equals(npc.getNpc().getUniqueId())){
                                                NPCManager.getInstance().removeGeselecteerdeNPC(playerInNpc);
                                            }
                                        }
                                    }
                                }catch (ConcurrentModificationException ignored){

                                }

                            }else{
                                p.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Fout antoord!");
                            }
                        }else{
                            p.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Je moet een antwoord geven!");
                        }
                        return true;
                    }else{
                        p.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Je hebt geen vraag geselecteerd!");
                    }
            }
        } else {
            sender.sendMessage(Schoolwars.prefix + " Enkel spelers kunnen deze command uitvoeren!");
        }
        return false;
    }

    public static PlayerCommandManager getInstance() {
        return INSTANCE;
    }
}
