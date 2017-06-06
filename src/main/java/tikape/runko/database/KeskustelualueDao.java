package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;

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

}
