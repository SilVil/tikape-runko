package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Yhteenveto;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        //Tämä post antaa käyttäjälle mahdollisuuden perustaa uuden keskustelualueen
        post("/keskustelualueet", (req, res) -> {
            String nimi = req.queryParams("nimi").trim();

            if (!nimi.isEmpty()) {
                keskustelualueDao.create(new Keskustelualue(UUID.randomUUID().toString().substring(0, 4), nimi));
            }

            res.redirect("/");
            return "";
        });

        //Tämä post antaa käyttäjälle mahdollisuuden perustaa uuden viestiketjun
        post("/:areaId/yhteenveto", (req, res) -> {
            String parentId = req.params(":areaId");
            System.out.println(parentId);
            // entä jos tässä lisäisi keskustelualueella olevaan viestiketjulistaan uuden viestiketjun?
            String otsikko = req.queryParams("otsikko").trim();

            if (!otsikko.isEmpty()) {
                viestiketjuDao.create(new Viestiketju(UUID.randomUUID().toString().substring(0, 4), otsikko, parentId, new ArrayList<>()));
            }

            res.redirect("/:id");
            return "";
        });

        //Tämä getti näyttää keskustelualueisiin liittyvää tietoa juuriosoitteessa
        get("/", (req, res) -> {
            TreeMap<String, Object> data = new TreeMap<>();
            List<Yhteenveto> keskustelualueidenYhteenvedot = new ArrayList<>();
            for (Keskustelualue keskustelualue : keskustelualueDao.findAll()) {
                keskustelualueidenYhteenvedot.add(keskustelualueDao.
                        findOnesMessageCountsAndLatest(
                                keskustelualue.getId(), keskustelualue.getNimi()));
            }
            data.put("keskustelualueidenYhteenvedot", keskustelualueidenYhteenvedot);
            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());

        //Tämä getti näyttää yhteen keskustelualueeseen liittyvää tietoa keskustelualueella
        get("/:id", (req, res) -> {
            
            //Keskustelualue keskustelualue = keskustelualueDao.findOne(req.params(":id"));
            
            //String keskustelualueenNimi = keskustelualue.getNimi();
      
            List<Yhteenveto> viestiketjujenYhteenvedot = new ArrayList<>();
            for (Viestiketju viestiketju : viestiketjuDao.findThreadsOfArea(req.params(":id"))) {
                viestiketjujenYhteenvedot.add(viestiketjuDao.
                        findOnesMessageCountsAndLatest(
                                viestiketju.getId(), viestiketju.getOtsikko()));
            }
            
          
            //lisää yhteenvetoihin tieto siitä, mikä on keskustelualueen ID
            for (Yhteenveto yhteenveto : viestiketjujenYhteenvedot) {
                yhteenveto.setParentId(req.params(":id"));
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("viestiketjujenYhteenvedot", viestiketjujenYhteenvedot);
            return new ModelAndView(data, "keskustelualue");
        }, new ThymeleafTemplateEngine());

        //Tämä getti näyttää viestiketjun
        get("/:areaId/:id", (req, res) -> {
            System.out.println("Viestiketjun id on" + req.params(":id"));
            List<Viesti> viestit = viestiDao.findMessagesOfThread(req.params(":id"));
            HashMap<String, Object> data = new HashMap<>();
            data.put("viestit", viestit);
            return new ModelAndView(data, "viestiketju");
        }, new ThymeleafTemplateEngine());
    }

}
