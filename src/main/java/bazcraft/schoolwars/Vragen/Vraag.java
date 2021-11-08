package bazcraft.schoolwars.Vragen;

public class Vraag {


    private String vraag;
    private String antwoord;
    private VraagType type;

    //Conectie met een databank voor vragen uit te kunne halen


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
}
