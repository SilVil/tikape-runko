/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author ville
 */
public class ViestiDao implements Dao<Viesti, String> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti create(Viesti t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Viesti findOne(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Viesti> findMessagesOfThread(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.*\n"
                + "FROM Viesti, Viestiketju\n"
                + "WHERE Viestiketju.id = ?\n"
                + "AND Viesti.viestiketju = Viestiketju.id\n"
                + "ORDER BY date(Viesti.aika) ASC;");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            viestit.add(new Viesti(rs.getString("id"),
                    rs.getString("nimimerkki"),
                    rs.getString("aika"),
                    rs.getString("teksti"),
                    rs.getString("viestiketju")));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }

}
