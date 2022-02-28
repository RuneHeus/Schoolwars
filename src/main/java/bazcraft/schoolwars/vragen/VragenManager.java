package bazcraft.schoolwars.vragen;

import bazcraft.schoolwars.npc.CustomNPC;
import bazcraft.schoolwars.Schoolwars;
import bazcraft.schoolwars.teams.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;

public class VragenManager{

    private Schoolwars plugin;
    private ArrayList<Vraag> vragenLijst;
    private TeamManager teamManager;

    public VragenManager(TeamManager teamManager, Schoolwars plugin){
        this.plugin = plugin;
        this.vragenLijst = new ArrayList<>() {{
            add(new Vraag("Wat is de naam van de programmeertaal waarin deze plugin is geschreven?", "java", VraagType.NORMAAL, plugin));
            add(new Vraag("Hoeveel is 2x2", "4", VraagType.NORMAAL, plugin));
            add(new Vraag("Wat is de naam van deze game?", "Minecraft", VraagType.NORMAAL, plugin));
            add(new Vraag("Wat is de naam van onze school?", "BAZandpoort", VraagType.NORMAAL, plugin));
            add(new Vraag("In welk jaar begon de 2de wereld oorlog", "1939", VraagType.NORMAAL, plugin));
            add(new Vraag("Hoeveel bits kunnen er in een byte", "8", VraagType.SPECIAAL, plugin));
            add(new Vraag("Wat is de online naam van Mr.Wijns", "Gerritje69", VraagType.SPECIAAL, plugin));
            add(new Vraag("Welke programmeer taal wordt gebruikt om met een databank te communiceren?", "sql", VraagType.SPECIAAL,plugin));
            add(new Vraag("Is HTML een programmeer taal?(ja/nee)", "nee", VraagType.SPECIAAL, plugin));
        }};
        this.teamManager = teamManager;
        //TODO momenteel zijn de bazcraft.schoolwars.vragen hardcoded to we een database hebben
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
                Vraag vraag = new Vraag(result.getString("vraag"), result.getString("antwoord"), type, plugin);
                this.vragenLijst.add(vraag);
            }
        } catch (SQLException e) {
            System.err.println("Not able to connect to Database");
        }
    }

    public Schoolwars getPlugin() {
        return plugin;
    }

    public void setPlugin(Schoolwars plugin) {
        this.plugin = plugin;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void startVraag(Player player){
        player.openBook(this.getVraagBoek(player));
    }

    public boolean compareAnswer(String antwoord, Vraag vraag){
        return antwoord.equalsIgnoreCase(vraag.getAntwoord());
    }

    public ItemStack getVraagBoek(Player player){
        CustomNPC npc = plugin.getNpcManager().getGeselecteerdeNPC().get(player);
        return npc.getActieveVraag().getBook();
    }
}