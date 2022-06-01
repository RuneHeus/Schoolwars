package bazcraft.schoolwars;

import bazcraft.schoolwars.Kit.KitManager;
import bazcraft.schoolwars.minions.Path;
import bazcraft.schoolwars.minions.Wall;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.tools.CounterRunnable;
import bazcraft.schoolwars.gameevents.SpecialEvent;
import bazcraft.schoolwars.vragen.KlasLokaal;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private static final GameManager INSTANCE = new GameManager();

    private ArrayList<Player> ingamePlayers;
    private final int maxAantalSpelers;
    private final Location lobby;
    private final int PLAYERSNEEDEDTOSTART;
    private CounterRunnable countdownRunnable;

    private Path[] paths;

    private GameManager() {
        ingamePlayers = new ArrayList<>();
        this.maxAantalSpelers = 8;
        this.lobby = new Location(Bukkit.getWorld("world"), 0.5, 200, 0.5);
        PLAYERSNEEDEDTOSTART = 1;

        World world = Bukkit.getWorld("world");
        Path redPath = new Path(
                new Wall[]{
                        new Wall(new Location(world, 32.5, 34, -92.5)),
                        new Wall(new Location(world, 58.5, 31, -91.5)),
                        new Wall(new Location(world, 84.5, 28, -89.5)),
                        new Wall(new Location(world, 100.5, 28, -96.5)),
                        new Wall(new Location(world, 111.5, 28, -108.5)),
                        new Wall(new Location(world, 130.5, 28, -101.5)),
                        new Wall(new Location(world, 149.5, 28, -102.5)),
                        new Wall(new Location(world, 164.5, 28, -112.5)),
                        new Wall(new Location(world, 195.5, 28, -112.5)),
                        new Wall(new Location(world, 232.5, 28, -89.5)),
                        new Wall(new Location(world, 262.5, 28, -90.5)),
                        new Wall(new Location(world, 278.5, 28, -99.5)),
                        new Wall(new Location(world, 297.5, 28, -100.5)),
                        new Wall(new Location(world, 316.5, 28, -93.5)),
                        new Wall(new Location(world, 343.5, 28, -112.5)),
                        new Wall(new Location(world, 370.5, 31, -110.5)),
                        new Wall(new Location(world, 395.5, 34, -109.5)),
                }
        );
        Path bluePath = new Path(
                new Wall[]{
                        new Wall(new Location(world, 395.5, 34, -109.5)),
                        new Wall(new Location(world, 370.5, 31, -110.5)),
                        new Wall(new Location(world, 343.5, 28, -112.5)),
                        new Wall(new Location(world, 316.5, 28, -93.5)),
                        new Wall(new Location(world, 297.5, 28, -100.5)),
                        new Wall(new Location(world, 278.5, 28, -99.5)),
                        new Wall(new Location(world, 262, 28, -90)),
                        new Wall(new Location(world, 232.5, 28, -89.5)),
                        new Wall(new Location(world, 195.5, 28, -112.5)),
                        new Wall(new Location(world, 164.5, 28, -112.5)),
                        new Wall(new Location(world, 149.5, 28, -102.5)),
                        new Wall(new Location(world, 130.5, 28, -101.5)),
                        new Wall(new Location(world, 111.5, 28, -108.5)),
                        new Wall(new Location(world, 100.5, 28, -96.5)),
                        new Wall(new Location(world, 84.5, 28, -89.5)),
                        new Wall(new Location(world, 58.5, 31, -91.5)),
                        new Wall(new Location(world, 32.5, 34, -92.5))
                }
        );

        paths = new Path[] {redPath, bluePath};

    }

    public void startGame() {
        GameState.setGamestate(GameState.INGAME);
        TeamManager.getInstance().getRED().teleportSpelers();
        TeamManager.getInstance().getBLUE().teleportSpelers();

        for(Player player: Bukkit.getOnlinePlayers()){
            player.getInventory().clear();
        }

        KitManager.getInstance().giveAllPlayerKit();

        //Spawn all bazcraft.schoolwars.npc
        NPCManager.getInstance().spawnAllNPC();
        new BukkitRunnable() {
            @Override
            public void run() {
                new SpecialEvent().run();
            }
        }.runTaskLater(JavaPlugin.getPlugin(Schoolwars.class), 6000);
    }

    public boolean addSpeler(Player speler) {
        if (ingamePlayers.size() < maxAantalSpelers) {

            ingamePlayers.add(speler);

            if (ingamePlayers.size() == PLAYERSNEEDEDTOSTART) {
                countdownRunnable = new CounterRunnable(JavaPlugin.getPlugin(Schoolwars.class), 60) {
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
                countdownRunnable.runTaskTimer(JavaPlugin.getPlugin(Schoolwars.class), 0, 20);
            }

            sorteerSpelerInTeam(speler);

            return true;

        } else {
            return false;
        }
    }

    private void sorteerSpelerInTeam(Player speler) {
        Random rand = new Random();

        Team blauw = TeamManager.getInstance().getBLUE();
        Team rood = TeamManager.getInstance().getRED();

        //Als er meer spelers in rood zijn dan blauw add speler in blauw
        if (rood.getScoreboard().getEntries().size() > blauw.getScoreboard().getEntries().size()) {
            TeamManager.getInstance().addPlayer(blauw, speler);
        } else if (rood.getScoreboard().getEntries().size() < blauw.getScoreboard().getEntries().size()){
            //Als er meer spelers in blauw zijn dan rood add speler in rood
            TeamManager.getInstance().addPlayer(rood, speler);
        } else {
            //Als er evenveel spelers zijn add speler in random team
            int random = rand.nextInt(2);
            if (random > 0) {
                TeamManager.getInstance().addPlayer(blauw, speler);
            } else {
                TeamManager.getInstance().addPlayer(rood, speler);
            }
        }
    }

    public void removeSpeler(Player speler) {
        ingamePlayers.remove(speler);
        TeamManager.getInstance().removePlayer(TeamManager.getInstance().getTeam(speler), speler);
        if (countdownRunnable != null && !countdownRunnable.isCancelled()) {
            if (ingamePlayers.size() < PLAYERSNEEDEDTOSTART) {
                countdownRunnable.cancel();
            }
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
        Team winner = TeamManager.getInstance().getRED();
        if (winner == loser) {
            winner = TeamManager.getInstance().getBLUE();
        }
        Bukkit.broadcastMessage(Schoolwars.prefix + " Team " + winner.getPublicHealthBar().getTitle() + " is gewonnen!");
        Bukkit.getScheduler().cancelTasks(JavaPlugin.getPlugin(Schoolwars.class));
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player n : Bukkit.getOnlinePlayers()) {
                    KitManager.getInstance().removeAllItemsFromPlayer(n);
                    n.kickPlayer("GameOver");
                }
                removeAllDroppedArrows();
                Bukkit.getServer().reload();
            }
        }.runTaskLater(JavaPlugin.getPlugin(Schoolwars.class), 200);
    }

    public void removeAllDroppedArrows(){
        for (Arrow arrow : Bukkit.getWorld("world").getEntitiesByClass(Arrow.class)) {
            arrow.remove();
        }
    }

    public Path[] getPaths() {
        return paths.clone();
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public int getMaxAantalSpelers() {
        return maxAantalSpelers;
    }
}
