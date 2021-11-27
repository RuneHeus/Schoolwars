package bazcraft.schoolwars.Vragen;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

public class VragenManager {

    private ArrayList<Vraag> vragenLijst;
    private Vraag actieveVraagBlauw;
    private Vraag actieveVraagRood;

    public VragenManager(){
        this.vragenLijst = new ArrayList<>(){{
            add(new Vraag("Wat is de naam van de programmeertaal waarin deze plugin is geschreven?", "java", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Hoeveel is 2x2", "4", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de naam van deze game?", "Minecraft", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de naam van onze school?", "BAZandpoort", VraagType.AARDRIJKSKUNDE));
            add(new Vraag("Wat is de slechteste programmeer taal?", "python", VraagType.AARDRIJKSKUNDE));
        }};

        //TODO momenteel zijn de vragen hardcoded to we een database hebben
        /*addAllVragen();*/
    }

    public ArrayList<Vraag> getVragenLijst(){
        return this.vragenLijst;
    }

    public void addAllVragen(){
        //database connectie waarbij je vraag en antwoord en type uit de db haalt en deze in een het Vraag object steek en deze in de arraylist plaatst
        try{
            Connection connection = DriverManager.getConnection("localhost", "root", ""); //momenteel localhost
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM tblvragen"); //momentele query

            while(result.next()){
                VraagType type = null;
                switch (result.getString("type")){
                    case "wiskunde": type = VraagType.WISKUNDE;break;
                    case "frans": type = VraagType.FRANS;break;
                    case "aardrijkskunde": type = VraagType.AARDRIJKSKUNDE;break;
                    case "geschiedenis": type = VraagType.GESCHIEDENIS;break;
                }
                Vraag vraag = new Vraag(result.getString("vraag"), result.getString("antwoord"), type);
                this.vragenLijst.add(vraag);
            }
        }catch (SQLException e){
            System.err.println("Not able to connect to Database");
        }
    }

    public Vraag getVraag(){
        for(int i = 0; i < this.vragenLijst.size(); i++){
            if(!this.vragenLijst.get(i).isBlauw()){
                Vraag vraag = this.vragenLijst.get(i);
                this.actieveVraagBlauw = vraag;
                return vraag;
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

    public void startVraag(Player player) {
        //TODO teleporteer speler naar vraag plaats
    }
}
