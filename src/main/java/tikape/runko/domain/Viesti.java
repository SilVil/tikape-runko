package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {

    private String id;
    private String nimi;
    private String aika;
    private String teksti;
    private String viestiketju;

    public Viesti(String id, String nimi, String aika, String teksti, String viestiketju) {
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

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public String getViestiketju() {
        return viestiketju;
    }

    public void setViestiKetjuId(String viestiketju) {
        this.viestiketju = viestiketju;
    }

}
