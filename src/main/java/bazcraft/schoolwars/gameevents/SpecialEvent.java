package bazcraft.schoolwars.gameevents;

import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SpecialEvent extends BukkitRunnable{

    private int puntenBlauw;
    private int puntenRood;
    private ArrayList<Player> playersOnTheHill;

    public SpecialEvent(){
       this.playersOnTheHill = new ArrayList<>();
    }

    public  void start() {
        new BukkitRunnable() {
            int counter = 0;
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    Location location = player.getLocation();
                    if ((location.getBlockX() >= 211) && (location.getBlockX() <= 216) && (location.getBlockZ() >= -104) && (location.getBlockZ() <= -100)){
                        if(!playersOnTheHill.contains(player)){
                            playersOnTheHill.add(player);
                        }
                    }else{
                        playersOnTheHill.remove(player);
                    }
                }
                if(playersOnTheHill.size() != 0){
                    if (isTwoTeamOnHill()){
                        Bukkit.broadcastMessage("Er staan 2 team op de hill");
                    }else{
                        if (TeamManager.getInstance().getTeam(playersOnTheHill.get(0)) == TeamManager.getInstance().getBLUE()){
                            puntenBlauw++;
                            Bukkit.broadcastMessage("Blauw heeft " + puntenBlauw + " punten");
                        }else{
                            puntenRood++;
                            Bukkit.broadcastMessage("Rood heeft " + puntenRood + " punten");
                        }
                    }
                }
                if (puntenBlauw == 100) {
                    Bukkit.broadcastMessage(Schoolwars.prefix + " " + ChatColor.BLUE + "Blauw " + ChatColor.AQUA + "heeft gewonnen!");
                    TeamManager.getInstance().getBLUE().addMinionPoints(5);
                    this.cancel();
                } else if (puntenRood == 100) {
                    Bukkit.broadcastMessage(Schoolwars.prefix + " " + ChatColor.RED + "Rood " + ChatColor.AQUA + "heeft gewonnen!");
                    TeamManager.getInstance().getRED().addMinionPoints(5);
                    this.cancel();
                }
                counter++;
                if(counter == 1200){
                    Bukkit.broadcastMessage(Schoolwars.prefix + " " + ChatColor.RED + "Het evenement is gedaan! Niemand Heeft gewonnen");
                    this.cancel();
                }
            }
        }.runTaskTimer(Schoolwars.getPlugin(Schoolwars.class), 0,5);
    }

    public  void end(){

    }

    @Override
    public void run(){
        Bukkit.broadcastMessage(Schoolwars.prefix + " " + ChatColor.AQUA + "Het speciaal evenement gaat beginnen!");
        start();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException{
        super.cancel();
        end();
    }

    private boolean isTwoTeamOnHill(){
        int aantalBlauw = 0;
        int aantalRood = 0;
        for(Player player: playersOnTheHill){
            if(TeamManager.getInstance().getTeam(player) == TeamManager.getInstance().getBLUE()){
                aantalBlauw++;
            }else{
                aantalRood++;
            }
        }
        return aantalBlauw > 0 && aantalRood > 0;

    }
}
