package bazcraft.schoolwars.Events;

import bazcraft.schoolwars.GUI.KitGUI;
import bazcraft.schoolwars.GUI.VragenGUI;
import bazcraft.schoolwars.GUI.shop.MainPage;
import bazcraft.schoolwars.GameState;
import bazcraft.schoolwars.Kit.KitTypes;
import bazcraft.schoolwars.NPC.CustomNPC;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.Vragen.Vraag;
import bazcraft.schoolwars.teams.Team;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.npc.ai.speech.Chat;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class EventListener implements Listener {

    private final Schoolwars plugin;

    public EventListener(Schoolwars plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (GameState.getCurrentGamestate()) {
            case WAITING:
                if (plugin.getGameManager().addSpeler(event.getPlayer())) {
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.BOW));
                    break;
                }
            case INGAME, ENDGAME:
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                break;
        }
        event.getPlayer().teleport(plugin.getGameManager().getLobby());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event){
        event.getPlayer().getInventory().remove(Material.BOW);
        plugin.getKitManager().removeAllItemsFromPlayer(event.getPlayer());
        event.getPlayer().sendMessage("Remove all items");
        plugin.getGameManager().removeSpeler(event.getPlayer());
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
        CustomNPC npc = plugin.getNpcManager().getCustomNPC(event.getNPC());
        this.plugin.getNpcManager().addGeselecteerdeNPC(npc);
        if (npc != null) {
            switch (npc.getType()) {
                case SHOP:
                    MainPage mainPage = new MainPage();
                    player.openInventory(mainPage.getInventory());
                    break;
                default:
                    if(plugin.getTeamManager().getTeam(player) == npc.getTeam()){
                        VragenGUI gui = new VragenGUI(player);
                        Inventory inventory = gui.getGui();
                        player.openInventory(inventory);
                    }else{
                        player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Dit is niet jouw leerkracht!");
                    }
            }
        }
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Team team = plugin.getTeamManager().getTeam(player);
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getHolder() instanceof MainPage) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        plugin.getVragenManager().startVraag(player);

                    }
                }
                event.setCancelled(true);
            }
        }

        if(event.getCurrentItem() != null){
            Material clickedItem = Objects.requireNonNull(event.getCurrentItem()).getType();
            if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Vragen Menu")){
                if(clickedItem.equals(Material.BOOK)){
                    if(team == plugin.getTeamManager().getBLUE() && this.plugin.getVragenManager().isAlleVragenBlauwBeantwoord()){
                        player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Je hebt alle vragan opgelost!");
                    }else if(team == plugin.getTeamManager().getRED() && this.plugin.getVragenManager().isAlleVragenRoodBeantwoord()){
                        player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Je hebt alle vragen opgelost!");
                    }else{
                        //check where player is
                        if(this.plugin.getKlasLokaal().getPlayersInClassRoom().containsKey(player)){
                            player.openBook(plugin.getVragenManager().getVraagBoek(player));
                        }else{
                            this.plugin.getKlasLokaal().addPlayersInClassRoom(player);
                            this.plugin.getKlasLokaal().teleportToClassRoom(player);
                        }
                    }
                }
                event.setCancelled(true);
            }else if(event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Kit Menu")){
                if(GameState.getCurrentGamestate() == GameState.WAITING){
                    if(clickedItem.equals(Material.STONE_SWORD)){
                        plugin.getKitManager().addPlayerWithKit(player, KitTypes.WARRIOR);
                        player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Je hebt de " + ChatColor.RED + "Warrior" + ChatColor.AQUA + " kit gekozen");
                    }else if(clickedItem.equals(Material.BOW)){
                        plugin.getKitManager().addPlayerWithKit(player, KitTypes.ARCHER);
                        player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Je hebt de " + ChatColor.GREEN + "Archer" + ChatColor.AQUA + " kit gekozen");
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
                    if(Objects.requireNonNull(event.getItem()).getType() == Material.BOW){
                        KitGUI kitGUI = new KitGUI(player);
                        Inventory inventory = kitGUI.getGui();
                        player.openInventory(inventory);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Team team = plugin.getTeamManager().getTeam(event.getPlayer());
        if (team != null) {
            event.setRespawnLocation(team.getSpawn());
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupArrowEvent event) {
        event.setCancelled(true);
    }
}
