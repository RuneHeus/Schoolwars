package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;

public class VragenManager{

    private Schoolwars plugin;
    private ArrayList<Vraag> vragenLijst;
    private TeamManager teamManager;

    public VragenManager(TeamManager teamManager, Schoolwars plugin){
        this.plugin = plugin;
        this.vragenLijst = plugin.getFirebase().getAlleVragen();
        this.teamManager = teamManager;
    }

    public ArrayList<Vraag> getVragenLijst() {
        return this.vragenLijst;
    }

    public Schoolwars getPlugin() {
        return plugin;
    }

    public void setPlugin(Schoolwars plugin) {
        this.plugin = plugin;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void startVraag(Player player){
        player.openBook(this.getVraagBoek(player));
    }

    public boolean compareAnswer(String antwoord, Vraag vraag){
        return antwoord.equalsIgnoreCase(vraag.getAntwoord());
    }

    public ItemStack getVraagBoek(Player player){
        CustomNPC npc = plugin.getNpcManager().getGeselecteerdeNPC().get(player);
        return npc.getActieveVraag().getBook();
    }
}