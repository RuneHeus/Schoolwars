package bazcraft.schoolwars.Vragen;

import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class VragenManager{

    private Schoolwars plugin;
    private ArrayList<Vraag> vragenLijst;
    private Vraag actieveVraagBlauw;
    private Vraag actieveVraagRood;
    private TeamManager teamManager;
    private boolean alleVragenRoodBeantwoord;
    private boolean alleVragenBlauwBeantwoord;

    public VragenManager(TeamManager teamManager, Schoolwars plugin) {
        this.plugin = plugin;
        this.vragenLijst = new ArrayList<>() {{
            add(new Vraag("Wat is de voledige naam van de maker van deze plugin?", "Rune Heus", VraagType.NEDERLANDS));
            add(new Vraag("Wat is de naam van de programmeertaal waarin deze plugin is geschreven?", "java", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Hoeveel is 2x2", "4", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de naam van deze game?", "Minecraft", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de naam van onze school?", "BAZandpoort", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de slechteste programmeer taal?", "python", VraagType.AARDRIJKSKUNDE));
        }};
        this.teamManager = teamManager;

        this.alleVragenBlauwBeantwoord = false;
        this.alleVragenRoodBeantwoord = false;
        //TODO momenteel zijn de vragen hardcoded to we een database hebben
        /*addAllVragen();*/
    }

    public ArrayList<Vraag> getVragenLijst() {
        return this.vragenLijst;
    }

    public void addAllVragen() {
        //database connectie waarbij je vraag en antwoord en type uit de db haalt en deze in een het Vraag object steek en deze in de arraylist plaatst
        try {
            Connection connection = DriverManager.getConnection("localhost", "root", ""); //momenteel localhost
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM tblvragen"); //momentele query

            while (result.next()) {
                VraagType type = null;
                switch (result.getString("type")) {
                    case "wiskunde":
                        type = VraagType.WISKUNDE;
                        break;
                    case "frans":
                        type = VraagType.FRANS;
                        break;
                    case "aardrijkskunde":
                        type = VraagType.AARDRIJKSKUNDE;
                        break;
                    case "geschiedenis":
                        type = VraagType.GESCHIEDENIS;
                        break;
                }
                Vraag vraag = new Vraag(result.getString("vraag"), result.getString("antwoord"), type);
                this.vragenLijst.add(vraag);
            }
        } catch (SQLException e) {
            System.err.println("Not able to connect to Database");
        }
    }

    public Vraag getVraag(Team team) {
        for (Vraag vraag : this.vragenLijst) {
            if(team == this.teamManager.getBLUE()){
                if (!vraag.isBlauw()){
                    return vraag;
                }
            }else{
                if(!vraag.isRood()){
                    return vraag;
                }
            }
        }
        return null;
    }

    public void setVragenLijst(ArrayList<Vraag> vragenLijst) {
        this.vragenLijst = vragenLijst;
    }

    public Vraag getActieveVraagBlauw() {
        return actieveVraagBlauw;
    }

    public void setActieveVraagBlauw(Vraag actieveVraagBlauw) {
        this.actieveVraagBlauw = actieveVraagBlauw;
    }

    public Vraag getActieveVraagRood() {
        return actieveVraagRood;
    }

    public void setActieveVraagRood(Vraag actieveVraagRood) {
        this.actieveVraagRood = actieveVraagRood;
    }

    public boolean isAlleVragenRoodBeantwoord() {
        return alleVragenRoodBeantwoord;
    }

    public void setAlleVragenRoodBeantwoord(boolean alleVragenRoodBeantwoord) {
        this.alleVragenRoodBeantwoord = alleVragenRoodBeantwoord;
    }

    public boolean isAlleVragenBlauwBeantwoord() {
        return alleVragenBlauwBeantwoord;
    }

    public void setAlleVragenBlauwBeantwoord(boolean alleVragenBlauwBeantwoord) {
        this.alleVragenBlauwBeantwoord = alleVragenBlauwBeantwoord;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }
    public void startVraag(Player player) {
        //TODO teleporteer speler naar vraag plaats
    }

    public void compareAnswer(String[]args, Player player){
        Team team = this.teamManager.getTeam(player);
        String antwoord = "";
        for(int i = 0; i < args.length; i++){
            if(args[i].length() > 0){
                if(i == 0){
                    antwoord += args[i];
                }else{
                    antwoord += " " + args[i];
                }
            }
        }else{
            if(args[0].toLowerCase(Locale.ROOT).equals(this.actieveVraagRood.getAntwoord().toLowerCase(Locale.ROOT))){
                player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Juist antwoord!");
                this.actieveVraagRood.setRood(true);
                if (this.vragenLijst.indexOf(this.actieveVraagRood)+1 == this.vragenLijst.size()) {
                    this.alleVragenRoodBeantwoord = true;
                }
            }else{
                if(antwoord.toLowerCase(Locale.ROOT).equals(this.actieveVraagRood.getAntwoord().toLowerCase(Locale.ROOT))){
                    player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Juist antwoord!");
                    this.actieveVraagRood.setRood(true);
                    this.plugin.getKlasLokaal().teleportToMainGame(player);
                    this.plugin.getKlasLokaal().removePlayerInClassRoom(player);
                    if (this.vragenLijst.indexOf(this.actieveVraagRood)+1 == this.vragenLijst.size()) {
                        this.alleVragenRoodBeantwoord = true;
                    }
                }else{
                    player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Antwoord is niet juist!");
                }
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Je moet een antwoord geven!");
        }
    }

    public ItemStack getVraagBoek(Player player){
        ItemStack book;
        if(this.teamManager.getTeam(player) == this.teamManager.getBLUE()){
            this.setActieveVraagBlauw(this.getVraag(this.teamManager.getTeam(player)));
            book = this.getActieveVraagBlauw().getBook();
        }else{
            this.setActieveVraagRood(this.getVraag(this.teamManager.getTeam(player)));
            book = this.getActieveVraagRood().getBook();
        }
        return book;
    }
}