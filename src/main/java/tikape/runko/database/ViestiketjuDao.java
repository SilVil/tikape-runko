package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;
import tikape.runko.domain.Yhteenveto;

public class ViestiketjuDao implements Dao<Viestiketju, String> {

    private Database database;

    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Viestiketju create(Viestiketju t) throws SQLException {
        Connection connection = database.getConnection();

        String id = t.getId();
        String otsikko = t.getOtsikko();
        ArrayList<Viesti> viestit = new ArrayList<>();

        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Keskustelualue VALUES (?, ?, ?)");

        statement.setString(1, id);
        statement.setString(2, otsikko);
        statement.setObject(3, viestit);

        statement.execute();

        Viestiketju v = new Viestiketju(id, otsikko, viestit);

        statement.close();
        connection.close();

        return v;

    }

    @Override
    public Viestiketju findOne(String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String id = rs.getString("id");
        String otsikko = rs.getString("otsikko");

        Viestiketju v = new Viestiketju(id, otsikko, new ArrayList<>());

        rs.close();
        stmt.close();
        connection.close();

        return v;

    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String otsikko = rs.getString("otsikko");

            viestiketjut.add(new Viestiketju(id, otsikko, new ArrayList<>()));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;

    }

    public Yhteenveto findOnesMessageCountsAndLatest(String key, String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(Viesti.id) as viesteja,\n"
                + "	 IFNULL(MAX(Viesti.aika),'n/a') as viimeisinViesti\n"
                + "FROM Viesti, Viestiketju\n"
                + "WHERE Viestiketju.id = ? \n"
                + "AND Viesti.viestiketju = Viestiketju.id;");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        Integer viestienLukumaara = rs.getInt("viesteja");
        String viimeisinViesti = rs.getString("viimeisinViesti");
        Yhteenveto viestiketjunYhteenveto = new Yhteenveto(key, nimi, viestienLukumaara, viimeisinViesti);

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjunYhteenveto;

    }

    public List<Viestiketju> findThreadsOfArea(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viestiketju.id, Viestiketju.otsikko\n"
                + "FROM Viestiketju, Keskustelualue\n"
                + "WHERE Keskustelualue.id = ?\n"
                + "AND Viestiketju.keskustelualue = Keskustelualue.id;");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String otsikko = rs.getString("otsikko");
            viestiketjut.add(new Viestiketju(id, otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;

    }

    //tätä ei ehkä tarvita, emt
    public String findAreaKey(String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement(" \n"
                + "SELECT Viestiketju.keskustelualue\n"
                + "FROM Viestiketju\n"
                + "WHERE Viestiketju.id = ?\n"
                + "LIMIT 1;");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String keskustelualue = rs.getString("keskustelualue");


        rs.close();
        stmt.close();
        connection.close();

        return keskustelualue;

    }
}
