package bazcraft.schoolwars.gui;

import bazcraft.schoolwars.tools.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LeaveUi extends InventoryGui {

    public LeaveUi(Player player){
        super(Bukkit.createInventory(player, 27, ChatColor.GREEN + "Verlaat Lokaal"), new ItemStack[]{new ItemStack(Material.GREEN_CONCRETE), new ItemStack(Material.RED_CONCRETE)});

        //Voor de rand van de GUI
        for (int i = 0; i < inv.getSize(); i++) {
            if (i < 10 || i > 16 && i < 28) {
                inv.setItem(i, ItemUtils.createItem("", Material.GRAY_STAINED_GLASS_PANE, 1, null, new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}));
            }
        }
        inv.setItem(12, ItemUtils.createItem("§aJa, ik wil het lokaal verlaten", itemStacks[0].getType()));
        inv.setItem(14, ItemUtils.createItem("§cNeen, ik wil het lokaal niet verlaten", itemStacks[1].getType()));
    }
}