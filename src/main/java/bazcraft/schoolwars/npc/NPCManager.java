package bazcraft.schoolwars.npc;

import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.vragen.VraagType;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class NPCManager {

    private static final NPCManager INSTANCE = new NPCManager();

    private ArrayList<CustomNPC> npcList;
    private final HashMap<Player, CustomNPC> geselecteerdeNPC;

    private NPCManager(){
        this.npcList = new ArrayList<>();
        this.geselecteerdeNPC = new HashMap<>();
        //Hard Coded NPC
        npcList.add(new CustomNPC(ChatColor.BLUE + "Leerkracht", VraagType.NORMAAL, TeamManager.getInstance().getBLUE()));
        npcList.add(new CustomNPC(ChatColor.RED + "Leerkracht", VraagType.NORMAAL, TeamManager.getInstance().getRED()));
        npcList.add(new CustomNPC("§cShop", VraagType.SPECIAAL, TeamManager.getInstance().getRED()));
        npcList.add(new CustomNPC("§9Shop", VraagType.SPECIAAL, TeamManager.getInstance().getBLUE()));
    }

    public CustomNPC getCustomNPC(NPC npc) {
        for (CustomNPC n : npcList) {
            if (n.getNpc().equals(npc)) {
                return n;
            }
        }
        return null;
    }

    public void spawnNPC(CustomNPC npc){
        /*
        Rood:
            -leerkracht = 110.5 44.0 -37.5 -91.5 0
            -sportLeerkracht = 86.5 28.0 -130.5 40 0

        Blauw:
            -leerkracht = 317.5 44.0 -164.5 90 0
            -sportLeerkracht = 341.5 28.0 -71.5 -140.0 0

        Speciaale bazcraft.schoolwars.npc = 214.0 28.0 -101.5 0 0
         */
       Location loc = null;
       switch (npc.getType()){
           case NORMAAL:
               if(npc.getTeam().getPublicHealthBar().getTitle().equals(ChatColor.BLUE + "BLAUW")){
                   //blauw team
                   loc = new Location(Bukkit.getServer().getWorld("world"), 317.5, 44.0, -164.5, 90, 0);
               }else{
                   //rood team
                   loc = new Location(Bukkit.getServer().getWorld("world"), 110.5, 44.0, -37.5, -91.5f, 0);
               }
               break;
           case SPECIAAL:
               if(npc.getTeam().getPublicHealthBar().getTitle().equals(ChatColor.BLUE + "BLAUW")){
                   loc = new Location(Bukkit.getServer().getWorld("world"), 398.5, 34, -109.5, 90, 0);
               } else {
                   loc = new Location(Bukkit.getServer().getWorld("world"), 29.5, 34, -92.5, -90, 0);
               }
               break;
       }
       if (loc != null) {
           npc.getNpc().spawn(loc);
       }
    }

    public void spawnAllNPC(){
        for(CustomNPC npc: npcList){
            this.spawnNPC(npc);
        }
    }

    public void addNPC(CustomNPC npc){
        this.npcList.add(npc);
    }

    public void removeAllNPC(){
        for(CustomNPC npc: this.npcList){
            npc.getNpc().despawn();
        }
    }

    public void setNpcList(ArrayList<CustomNPC> npcList) {
        this.npcList = npcList;
    }

    public HashMap<Player, CustomNPC> getGeselecteerdeNPC() {
        return geselecteerdeNPC;
    }

    public void addGeselecteerdeNPC(Player player, CustomNPC npc){
        this.geselecteerdeNPC.put(player, npc);
    }

    public void removeGeselecteerdeNPC(Player player){
        geselecteerdeNPC.remove(player);
    }

    public ArrayList<CustomNPC> getNpcList(){
        return this.npcList;
    }

    public CustomNPC getLeerkrachtNpc(Team team){
        if(team == TeamManager.getInstance().getBLUE()){
            return npcList.get(0);
        }else{
            return npcList.get(1);
        }
    }

    public static NPCManager getInstance() {
        return INSTANCE;
    }
}
