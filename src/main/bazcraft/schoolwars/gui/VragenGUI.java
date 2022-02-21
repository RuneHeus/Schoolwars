package main.bazcraft.schoolwars.gui;

import main.bazcraft.schoolwars.npc.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VragenGUI implements InventoryHolder{

    private Inventory gui;
    private final CustomNPC npc;

    public VragenGUI(CustomNPC npc){
        gui = Bukkit.createInventory(this, 9, ChatColor.AQUA + "Vragen Menu");
        this.npc = npc;

        //ItemMeta vraag Boek
        ItemStack vraagBoek = new ItemStack(Material.BOOK);
        ItemMeta vraagBoekMeta = vraagBoek.getItemMeta();
        vraagBoekMeta.setDisplayName(ChatColor.GREEN + "Vraag");
        ArrayList<String> vraagBoekLore = new ArrayList<>();
        vraagBoekLore.add(ChatColor.AQUA + "Klik voor een vraag te krijgen.");
        vraagBoekMeta.setLore(vraagBoekLore);
        vraagBoek.setItemMeta(vraagBoekMeta);
        gui.addItem(vraagBoek);
    }

    public CustomNPC getNpc() {
        return npc;
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}
