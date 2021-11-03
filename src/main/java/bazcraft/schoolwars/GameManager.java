package bazcraft.schoolwars;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private Schoolwars plugin;
    private ArrayList<Player> ingamePlayers;
    private int maxAantalSpelers;
    private final Location lobby;

    public GameManager(Schoolwars plugin, int maxAantalSpelers, Location lobby) {
        this.plugin = plugin;
        ingamePlayers = new ArrayList<>();
        this.maxAantalSpelers = maxAantalSpelers;
        this.lobby = lobby;
    }

    public boolean addSpeler(Player speler) {
        if (ingamePlayers.size() < maxAantalSpelers) {

            ingamePlayers.add(speler);

            if (ingamePlayers.size() == maxAantalSpelers) {
                GameState.setGamestate(GameState.STARTING);
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
            blauw.addSpeler(speler);
        } else if (rood.getScoreboard().getEntries().size() < blauw.getScoreboard().getEntries().size()){
            //Als er meer spelers in blauw zijn dan rood add speler in rood
            rood.addSpeler(speler);
        } else {
            //Als er evenveel spelers zijn add speler in random team
            int random = rand.nextInt(2);
            if (random > 0) {
                blauw.addSpeler(speler);
            } else {
                rood.addSpeler(speler);
            }
        }
    }

    public void removeSpeler(Player speler) {
        ingamePlayers.remove(speler);
    }

    public Location getLobby() {
        return lobby;
    }

    public ArrayList<Player> getIngamePlayers() {
        return ingamePlayers;
    }
}
