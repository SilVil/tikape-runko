/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.List;

/**
 *
 * @author ville
 */
public class Yhteenvedot {
    private String parentId;
    private String parentName;
    private List<Yhteenveto> yhteenvedot;

    public Yhteenvedot(String parentId, String parentName, List<Yhteenveto> yhteenvedot) {
        this.parentId = parentId;
        this.parentName = parentName;
        this.yhteenvedot = yhteenvedot;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Yhteenveto> getYhteenvedot() {
        return yhteenvedot;
    }

    public void setYhteenvedot(List<Yhteenveto> yhteenvedot) {
        this.yhteenvedot = yhteenvedot;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    
    
}
