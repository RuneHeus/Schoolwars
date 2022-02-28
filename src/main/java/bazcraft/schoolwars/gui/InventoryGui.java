package bazcraft.schoolwars.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryGui implements InventoryHolder {
    protected Inventory inv;
    protected ItemStack[] itemStacks;

    public InventoryGui(Inventory inv, ItemStack[] itemStacks){
        this.inv = inv;
        this.itemStacks = itemStacks;
    }

    @Override
    public Inventory getInventory(){
        return this.inv;
    }
}
