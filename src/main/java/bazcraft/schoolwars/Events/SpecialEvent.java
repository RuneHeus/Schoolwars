package bazcraft.schoolwars.Events;

import bazcraft.schoolwars.Schoolwars;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class SpecialEvent extends BukkitRunnable{

    private Schoolwars plugin;
    private int puntenBlauw;
    private int puntenRood;
    private ArrayList<Player> playersOnTheHill;

    public SpecialEvent(Schoolwars plugin){
       this.plugin = plugin;
       this.playersOnTheHill = new ArrayList<>();
    }

    public  void start(){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()){
                    if(puntenBlauw == 100){
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Game: " + ChatColor.BLUE + "Blauw " + ChatColor.AQUA + "heeft gewonnen!");
                        this.cancel();
                    }else if(puntenRood == 100){
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Rood " + ChatColor.AQUA + "heeft gewonnen!");
                        this.cancel();
                    }else{
                        Location location = player.getLocation();
                        if((location.getBlockX() >= 211) && (location.getBlockX() <= 216) && (location.getBlockZ() >= -104) && (location.getBlockZ() <= -100) && (location.getBlockY() == 28)){
                            playersOnTheHill.add(player);
                            if(isTwoTeamOnHill(playersOnTheHill)){
                                Bukkit.broadcastMessage("Er staan 2 team op de hill");
                            }else{
                                if(plugin.getTeamManager().getTeam(player) == plugin.getTeamManager().getBLUE()){
                                    puntenBlauw++;
                                    Bukkit.broadcastMessage("Blauw heeft " + puntenBlauw + " punten");
                                }else{
                                    puntenRood++;
                                    Bukkit.broadcastMessage("Rood heeft " + puntenRood + " punten");
                                }
                            }
                        }else{
                            playersOnTheHill.remove(player);
                        }
                    }
                }
            }

        }.runTaskTimer(Schoolwars.getPlugin(Schoolwars.class), 0, 5);
    }

    public  void end(){

    }

    @Override
    public void run(){
        Bukkit.broadcastMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Het speciaal evenement gaat beginnen!");
        start();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException{
        super.cancel();
        end();
    }

    private boolean isTwoTeamOnHill(ArrayList<Player> playersOnTheHill){
        int aantalBlauw = 0;
        int aantalRood = 0;
        if(playersOnTheHill.size() <= 1){
            return false;
        }else{
            for(Player player: playersOnTheHill){
                if(plugin.getTeamManager().getTeam(player) == plugin.getTeamManager().getBLUE()){
                    aantalBlauw++;
                }else{
                    aantalRood++;
                }
            }
            return aantalBlauw > 0 && aantalRood > 0;
        }
    }
}
