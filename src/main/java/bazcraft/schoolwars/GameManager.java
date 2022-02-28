package bazcraft.schoolwars;

import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.tools.CounterRunnable;
import bazcraft.schoolwars.gameevents.SpecialEvent;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

        for(Player player: Bukkit.getOnlinePlayers()){
            player.getInventory().clear();
        }

        plugin.getKitManager().giveAllPlayerKit();

        //Spawn all bazcraft.schoolwars.npc
        plugin.getNpcManager().spawnAllNPC(plugin.getNpcManager().getNpcList());
        new BukkitRunnable() {
            @Override
            public void run() {
                new SpecialEvent(plugin).run();
            }
        }.runTaskLater(plugin, 6000);
    }

    public boolean addSpeler(Player speler) {
        if (ingamePlayers.size() < maxAantalSpelers) {

            ingamePlayers.add(speler);

            if (ingamePlayers.size() == PLAYERSNEEDEDTOSTART) {
                countdownRunnable = new CounterRunnable(plugin, 60) {
                    @Override
                    public void repeat() {
                        if ((counter > 10 && counter %20==0) || (counter == 10) || counter < 6) {
                            Bukkit.broadcastMessage(Schoolwars.prefix + " Het spel zal binnen " + counter + " seconden starten");
                        }
                        if (counter < 6) {
                            for (Player n : Bukkit.getOnlinePlayers()) {
                                n.playSound(n.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                            }
                        }
                    }

                    @Override
                    public void finish() {
                        Bukkit.broadcastMessage(Schoolwars.prefix + " Het spel is begonnen");
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

    public void endGame(Team loser) {
        GameState.setGamestate(GameState.ENDGAME);
        CitizensAPI.getNPCRegistry().deregisterAll();
        Team winner = plugin.getTeamManager().getRED();
        if (winner == loser) {
            winner = plugin.getTeamManager().getBLUE();
        }
        Bukkit.broadcastMessage(Schoolwars.prefix + " Team " + winner.getPublicHealthBar().getTitle() + " is gewonnen!");
        Bukkit.getScheduler().cancelTasks(plugin);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player n : Bukkit.getOnlinePlayers()) {
                    plugin.getKitManager().removeAllItemsFromPlayer(n);
                    n.kickPlayer("GameOver");
                }
                removeAllDroppedArrows();
                plugin.getServer().reload();
            }
        }.runTaskLater(plugin, 200);
    }

    public void removeAllDroppedArrows(){
        for (Arrow arrow : Bukkit.getWorld("world").getEntitiesByClass(Arrow.class)) {
            arrow.remove();
        }
    }
}
