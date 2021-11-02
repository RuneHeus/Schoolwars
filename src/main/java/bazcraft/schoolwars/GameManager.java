package bazcraft.schoolwars;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
            return true;
        } else {
            return false;
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
