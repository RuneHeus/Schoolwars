package bazcraft.schoolwars;

import bazcraft.schoolwars.Events.SpecialEvent;
import bazcraft.schoolwars.NPC.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.tools.CounterRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private Schoolwars plugin;
    private ArrayList<Player> ingamePlayers;
    private int maxAantalSpelers;
    private final Location lobby;
    private final int PLAYERSNEEDEDTOSTART;
    private CounterRunnable countdownRunnable;

    public GameManager(Schoolwars plugin, int maxAantalSpelers, Location lobby) {
        this.plugin = plugin;
        ingamePlayers = new ArrayList<>();
        this.maxAantalSpelers = maxAantalSpelers;
        this.lobby = lobby;
        PLAYERSNEEDEDTOSTART = 1;
    }

    public void startGame() {
        GameState.setGamestate(GameState.INGAME);
        plugin.getTeamManager().getRED().teleportSpelers();
        plugin.getTeamManager().getBLUE().teleportSpelers();

        //Spawn all npc
        plugin.getNpcManager().spawnAllNPC(plugin.getNpcManager().getNpcList());
        new BukkitRunnable() {
            @Override
            public void run() {
                new SpecialEvent().run();
            }
        }.runTaskLater(plugin, 100);
    }

    public boolean addSpeler(Player speler) {
        if (ingamePlayers.size() < maxAantalSpelers) {

            ingamePlayers.add(speler);

            if (ingamePlayers.size() == PLAYERSNEEDEDTOSTART) {
                countdownRunnable = new CounterRunnable(plugin, 60) {
                    @Override
                    public void repeat() {
                        if ((counter > 10 && counter %20==0) || (counter == 10) || counter < 6) {
                            Bukkit.broadcastMessage("Het spel zal binnen " + counter + " seconden starten");
                        }
                    }

                    @Override
                    public void finish() {
                        Bukkit.broadcastMessage("Het spel is begonnen");
                        startGame();
                    }
                };
                countdownRunnable.runTaskTimer(plugin, 0, 20);
            }

            sorteerSpelerInTeam(speler);

            return true;

        } else {
            return false;
        }
    }

    private void sorteerSpelerInTeam(Player speler) {
        Random rand = new Random();

        Team blauw = plugin.getTeamManager().getBLUE();
        Team rood = plugin.getTeamManager().getRED();

        //Als er meer spelers in rood zijn dan blauw add speler in blauw
        if (rood.getScoreboard().getEntries().size() > blauw.getScoreboard().getEntries().size()) {
            plugin.getTeamManager().addPlayer(blauw, speler);
        } else if (rood.getScoreboard().getEntries().size() < blauw.getScoreboard().getEntries().size()){
            //Als er meer spelers in blauw zijn dan rood add speler in rood
            plugin.getTeamManager().addPlayer(rood, speler);
        } else {
            //Als er evenveel spelers zijn add speler in random team
            int random = rand.nextInt(2);
            if (random > 0) {
                plugin.getTeamManager().addPlayer(blauw, speler);
            } else {
                plugin.getTeamManager().addPlayer(rood, speler);
            }
        }
    }

    public void removeSpeler(Player speler) {
        ingamePlayers.remove(speler);
        plugin.getTeamManager().removePlayer(plugin.getTeamManager().getTeam(speler), speler);
        if (ingamePlayers.size() < PLAYERSNEEDEDTOSTART) {
            countdownRunnable.cancel();
        }
    }

    public Location getLobby() {
        return lobby;
    }

    public ArrayList<Player> getIngamePlayers() {
        return ingamePlayers;
    }

    public void forceStart() {
        countdownRunnable.cancel();
        startGame();
    }
}
