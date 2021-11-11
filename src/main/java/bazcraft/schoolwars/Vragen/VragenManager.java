package bazcraft.schoolwars.Vragen;

import java.sql.*;
import java.util.ArrayList;

public class VragenManager {

    private ArrayList<Vraag> vragenLijst;
    private ArrayList<Integer> beantwoordenVragen;

    public VragenManager(){
        this.vragenLijst = new ArrayList<>();
        this.beantwoordenVragen = new ArrayList<>();

        //TODO momenteel zijn de vragen hardcoded to we een database hebben

        Vraag vraag = new Vraag("Wat is de naam van de programmeertaal waarin deze plugin is geschreven?", "java", VraagType.AARDRIJKSKUNDE);
        this.vragenLijst.add(vraag);
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
}
