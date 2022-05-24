package bazcraft.schoolwars.Kit;

import bazcraft.schoolwars.GameManager;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitManager {

    private static final KitManager INSTANCE = new KitManager();

    private HashMap<Player, KitTypes> playerKitList;
    private PlayerKit AllkitList;

    private KitManager(){
        this.playerKitList = new HashMap<>();
        this.AllkitList = new PlayerKit();
    }

    public void addPlayerWithKit(Player player, KitTypes type){
        //Hashmap override value als key al bestaat
        this.playerKitList.put(player, type);
    }

    public boolean hasKit(Player player) {
        return playerKitList.containsKey(player);
    }

    public HashMap<Player, KitTypes> getPlayerKitList(){
        return this.playerKitList;
    }

    public void giveAllPlayerKit(){
         for(Player n : GameManager.getInstance().getIngamePlayers()){
             KitTypes types = playerKitList.get(n);
             this.AllkitList.givePlayerLeatherArmour(n, TeamManager.getInstance().getTeam(n), TeamManager.getInstance().getBLUE());
             if(types == KitTypes.WARRIOR){
                 this.AllkitList.givePlayerWarriorWeapon(n, TeamManager.getInstance().getTeam(n));
             }else if(types == KitTypes.ARCHER) {
                 this.AllkitList.givePlayerArcherWeapon(n, TeamManager.getInstance().getTeam(n), TeamManager.getInstance().getBLUE());
             } else {
                 AllkitList.givePlayerWarriorWeapon(n, TeamManager.getInstance().getTeam(n));
             }
         }
    }

    public void removeAllItemsFromPlayer(Player player){
        player.getInventory().clear();
    }

    public static KitManager getInstance() {
        return INSTANCE;
    }
}
