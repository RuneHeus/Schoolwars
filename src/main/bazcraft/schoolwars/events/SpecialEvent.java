package main.bazcraft.schoolwars.events;

import main.bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                        if (plugin.getTeamManager().getTeam(playersOnTheHill.get(0)) == plugin.getTeamManager().getBLUE()){
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
                    plugin.getTeamManager().getBLUE().setMinionPoints(plugin.getTeamManager().getBLUE().getMinionPoints()+1);
                    this.cancel();
                } else if (puntenRood == 100) {
                    Bukkit.broadcastMessage(Schoolwars.prefix + " " + ChatColor.RED + "Rood " + ChatColor.AQUA + "heeft gewonnen!");
                    plugin.getTeamManager().getRED().setMinionPoints(plugin.getTeamManager().getRED().getMinionPoints()+1);
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
            if(plugin.getTeamManager().getTeam(player) == plugin.getTeamManager().getBLUE()){
                aantalBlauw++;
            }else{
                aantalRood++;
            }
        }
        return aantalBlauw > 0 && aantalRood > 0;

    }
}
