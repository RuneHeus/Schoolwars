package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class KlasLokaal {

    private static final KlasLokaal INSTANCE = new KlasLokaal();

    private CustomNPC leerkracht;
    private Location locatieBlauwSpeler;
    private Location locatieRoodSpeler;
    private Location locatieBlauwNpc;
    private Location locatieRoodNpc;
    private HashMap<Player, Location> playersInClassRoom;

    private KlasLokaal(){
        this.locatieBlauwSpeler = new Location(Bukkit.getServer().getWorld("world"), 431.5, 31.0, -104.5, 90.0f, 0.0f);
        this.locatieRoodSpeler = new Location(Bukkit.getServer().getWorld("world"), 10.5, 42.0, -98.5, 90.0f, 0.0f);
        this.locatieBlauwNpc = new Location(Bukkit.getServer().getWorld("world"), 421.5, 31.0, -103.5, -80.0f, 0.0f);
        this.locatieRoodNpc = new Location(Bukkit.getServer().getWorld("world"), 0.5, 42.0, -97.5, -80.0f, 0.0f);
        this.playersInClassRoom = new HashMap<>();
    }

    public void teleportToMainGame(Player player, CustomNPC npc){
        player.teleport(getPlayersInClassRoom().get(player));
        removePlayerInClassRoom(player);
        assert npc != null;
        if(npc.getTeam() == TeamManager.getInstance().getBLUE()){
            npc.getNpc().teleport(new Location(Bukkit.getServer().getWorld("world"), 317.5, 44.0, -164.5, 90, 0), PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }else if(npc.getTeam() == TeamManager.getInstance().getRED()){
            npc.getNpc().teleport(new Location(Bukkit.getServer().getWorld("world"), 110.5, 44.0, -37.5, -91.5f, 0), PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }
    }

    public void teleportToClassRoom(Player player){
        Team team = TeamManager.getInstance().getTeam(player);
        CustomNPC npc = NPCManager.getInstance().getGeselecteerdeNPC().get(player);
        if (team == TeamManager.getInstance().getBLUE()){
            player.teleport(locatieBlauwSpeler);
            assert npc != null;
            npc.getNpc().teleport(locatieBlauwNpc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }else if(team == TeamManager.getInstance().getRED()){
            player.teleport(locatieRoodSpeler);
            assert npc != null;
            npc.getNpc().teleport(locatieRoodNpc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    public HashMap<Player, Location> getPlayersInClassRoom() {
        return playersInClassRoom;
    }

    public void removePlayerInClassRoom(Player player){
        this.playersInClassRoom.remove(player);
    }

    public void addPlayersInClassRoom(Player player) {
        this.playersInClassRoom.put(player, player.getLocation());
    }

    public static KlasLokaal getInstance() {
        return INSTANCE;
    }
}
