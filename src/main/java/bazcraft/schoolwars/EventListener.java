package bazcraft.schoolwars;

import bazcraft.schoolwars.GUI.VragenGUI;
import bazcraft.schoolwars.GUI.shop.MainPage;
import bazcraft.schoolwars.NPC.CustomNPC;
import bazcraft.schoolwars.Vragen.Vraag;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.npc.ai.speech.Chat;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
                    break;
                }
            case INGAME, ENDGAME:
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                break;
        }
        event.getPlayer().teleport(plugin.getGameManager().getLobby());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
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
        } else {
            plugin.getTeamManager().getTeam((Player) event.getEntity()).removeHealth(10);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNPCRightClick(NPCRightClickEvent event){
        Player player = event.getClicker();
        CustomNPC npc = plugin.getNpcManager().getCustomNPC(event.getNPC());
        if (npc != null) {
            switch (npc.getType()) {
                case SHOP:
                    MainPage mainPage = new MainPage();
                    player.openInventory(mainPage.getInventory());
                    break;
                default:
                    VragenGUI gui = new VragenGUI(player);
                    Inventory inventory = gui.getGui();
                    player.openInventory(inventory);

            }
        }
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event){
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getHolder() instanceof MainPage) {
                if (event.getCurrentItem() != null) {
                    switch (event.getCurrentItem().getType()) {
                        case PLAYER_HEAD -> plugin.getVragenManager().startVraag((Player)event.getWhoClicked());
                    }
                }
                event.setCancelled(true);
            }
        }

        //logica Team kleur
        if(plugin.getVragenManager().getActieveVraagBlauw() == null){
            this.plugin.getVragenManager().setActieveVraagBlauw(this.plugin.getVragenManager().getVraag());
        }
        ItemStack book = plugin.getVragenManager().getActieveVraagBlauw().getBook();
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem() != null){
            Material clickedItem = Objects.requireNonNull(event.getCurrentItem()).getType();
            if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Vragen Menu")){
                if(clickedItem.equals(Material.BOOK)){
                    player.openBook(book);
                }else if(clickedItem.equals(Material.WRITTEN_BOOK)){
                    player.sendMessage(ChatColor.RED + "Debug: " + ChatColor.GREEN + "Normaal moete nu een antwoord kunnen geven");
                    //logica commandline openen
                }
                event.setCancelled(true);
            }
        }
    }
}
