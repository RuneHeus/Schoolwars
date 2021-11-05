package bazcraft.schoolwars.NPC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;

public class NPCManager {

    private ArrayList<CustomNPC> npcList = new ArrayList<>();

    public NPCManager(){

        //Hard Coded NPC
        /*
        CustomNPC leerkrachtNPCBlauw = new CustomNPC("Leerkracht", NPCType.LEERKRACHTNPC, NPCTeam.BLAUW);
        CustomNPC leerkrachtNPCRood = new CustomNPC("Leerkracht", NPCType.LEERKRACHTNPC, NPCTeam.ROOD);

        npcList.add(leerkrachtNPCBlauw);
        npcList.add(leerkrachtNPCRood);
         */
    }

    public void spawnNPC(CustomNPC npc){
        /*
        Rood:
            -leerkracht = 110.5 44.0 -37.5 -91.5 0
            -sportLeerkracht = 86.5 28.0 -130.5 40 0

        Blauw:
            -leerkracht = 317.5 44.0 -164.5 90 0
            -sportLeerkracht = 341.5 28.0 -71.5 -140.0 0

        Speciaale npc = 214.0 28.0 -101.5 0 0
         */

       switch (npc.getType()){
           case LEERKRACHTNPC:
               Location location;
               if(npc.getTeam() == NPCTeam.BLAUW){
                   //blauw team
                   System.out.println(ChatColor.GREEN + "Test npc blauw");
                   location = new Location(Bukkit.getServer().getWorld("World"), 317.5, 44.0, -164.5, 90, 0);
                   npc.getNpc().spawn(location);
               }else{
                   //rood team
                   System.out.println(ChatColor.GREEN + "Test npc rood");
                   location = new Location(Bukkit.getServer().getWorld("World"), 110.5, 44.0, -37.5, (float) -91.5, 0);
                   npc.getNpc().spawn(location);
                   break;
               }
       }
    }

    public void spawnAllNPC(ArrayList<CustomNPC> customNPC){
        for(CustomNPC npc: customNPC){
            this.spawnNPC(npc);
        }
    }

    public void addNPC(CustomNPC npc){
        this.npcList.add(npc);
    }

    public ArrayList<CustomNPC> getNpcList(){
        return this.npcList;
    }
}
