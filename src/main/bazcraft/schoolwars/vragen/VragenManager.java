package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.npc.NPCType;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.Team;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

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
            add(new Vraag("Wat is de naam van de programmeertaal waarin deze plugin is geschreven?", "java", VraagType.NORMAAL));
            add(new Vraag("Hoeveel is 2x2", "4", VraagType.NORMAAL));
            add(new Vraag("Wat is de naam van deze game?", "Minecraft", VraagType.NORMAAL));
            add(new Vraag("Wat is de naam van onze school?", "BAZandpoort", VraagType.NORMAAL));
            add(new Vraag("In welk jaar begon de 2de wereld oorlog", "1939", VraagType.NORMAAL));
            add(new Vraag("Hoeveel bits kunnen er in een byte", "8", VraagType.SPECIAAL));
            add(new Vraag("Wat is de online naam van Mr.Wijns", "Gerritje69", VraagType.SPECIAAL));
            add(new Vraag("Welke programmeer taal wordt gebruikt om met een databank te communiceren?", "sql", VraagType.SPECIAAL));
            add(new Vraag("Is HTML een programmeer taal?(ja/nee)", "nee", VraagType.SPECIAAL));
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
                VraagType type = switch (result.getString("type")) {
                    case "normaal" -> VraagType.NORMAAL;
                    case "speciaal" -> VraagType.SPECIAAL;
                    default -> null;
                };
                Vraag vraag = new Vraag(result.getString("vraag"), result.getString("antwoord"), type);
                this.vragenLijst.add(vraag);
            }
        } catch (SQLException e) {
            System.err.println("Not able to connect to Database");
        }
    }

    public Vraag getVraag(Player player){
        Team team = plugin.getTeamManager().getTeam(player);
        boolean vraagOpgelost = true;

        if(team == plugin.getTeamManager().getBLUE()){
            if(this.actieveVraagBlauw != null){
                if(this.getActieveVraagBlauw().isBlauw()){
                    vraagOpgelost = false;
                }else if(this.actieveVraagBlauw.getType() == VraagType.NORMAAL && this.plugin.getNpcManager().getGeselecteerdeNPC().get(player).getType() == NPCType.SPECIALNPC){
                    player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Er is al een vraag actief!");
                }
            }else{
                vraagOpgelost = false;
            }
        }else{
            if(this.actieveVraagRood != null){
                if(this.getActieveVraagRood().isRood()){
                    vraagOpgelost = false;
                }else if(this.actieveVraagRood.getType() == VraagType.NORMAAL && this.plugin.getNpcManager().getGeselecteerdeNPC().get(player).getType() == NPCType.SPECIALNPC){
                    player.sendMessage(ChatColor.GREEN + "Game: " + ChatColor.RED + "Er is al een vraag actief!");
                }{

                }
            }else{
                vraagOpgelost = false;
            }
        }

        if(!vraagOpgelost){
            Random rand = new Random();
            boolean goedVraag = false;
            Vraag vraag = null;
            VraagType type;

            if(plugin.getNpcManager().getGeselecteerdeNPC().get(player).getType() == NPCType.SPECIALNPC){
                type = VraagType.SPECIAAL;
            }else{
                type = VraagType.NORMAAL;
            }
            do{
                int randomVraag = rand.nextInt(this.vragenLijst.size());

                vraag = this.getVragenLijst().get(randomVraag);
                if(vraag.getType() == type){
                    if(team == plugin.getTeamManager().getRED()){
                        if(!vraag.isRood()){
                            goedVraag = true;
                        }
                    }else{
                        if(!vraag.isBlauw()){
                            goedVraag = true;
                        }
                    }
                }
            }while(!goedVraag);

            return vraag;
        }else{
            if(team == plugin.getTeamManager().getBLUE()){
                return this.actieveVraagBlauw;
            }else{
                return this.actieveVraagRood;
            }
        }
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

    public void startVraag(Player player){
        player.openBook(this.getVraagBoek(player));
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
        }
        if(antwoord.length() > 0){
            if(team == this.teamManager.getBLUE()){
                if (antwoord.toLowerCase(Locale.ROOT).equals(this.actieveVraagBlauw.getAntwoord().toLowerCase(Locale.ROOT))) {
                    juist(player, false);
                }else{
                    player.sendMessage(Schoolwars.prefix + ChatColor.RED + " Antwoord is niet juist!");
                }
            }else{
                if(antwoord.toLowerCase(Locale.ROOT).equals(this.actieveVraagRood.getAntwoord().toLowerCase(Locale.ROOT))){
                    juist(player, true);
                }else{
                    player.sendMessage(Schoolwars.prefix +  ChatColor.RED + " Antwoord is niet juist!");
                }
            }
        }else{
            player.sendMessage(Schoolwars.prefix +  ChatColor.RED + " Je moet een antwoord geven!");
        }
    }

    private void juist(Player player, boolean rood) {
        player.sendMessage(Schoolwars.prefix +  ChatColor.AQUA + " Juist antwoord!");
        if (rood) {
            this.actieveVraagRood.setRood(true);
            if (this.vragenLijst.indexOf(this.actieveVraagRood)+1 == this.vragenLijst.size()) {
                this.alleVragenRoodBeantwoord = true;
            }
        } else {
            this.actieveVraagBlauw.setBlauw(true);
            if (this.vragenLijst.indexOf(this.actieveVraagBlauw)+1 == this.vragenLijst.size()) {
                this.alleVragenBlauwBeantwoord = true;
            }
        }
        if(this.plugin.getNpcManager().getGeselecteerdeNPC().get(player).getType() == NPCType.SPECIALNPC){

        }else{
            this.plugin.getKlasLokaal().teleportToMainGame(player);
            this.plugin.getKlasLokaal().removePlayerInClassRoom(player);
        }


        //spawn minion
        plugin.getMinionManager().addMinion(plugin.getTeamManager().getTeam(player).getPath());

    }

    public ItemStack getVraagBoek(Player player){
        ItemStack book;
        if(this.teamManager.getTeam(player) == this.teamManager.getBLUE()){
            this.setActieveVraagBlauw(this.getVraag(player));
            book = this.getActieveVraagBlauw().getBook();
        }else{
            this.setActieveVraagRood(this.getVraag(player));
            book = this.getActieveVraagRood().getBook();
        }
        return book;
    }
}