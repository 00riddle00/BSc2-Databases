package db_auto_nuoma;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

// Ieskoti TP pagal VIN
// Ieskoti zmogu pagal asmens_koda
// Keisti TP valst. nr.
// Keisti TP savininka
// Iregistruoti nauja TP
// Iregistruoti zmogu
// Iregistruoti autoivyki
// Pasalinti TP
// Pasalinti zmogu

// Ieskoti Automobilio pagal Kebulo Nr.
// Ieskoti Vartotojo pagal AK
// Keisti Automobilio valst. nr.
// Keisti Automobilio Savininka
// Iregistruoti nauja Automobili
// Iregistruoti nauja Vartotoja
// Iregistruoti nauja Dalijimosi Imone
// Pasalinti Automobili
// Pasalinti Vartotoja
public class Duombaze {

    public Connection con = null;

    private String scheme;
    private PreparedStatement stmt_updateVN;
    private PreparedStatement stmt_updateSav;
    private PreparedStatement stmt_deleteAuto;
    private PreparedStatement stmt_deleteVartotojas;
    private PreparedStatement stmt_insertAuto;
    private PreparedStatement stmt_insertVartotojas;
    private PreparedStatement stmt_insertIvykis;
    private PreparedStatement stmt_deleteIvykis;

    public Duombaze(String scheme) {
        this.scheme = scheme;
    }

    public void initPreparedStatements() {
        try {
            this.stmt_updateVN = this.con.prepareStatement("UPDATE " + this.scheme + ".Automobilis SET Valst_nr = ? WHERE Kebulo_nr = ?");
            this.stmt_updateSav = this.con.prepareStatement("UPDATE " + this.scheme + ".Automobilis SET Savininkas = ? WHERE Kebulo_nr = ?");

            this.stmt_deleteAuto = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Automobilis WHERE Kebulo_nr = ?");
            this.stmt_deleteVartotojas = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Zmogus WHERE AK = ?");

            this.stmt_insertAuto = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Automobilis VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            this.stmt_insertVartotojas = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Vartotojas VALUES(?, ?, ?, ?, ?, ?)");

            this.stmt_insertIvykis = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Imonese_nuomuojami_automobiliai VALUES(DDEFAULT, ?, ?)");
            this.stmt_deleteIvykis = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Imonese_nuomuojami_automobiliai WHERE Eil_nr = ?");

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public void closePreparedStatements() {
        try {
            this.stmt_updateVN.close();
            this.stmt_updateSav.close();
            this.stmt_deleteAuto.close();
            this.stmt_deleteVartotojas.close();

            this.stmt_insertAuto.close();
            this.stmt_insertVartotojas.close();
            this.stmt_insertIvykis.close();
            this.stmt_deleteIvykis.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }


    public ResultSet searchAutoByVIN(String vin) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Automobilis WHERE Kebulo_nr = '" + vin + "'");
        if (result.next()) {
            return result;
        } else {
            return null;
        }
    }

    public ResultSet searchZmogusByAK(String AK) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Vartotojas WHERE AK = '" + AK + "'");
        if (result.next()) {
            return result;
        } else {
            return null;
        }
    }

    public ResultSet searchZmogausAutos(String AK) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Automobilis WHERE Savininkas = '" + AK + "'");
        if (result.next()) {
            return result;
        } else {
            return null;
        }
    }

    public int updateVN(String valst_nr, String vin) throws SQLException {
        this.stmt_updateVN.setString(1, valst_nr);
        this.stmt_updateVN.setString(2, vin);
        return this.stmt_updateVN.executeUpdate();
    }

    public int updateSav(String ak, String vin) throws SQLException {
        this.stmt_updateSav.setString(1, ak);
        this.stmt_updateSav.setString(2, vin);
        return this.stmt_updateSav.executeUpdate();
    }

    public int deleteAuto(String vin) throws SQLException {
        this.stmt_deleteAuto.setString(1, vin);
        return this.stmt_deleteAuto.executeUpdate();
    }

    public int deleteZmogus(String ak) throws SQLException {
        this.stmt_deleteVartotojas.setString(1, ak);
        return this.stmt_deleteVartotojas.executeUpdate();
    }

    public void insertAuto(String vin, String vn, String marke, String modelis, String spalva, int metai, String savininkas) throws SQLException {
        this.stmt_insertAuto.setString(1, vin);
        this.stmt_insertAuto.setString(2, vn);
        this.stmt_insertAuto.setString(3, marke);
        this.stmt_insertAuto.setString(4, spalva);
        this.stmt_insertAuto.setString(5, modelis);
        this.stmt_insertAuto.setInt(6, metai);
        this.stmt_insertAuto.setString(7, savininkas);
        this.stmt_insertAuto.executeUpdate();
    }

    public void insertZmogus(String asmens_kodas, String vardas, String pavarde) throws SQLException {
        this.stmt_insertVartotojas.setString(1, asmens_kodas);
        this.stmt_insertVartotojas.setString(2, vardas);
        this.stmt_insertVartotojas.setString(3, pavarde);
        this.stmt_insertVartotojas.executeUpdate();
    }

    public void insertIvykis(String vieta) throws SQLException {
        this.stmt_insertIvykis.setString(1, vieta);
        this.stmt_insertIvykis.executeUpdate();
    }




    public int getLastIvykis() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT MAX(Nr) AS last FROM " + this.scheme + ".Autoivykis");
        result.next();
        return result.getInt("last");
    }

    public void insertDalyvis(int nr, String vin, String asmens_kodas, boolean kaltas) throws SQLException {
        this.stmt_deleteIvykis.setInt(1, nr);
        this.stmt_deleteIvykis.setString(2, vin);
        this.stmt_deleteIvykis.setString(3, asmens_kodas);
        if (kaltas) {
            this.stmt_deleteIvykis.setInt(4, 1);
        } else {
            this.stmt_deleteIvykis.setInt(4, 0);
        }
        this.stmt_deleteIvykis.executeUpdate();
    }


}
