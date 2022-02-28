package main.bazcraft.schoolwars.gui;

import main.bazcraft.schoolwars.npc.CustomNPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VragenGUI extends InventoryGui{
    private final CustomNPC npc;

    public VragenGUI(Player player, CustomNPC npc){
        super(Bukkit.createInventory(player, 9, ChatColor.AQUA + "Vragen Menu"), new ItemStack[]{new ItemStack(Material.BOOK)});
        this.npc = npc;
        //ItemMeta vraag Boek
        ItemMeta vraagBoekMeta =  itemStacks[0].getItemMeta();
        vraagBoekMeta.setDisplayName(ChatColor.GREEN + "Vraag");
        ArrayList<String> vraagBoekLore = new ArrayList<>();
        vraagBoekLore.add(ChatColor.AQUA + "Klik voor een vraag te krijgen.");
        vraagBoekMeta.setLore(vraagBoekLore);
        itemStacks[0].setItemMeta(vraagBoekMeta);
        inv.addItem( itemStacks[0]);
    }

    public CustomNPC getNpc() {
        return npc;
    }
}
