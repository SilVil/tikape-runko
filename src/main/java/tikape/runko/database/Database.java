package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        String ukrainaId = UUID.randomUUID().toString().substring(0, 4);

        String jenkemId = UUID.randomUUID().toString().substring(0, 4);
        String lyricatId = UUID.randomUUID().toString().substring(0, 4);

        String viestiId1 = UUID.randomUUID().toString().substring(0, 4);
        String viestiId2 = UUID.randomUUID().toString().substring(0, 4);

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Keskustelualue"
                + "(id varchar(5) PRIMARY KEY, "
                + "nimi varchar(50)"
                + ");");
        lista.add("CREATE TABLE Viestiketju "
                + "(id varchar(5) PRIMARY KEY, "
                + "otsikko varchar(100),"
                + "keskustelualue varchar(5),"
                + "FOREIGN KEY(keskustelualue) REFERENCES Keskustelualue(id)"
                + ");");
        lista.add("CREATE TABLE Viesti "
                + "(id varchar(5) PRIMARY KEY, "
                + "nimimerkki varchar(100),"
                + "teksti varchar(5000),"
                + "aika text,"
                + "viestiketju varchar(5),"
                + "FOREIGN KEY(viestiketju) REFERENCES Viestiketju(id)"
                + ");");

        lista.add("INSERT INTO Keskustelualue (id,nimi) VALUES ('" + ukrainaId + "', 'Ukraina');");
        lista.add("INSERT INTO Keskustelualue (id,nimi) VALUES ('" + UUID.randomUUID().toString().substring(0, 4) + "', 'Erectus');");
        lista.add("INSERT INTO Keskustelualue (id,nimi) VALUES ('" + UUID.randomUUID().toString().substring(0, 4) + "', 'Se syvenee syksyllä');");

        lista.add("INSERT INTO Viestiketju (id, otsikko,keskustelualue) VALUES ('" + jenkemId + "', 'Jenkem', '" + ukrainaId + "');");
        lista.add("INSERT INTO Viestiketju (id, otsikko,keskustelualue) VALUES ('" + lyricatId + "', 'Lyricat', '" + ukrainaId + "');");
        
        lista.add("INSERT INTO Viesti (id, nimimerkki, teksti, viestiketju, aika) VALUES ('" + viestiId1 + "', 'pyhimys', 'nisti ku nisti eskapisti jotain paos', '" + jenkemId + "', datetime('now', 'localtime'));");
        lista.add("INSERT INTO Viesti (id, nimimerkki, teksti, viestiketju, aika) VALUES ('" + viestiId2 + "', 'pyhimys', 'lyricat nappaa, lyriikat nappaa', '" + lyricatId + "', datetime('now', 'localtime'));");
        return lista;
    }
}
