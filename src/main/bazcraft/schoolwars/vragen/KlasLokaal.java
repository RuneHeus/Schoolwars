package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class KlasLokaal {

    private Schoolwars plugin;
    private VragenManager vragenManager;
    private CustomNPC leerkracht;
    private Location locatieBlauwSpeler;
    private Location locatieRoodSpeler;
    private Location locatieBlauwNpc;
    private Location locatieRoodNpc;
    private HashMap<Player, Location> playersInClassRoom;

    public KlasLokaal(VragenManager vragenManager, Schoolwars plugin){
        this.plugin = plugin;
        this.vragenManager = vragenManager;
        this.locatieBlauwSpeler = new Location(Bukkit.getServer().getWorld("world"), 10.5, 42.0, -98.5, 90.0f, 0.0f);
        this.locatieRoodSpeler = new Location(Bukkit.getServer().getWorld("world"), 431.5, 31.0, -104.5, 90.0f, 0.0f);
        this.locatieBlauwNpc = new Location(Bukkit.getServer().getWorld("world"), 0.5, 42.0, -97.5, -80.0f, 0.0f);
        this.locatieRoodNpc = new Location(Bukkit.getServer().getWorld("world"), 421.5, 31.0, -103.5, -80.0f, 0.0f);
        this.playersInClassRoom = new HashMap<>();
    }

    public void teleportToMainGame(Player player){
        Team team = this.plugin.getTeamManager().getTeam(player);
        CustomNPC npc = null;
        for(int i = 0; i < this.plugin.getNpcManager().getGeselecteerdeNPC().size(); i++){
            if(team == this.plugin.getNpcManager().getGeselecteerdeNPC().get(i).getTeam()){
                npc = this.plugin.getNpcManager().getGeselecteerdeNPC().get(i);
            }
        }
        player.teleport(this.getPlayersInClassRoom().get(player));
        assert npc != null;
        if(npc.getTeam() == this.plugin.getTeamManager().getBLUE()){
            npc.getNpc().teleport(new Location(Bukkit.getServer().getWorld("world"), 317.5, 44.0, -164.5, 90, 0), PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }else if(npc.getTeam() == this.plugin.getTeamManager().getRED()){
            npc.getNpc().teleport(new Location(Bukkit.getServer().getWorld("world"), 110.5, 44.0, -37.5, -91.5f, 0), PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }
    }

    public void teleportToClassRoom(Player player) {
        Team team = plugin.getTeamManager().getTeam(player);
        CustomNPC npc = null;
        for(int i = 0; i < plugin.getNpcManager().getGeselecteerdeNPC().size(); i++){
            if(team == this.plugin.getNpcManager().getGeselecteerdeNPC().get(i).getTeam()){
                npc = this.plugin.getNpcManager().getGeselecteerdeNPC().get(i);
            }
        }
        if (team == plugin.getTeamManager().getBLUE()){
            player.teleport(this.locatieBlauwSpeler);
            assert npc != null;
            npc.getNpc().teleport(this.locatieBlauwNpc, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }else if(team == plugin.getTeamManager().getRED()){
            player.teleport(this.locatieRoodSpeler);
            assert npc != null;
            npc.getNpc().teleport(this.locatieRoodNpc, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }
        this.plugin.getNpcManager().removeGeselecteerdeNPC(npc);
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

    public VragenManager getVragenManager() {
        return vragenManager;
    }

    public void setVragenManager(VragenManager vragenManager) {
        this.vragenManager = vragenManager;
    }

    public CustomNPC getLeerkracht() {
        return leerkracht;
    }

    public void setLeerkracht(CustomNPC leerkracht) {
        this.leerkracht = leerkracht;
    }

    public Location getLocatieBlauwSpeler() {
        return locatieBlauwSpeler;
    }

    public void setLocatieBlauwSpeler(Location locatieBlauwSpeler) {
        this.locatieBlauwSpeler = locatieBlauwSpeler;
    }

    public Location getLocatieRoodSpeler() {
        return locatieRoodSpeler;
    }

    public void setLocatieRoodSpeler(Location locatieRoodSpeler) {
        this.locatieRoodSpeler = locatieRoodSpeler;
    }

    public Location getLocatieBlauwNpc() {
        return locatieBlauwNpc;
    }

    public void setLocatieBlauwNpc(Location locatieBlauwNpc) {
        this.locatieBlauwNpc = locatieBlauwNpc;
    }

    public Location getLocatieRoodNpc() {
        return locatieRoodNpc;
    }

    public void setLocatieRoodNpc(Location locatieRoodNpc) {
        this.locatieRoodNpc = locatieRoodNpc;
    }
}
