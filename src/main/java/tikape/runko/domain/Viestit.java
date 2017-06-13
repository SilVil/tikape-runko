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
public class Viestit {
    private String parentId;
    private String parentName;
    private List<Viesti> viestit;
    private String grandparentId;

    public Viestit(String parentId, String parentName, List<Viesti> viestit, String grandparentId) {
        this.parentId = parentId;
        this.parentName = parentName;
        this.viestit = viestit;
        this.grandparentId = grandparentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public String getGrandparentId() {
        return grandparentId;
    }

    public void setGrandparentId(String grandparentId) {
        this.grandparentId = grandparentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    
    
    
}
