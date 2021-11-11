package bazcraft.schoolwars.Vragen;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Vraag {

    private ItemStack book;
    private String vraag;
    private String antwoord;
    private VraagType type;

    public Vraag(String vraag, String antwoord, VraagType type){
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.type = type;

        //Vraag boek aanmaken
        this.book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) this.book.getItemMeta();
        ArrayList<String> pages = new ArrayList<>();
        pages.add(0, vraag);
        meta.setTitle("Vraag");
        meta.setAuthor(ChatColor.GREEN + "BAZCraft");
        meta.setPages(pages);
        this.book.setItemMeta(meta);
    }

    public String getVraag(){
        return this.vraag;
    }

    public void setVraag(String vraag){
        this.vraag = vraag;
    }
    
    public String getAntwoord(){
        return this.antwoord;
    }

    public void setAntwoord(String antwoord){
        this.antwoord = antwoord;
    }

    public VraagType getType(){
        return this.type;
    }

    public void setType(VraagType type){
        this.type = type;
    }

    public ItemStack getBook(){
        return this.book;
    }
}
