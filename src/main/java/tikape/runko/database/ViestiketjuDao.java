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
}


