package tikape.runko.domain;

import java.util.ArrayList;

public class Viestiketju {

    private String id;
    private String otsikko;
    private ArrayList<Viesti> viestit;

    public Viestiketju(String id, String otsikko, ArrayList<Viesti> viestit) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = viestit;
    }

    public Viestiketju(String id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = new ArrayList<>();
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
