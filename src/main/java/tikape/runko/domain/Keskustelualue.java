package tikape.runko.domain;

public class Keskustelualue {

    private String id;
    private String nimi;

    public Keskustelualue(String id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    
    
}
