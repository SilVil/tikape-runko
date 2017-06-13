package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Yhteenveto;

public class KeskustelualueDao implements Dao<Keskustelualue, String> {

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelualue create(Keskustelualue t) throws SQLException {

        Connection connection = database.getConnection();

        String id = t.getId();
        String nimi = t.getNimi();

        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Keskustelualue VALUES (?, ?)");

        statement.setString(1, id);
        statement.setString(2, nimi);

        statement.execute();

        Keskustelualue k = new Keskustelualue(id, nimi);

        statement.close();
        connection.close();

        return k;

    }

    @Override
    public Keskustelualue findOne(String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String id = rs.getString("id");
        String nimi = rs.getString("nimi");

        Keskustelualue k = new Keskustelualue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return k;

    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> keskustelualueet = new ArrayList<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String nimi = rs.getString("nimi");

            keskustelualueet.add(new Keskustelualue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueet;

    }

    public Yhteenveto findOnesMessageCountsAndLatest(String key, String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(Viesti.id) as viesteja,\n"
                + "	 IFNULL(MAX(Viesti.aika),'n/a') as viimeisinViesti\n"
                + "FROM Viesti, Viestiketju, Keskustelualue\n"
                + "WHERE Keskustelualue.id = ? \n"
                + "AND Viesti.viestiketju = Viestiketju.id\n"
                + "AND Viestiketju.keskustelualue = Keskustelualue.id;");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer viestienLukumaara = rs.getInt("viesteja");
        String viimeisinViesti = rs.getString("viimeisinViesti");
        Yhteenveto keskustelualueenYhteenveto = new Yhteenveto(key, nimi, viestienLukumaara, viimeisinViesti);

        rs.close();
        stmt.close();
        connection.close();

        return keskustelualueenYhteenveto;

    }

    public String findTitle(String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT Keskustelualue.nimi\n"
                + "FROM Keskustelualue\n"
                + "WHERE Keskustelualue.id = ?\n"
                + "LIMIT 1;");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String nimi = rs.getString("nimi");

        rs.close();
        stmt.close();
        connection.close();

        return nimi;

    }

}
