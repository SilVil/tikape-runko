/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author ville
 */
public class Yhteenveto {
    private String id;
    private String nimi;
    private Integer viestienLukumaara;
    private String aika;

    public Yhteenveto(String id, String nimi, Integer viestienLukumaara, String aika) {
        this.id = id;
        this.nimi = nimi;
        this.viestienLukumaara = viestienLukumaara;
        this.aika = aika;
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

    public Integer getViestienLukumaara() {
        return viestienLukumaara;
    }

    public void setViestienLukumaara(Integer viestienLukumaara) {
        this.viestienLukumaara = viestienLukumaara;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String paivamaara) {
        this.aika = paivamaara;
    }

    
    
    
}
