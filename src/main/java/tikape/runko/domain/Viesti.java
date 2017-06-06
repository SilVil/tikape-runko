package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {

    private String id;
    private String nimi;
    private Timestamp aika;
    private String teksti;
    private Integer viestiketju;

    public Viesti(String id, String nimi, Timestamp aika, String teksti, Integer viestiketju) {
        this.id = id;
        this.nimi = nimi;
        this.aika = aika;
        this.teksti = teksti;
        this.viestiketju = viestiketju;

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

    public Timestamp getAika() {
        return aika;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public Integer getViestiketju() {
        return viestiketju;
    }

    public void setViestiKetjuId(Integer viestiketju) {
        this.viestiketju = viestiketju;
    }

}
