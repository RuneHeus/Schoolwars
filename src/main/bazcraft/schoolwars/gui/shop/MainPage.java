package main.bazcraft.schoolwars.gui.shop;

import main.bazcraft.schoolwars.npc.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;

import static main.bazcraft.schoolwars.tools.ItemUtils.createItem;

public class MainPage implements InventoryHolder{

    private final Inventory inv;
    private CustomNPC npc;

    public MainPage(CustomNPC npc) {
        inv = Bukkit.createInventory(this, 27, "SHOP");
        this.npc = npc;
        init();
    }

    private void init() {
        //Voor de rand van de GUI
        for (int i = 0; i < inv.getSize(); i++) {
            if (i < 10 || i > 16 && i < 28) {
                inv.setItem(i, createItem("", Material.GRAY_STAINED_GLASS_PANE, 1, null, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}));
            }
        }

        inv.setItem(13, createItem("§aMinionPoint", Material.PLAYER_HEAD));
    }

    public CustomNPC getNpc() {
        return npc;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
