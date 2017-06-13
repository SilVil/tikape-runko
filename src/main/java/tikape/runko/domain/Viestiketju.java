package tikape.runko.domain;

import java.util.ArrayList;

public class Viestiketju {

    private String id;
    private String otsikko;
    private ArrayList<Viesti> viestit;
    private String keskustelualue;

    

    public Viestiketju(String id, String otsikko, String keskustelualue, ArrayList<Viesti> viestit) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = viestit;
        this.keskustelualue = keskustelualue;
    }

    public Viestiketju(String id, String otsikko, String keskustelualue) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = new ArrayList<>();
        this.keskustelualue = keskustelualue;
    }

    public String getKeskustelualue() {
        return keskustelualue;
    }

    public void setKeskustelualue(String keskustelualue) {
        this.keskustelualue = keskustelualue;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public ArrayList<Viesti> getViestit() {
        return viestit;
    }

    public void lisaaViesti(Viesti viesti) {
        this.viestit.add(viesti);
    }
}
