package bazcraft.schoolwars;

import bazcraft.schoolwars.Kit.KitManager;
import bazcraft.schoolwars.Kit.KitTypes;
import bazcraft.schoolwars.gui.KitGUI;
import bazcraft.schoolwars.gui.Scoreboard;
import bazcraft.schoolwars.gui.VragenGUI;
import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.npc.NPCManager;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import bazcraft.schoolwars.tools.ItemUtils;
import bazcraft.schoolwars.vragen.KlasLokaal;
import bazcraft.schoolwars.vragen.VraagType;
import bazcraft.schoolwars.gui.LeaveUi;
import bazcraft.schoolwars.gui.shop.MainPage;
import bazcraft.schoolwars.vragen.VragenManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public class EventListener implements Listener {

    private static final EventListener INSTANCE = new EventListener();

    private final String KITSELECTOR = "§d§lkitselector";

    private EventListener() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (GameState.getCurrentGamestate()) {
            case WAITING:
                GameManager game = GameManager.getInstance();
                if (game.addSpeler(event.getPlayer())) {
                    event.setJoinMessage(event.getPlayer().getDisplayName() + " §ejoined (" + game.getIngamePlayers().size() + "/" + game.getMaxAantalSpelers()+ ")");
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                    event.getPlayer().getInventory().addItem(ItemUtils.createItem(KITSELECTOR, Material.BOW));

                    new Scoreboard(event.getPlayer()).createBoard();
                    break;
                }
            case INGAME, ENDGAME:
                event.setJoinMessage(null);
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                break;
        }
        event.getPlayer().teleport(GameManager.getInstance().getLobby());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event){

        KitManager.getInstance().removeAllItemsFromPlayer(event.getPlayer());

        GameManager game = GameManager.getInstance();
        game.removeSpeler(event.getPlayer());
        if (GameState.getCurrentGamestate() == GameState.WAITING) {
            event.setQuitMessage(event.getPlayer().getDisplayName() + " §cleft (" + game.getIngamePlayers().size() + "/" + game.getMaxAantalSpelers()+ ")");
        } else if (GameState.getCurrentGamestate() == GameState.INGAME) {
            if (game.getIngamePlayers().contains(event.getPlayer())) {
                event.setQuitMessage(event.getPlayer().getDisplayName() + " §cleft");
            } else {
                event.setQuitMessage(null);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event){
        //in de lobby mag men geen damage nemen
        if(GameState.getCurrentGamestate() == GameState.WAITING){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onNPCRightClick(NPCRightClickEvent event){
        Player player = event.getClicker();
        CustomNPC npc = NPCManager.getInstance().getCustomNPC(event.getNPC());
        if (npc != null) {
            if(TeamManager.getInstance().getTeam(player) == npc.getTeam()){
                if (npc.getType() == VraagType.SPECIAAL){
                    MainPage mainPage = new MainPage(npc);
                    player.openInventory(mainPage.getInventory());
                } else {
                    VragenGUI gui = new VragenGUI(event.getClicker(), npc);
                    player.openInventory(gui.getInventory());
                }
            }else{
                player.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Dit is niet jouw leerkracht!");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onGuiClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Team team = TeamManager.getInstance().getTeam(player);
        if (event.getClickedInventory() != null) {
            InventoryHolder holder = event.getClickedInventory().getHolder();
            if (holder instanceof MainPage) {
                MainPage gui = (MainPage) event.getClickedInventory().getHolder();
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getType() == Material.PLAYER_HEAD){
                        if (team.getMinionPoints() > 0) {
                            if(!team.isBeantwoordenVragenS()){
                                NPCManager.getInstance().addGeselecteerdeNPC(player, gui.getNpc());
                                VragenManager.getInstance().startVraag(player);
                            }else{
                                player.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Alle speciale vragen zijn al opgelost!");
                            }
                        } else {
                            player.sendMessage(Schoolwars.prefix + " §cJe team heeft niet genoeg minionpoints");
                        }
                    }
                }
                event.setCancelled(true);
            }else if(event.getView().getTitle().equals(ChatColor.AQUA + "Vragen Menu")){
                if(event.getCurrentItem() != null){
                    if(event.getCurrentItem().getType() == Material.BOOK){
                        if(!team.isBeantwoordenVragenN()){
                            if(KlasLokaal.getInstance().getPlayersInClassRoom().containsKey(player)){
                                VragenManager.getInstance().startVraag(player);
                            }else{
                                NPCManager.getInstance().addGeselecteerdeNPC(player, NPCManager.getInstance().getLeerkrachtNpc(TeamManager.getInstance().getTeam(player)));
                                KlasLokaal.getInstance().addPlayersInClassRoom(player);
                                KlasLokaal.getInstance().teleportToClassRoom(player);
                            }
                        }else{
                            player.sendMessage(Schoolwars.prefix + " " + ChatColor.RED + "Alle vragen zijn al opgelost!");
                        }
                    }
                    event.setCancelled(true);
                }
            }else if(event.getView().getTitle().equals(ChatColor.RED + "Kit Menu")){
                if(event.getCurrentItem() != null){
                    if(GameState.getCurrentGamestate() == GameState.WAITING){
                        if(Objects.requireNonNull(event.getCurrentItem()).getType() == Material.STONE_SWORD){
                            KitManager.getInstance().addPlayerWithKit(player, KitTypes.WARRIOR);
                            player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Je hebt de " + ChatColor.RED + "Warrior" + ChatColor.AQUA + " kit gekozen");
                        }else if(event.getCurrentItem().getType().equals(Material.BOW)){
                            KitManager.getInstance().addPlayerWithKit(player, KitTypes.ARCHER);
                            player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Je hebt de " + ChatColor.GREEN + "Archer" + ChatColor.AQUA + " kit gekozen");
                        }
                        event.setCancelled(true);
                    }
                }
            }else if(event.getView().getTitle().equals(ChatColor.GREEN + "Verlaat Lokaal")){
                if(event.getCurrentItem() != null){
                    if(Objects.requireNonNull(event.getCurrentItem()).getType() == Material.GREEN_CONCRETE){
                        CustomNPC npc = NPCManager.getInstance().getLeerkrachtNpc(team);
                        KlasLokaal.getInstance().teleportToMainGame(player, npc);
                        NPCManager.getInstance().removeGeselecteerdeNPC(player);
                    }else if(Objects.requireNonNull(event.getCurrentItem()).getType() == Material.RED_CONCRETE){
                        player.closeInventory();
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(GameState.getCurrentGamestate() == GameState.WAITING){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
                if(event.getItem() != null){
                    if(Objects.requireNonNull(event.getItem()).getType() == Material.BOW && event.getItem().getItemMeta().getDisplayName().equals(KITSELECTOR)){
                        KitGUI kitGUI = new KitGUI(player);
                        Inventory inventory = kitGUI.getInventory();
                        player.openInventory(inventory);
                    }
                }
            }
        }else{
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK )|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                if((event.getClickedBlock() != null) && (event.getClickedBlock().getType().equals(Material.STONE_BUTTON)) && (event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world"), 431, 32, -103)) || event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world"), 10, 43, -97)))){
                    LeaveUi leaveUi = new LeaveUi(event.getPlayer());
                    Inventory inventory = leaveUi.getInventory();
                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Team team = TeamManager.getInstance().getTeam(event.getPlayer());
        if (team != null) {
            event.setRespawnLocation(team.getSpawn());
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupArrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCitizensEnable(CitizensEnableEvent event) {
        for (CustomNPC n : NPCManager.getInstance().getNpcList()){

            for (NPC m : CitizensAPI.getNPCRegistry()) {
                if (n.getName().equals(m.getName())) {
                    n.setNpc(m);
                    break;
                }
            }

            if (n.getNpc() == null) {
                n.setNpc(CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, n.getName()));
            }
        }
    }

    public static EventListener getInstance() {
        return INSTANCE;
    }
}
