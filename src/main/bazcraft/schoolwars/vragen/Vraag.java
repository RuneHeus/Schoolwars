package bazcraft.schoolwars.vragen;

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
    private boolean blauw;
    private boolean rood;

    public Vraag(String vraag, String antwoord, VraagType type){
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.type = type;
        this.blauw = false;
        this.rood = false;

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

    public void setBook(ItemStack book) {
        this.book = book;
    }

    public boolean isBlauw() {
        return blauw;
    }

    public void setBlauw(boolean blauw) {
        this.blauw = blauw;
    }

    public boolean isRood() {
        return rood;
    }

    public void setRood(boolean rood) {
        this.rood = rood;
    }
}
