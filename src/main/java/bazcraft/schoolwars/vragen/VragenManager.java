package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.firebase.Firebase;
import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;

public class VragenManager{

    private static final VragenManager INSTANCE = new VragenManager();
    private final ArrayList<Vraag> vragenLijst;

    public VragenManager(){
        this.vragenLijst = Firebase.getInstance().getAlleVragen();
    }

    public ArrayList<Vraag> getVragenLijst() {
        return this.vragenLijst;
    }

    public void startVraag(Player player){
        player.openBook(this.getVraagBoek(player));
    }

    public boolean compareAnswer(String antwoord, Vraag vraag){
        return antwoord.equalsIgnoreCase(vraag.getAntwoord());
    }

    public ItemStack getVraagBoek(Player player){
        CustomNPC npc = NPCManager.getInstance().getGeselecteerdeNPC().get(player);
        return npc.getActieveVraag().getBook();
    }

    public static VragenManager getInstance() {
        return INSTANCE;
    }
}