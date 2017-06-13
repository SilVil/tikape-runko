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
import tikape.runko.domain.Viestit;
import tikape.runko.domain.Yhteenvedot;
import tikape.runko.domain.Yhteenveto;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        //Tämä post antaa käyttäjälle mahdollisuuden perustaa uuden keskustelualueen
        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi").trim();

            if (!nimi.isEmpty()) {
                keskustelualueDao.create(new Keskustelualue(UUID.randomUUID().toString().substring(0, 4), nimi));
            }

            res.redirect("/");
            return "";
        });

        //Tämä post antaa käyttäjälle mahdollisuuden perustaa uuden viestiketjun
        post("/:areaId", (req, res) -> {
            String parentId = req.params(":areaId");
            System.out.println(parentId);
            String otsikko = req.queryParams("otsikko").trim();
            if (!otsikko.isEmpty()) {
                viestiketjuDao.create(new Viestiketju(UUID.randomUUID().toString().substring(0, 4), otsikko, parentId));
            }
            res.redirect("/" + req.params(":areaId"));
            return "";
        });

        //Tämä post antaa käyttäjälle mahdollisuuden vastata viestiin
        post("/:areaId/:id", (req, res) -> {
            String parentId = req.params(":id");
            System.out.println(parentId);
            String teksti = req.queryParams("teksti").trim();
            String nimi = req.queryParams("nimi").trim();
            if (!teksti.isEmpty() && !nimi.isEmpty()) {
                viestiDao.create(new Viesti(UUID.randomUUID().toString().substring(0, 4),
                        nimi,
                        "n/a",
                        teksti,
                        parentId));
            }
            res.redirect("/" + req.params(":areaId") + "/" + req.params(":id"));
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

            List<Yhteenveto> viestiketjujenYhteenvedot = new ArrayList<>();
            for (Viestiketju viestiketju : viestiketjuDao.findThreadsOfArea(req.params(":id"))) {
                viestiketjujenYhteenvedot.add(viestiketjuDao.
                        findOnesMessageCountsAndLatest(
                                viestiketju.getId(), viestiketju.getOtsikko()));
            }

            Yhteenvedot yhteenvedotKeskustelualue = new Yhteenvedot(req.params(":id"), keskustelualueDao.findTitle(req.params(":id")), viestiketjujenYhteenvedot);

            HashMap<String, Object> data = new HashMap<>();
            data.put("yhteenvedotKeskustelualue", yhteenvedotKeskustelualue);
            return new ModelAndView(data, "keskustelualue");
        }, new ThymeleafTemplateEngine());

        //Tämä getti näyttää viestiketjun
        get("/:areaId/:id", (req, res) -> {
            List<Viesti> viestit = viestiDao.findMessagesOfThread(req.params(":id"));
            Viestit viestitViestiketju = new Viestit(req.params(":id"), viestiketjuDao.findTitle(req.params(":id")), viestit, viestiketjuDao.findAreaKey(req.params(":id")));
            HashMap<String, Object> data = new HashMap<>();
            data.put("viestitViestiketju", viestitViestiketju);
            return new ModelAndView(data, "viestiketju");
        }, new ThymeleafTemplateEngine());
    }

}
