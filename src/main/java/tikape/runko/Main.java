package tikape.runko;

import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.domain.Keskustelualue;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);

        post("/", (req, res) -> {
            String nimi = req.queryParams("name").trim();

            if (!nimi.isEmpty()) {
                keskustelualueDao.create(new Keskustelualue(UUID.randomUUID().toString().substring(0, 4), nimi));
            }

            res.redirect("/");
            return "";
        });

        get("/", (req, res) -> {
            TreeMap<String, Object> data = new TreeMap<>();
            List<Keskustelualue> keskustelualueet = keskustelualueDao.findAll();
            data.put("keskustelualueet", keskustelualueet);
            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());

//         get("/:id", (req, res) -> {
//            TreeMap<String, Object> data = new TreeMap<>();
//            List<Viestiketju> viestiketjut = keskustelualueDao.findAll();
//            data.put("keskustelualue", k);
//            return new ModelAndView(data, "keskustelualue");
//        }, new ThymeleafTemplateEngine());
//        
        
    }

}
