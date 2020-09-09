package db_auto_nuoma;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

// Ieskoti Automobilio pagal Kebulo Nr.
// Ieskoti Vartotojo pagal AK
// Keisti Automobilio valst. nr.
// Keisti Automobilio Savininka
// Iregistruoti nauja Automobili
// Iregistruoti nauja Vartotoja
// Pasalinti Automobili
// Pasalinti Vartotoja
// Isregistruoti sena Nuoma ir iregistruoti nauja
public class Duombaze {

    public Connection con;
    private String scheme;

    private PreparedStatement stmt_updateVN;
    private PreparedStatement stmt_updateSav;
    private PreparedStatement stmt_deleteAuto;
    private PreparedStatement stmt_deleteVartotojas;
    private PreparedStatement stmt_insertAuto;
    private PreparedStatement stmt_insertVartotojas;
    private PreparedStatement stmt_deleteNuomAuto;
    private PreparedStatement stmt_insertNuomAuto;

    public Duombaze(Connection con, String scheme) {
        this.con = con;
        this.scheme = scheme;
    }

    public void initPreparedStatements() {
        try {
            this.stmt_updateVN = this.con.prepareStatement("UPDATE " + this.scheme + ".Automobilis SET Valst_nr = ? WHERE Kebulo_nr = ?");
            this.stmt_updateSav = this.con.prepareStatement("UPDATE " + this.scheme + ".Automobilis SET Savininkas = ? WHERE Kebulo_nr = ?");

            this.stmt_deleteAuto = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Automobilis WHERE Kebulo_nr = ?");
            this.stmt_deleteVartotojas = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Vartotojas WHERE AK = ?");

            this.stmt_insertAuto = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Automobilis VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            this.stmt_insertVartotojas = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Vartotojas VALUES(?, ?, ?, ?, ?, ?)");

            this.stmt_deleteNuomAuto = this.con.prepareStatement("DELETE FROM " + this.scheme + ".Imonese_nuomuojami_automobiliai WHERE Eil_nr = ?");
            this.stmt_insertNuomAuto = this.con.prepareStatement("INSERT INTO " + this.scheme + ".Imonese_nuomuojami_automobiliai VALUES(DEFAULT, ?, ?)");

        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception exc) {
            System.out.println(exc);
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
            this.stmt_deleteNuomAuto.close();
            this.stmt_insertNuomAuto.close();

        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }


    public ResultSet searchAutoByVIN(String vin) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Automobilis WHERE Kebulo_nr = '" + vin + "'");
            if (result.next()) {
                return result;
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        } catch (Exception exc) {
            System.out.println(exc);
            return null;
        }
    }

    public ResultSet searchZmogusByAK(String AK) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Vartotojas WHERE AK = '" + AK + "'");
            if (result.next()) {
                return result;
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        } catch (Exception exc) {
            System.out.println(exc);
            return null;
        }
    }

    public ResultSet searchZmogausAutos(String AK) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Automobilis WHERE Savininkas = '" + AK + "'");
            if (result.next()) {
                return result;
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        } catch (Exception exc) {
            System.out.println(exc);
            return null;
        }
    }

    public int updateVN(String valst_nr, String vin) throws SQLException {
        try {
            this.stmt_updateVN.setString(1, valst_nr);
            this.stmt_updateVN.setString(2, vin);
            return this.stmt_updateVN.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return 0;
        } catch (Exception exc) {
            System.out.println(exc);
            return 0;
        }
    }

    public int updateSav(String ak, String vin) throws SQLException {
        try {
            this.stmt_updateSav.setString(1, ak);
            this.stmt_updateSav.setString(2, vin);
            return this.stmt_updateSav.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return 0;
        } catch (Exception exc) {
            System.out.println(exc);
            return 0;
        }
    }

    public int deleteAuto(String vin) throws SQLException {
        try {
            this.stmt_deleteAuto.setString(1, vin);
            return this.stmt_deleteAuto.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return 0;
        } catch (Exception exc) {
            System.out.println(exc);
            return 0;
        }
    }

    public int deleteZmogus(String ak) throws SQLException {
        try {
            this.stmt_deleteVartotojas.setString(1, ak);
            return this.stmt_deleteVartotojas.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return 0;
        } catch (Exception exc) {
            System.out.println(exc);
            return 0;
        }
    }

    public void insertAuto(String vin, String vn, int gamybos_metai, String marke, String modelis, String kuro_sanaudos, boolean atsarginis_ratas_yra, boolean saugos_pagalves_yra, String savininkas, String autoservisas) throws
            SQLException {
        try {
            this.stmt_insertAuto.setString(1, vin);
            this.stmt_insertAuto.setString(2, vn);
            this.stmt_insertAuto.setInt(3, gamybos_metai);
            this.stmt_insertAuto.setString(4, marke);
            this.stmt_insertAuto.setString(5, modelis);
            this.stmt_insertAuto.setString(6, kuro_sanaudos);
            this.stmt_insertAuto.setBoolean(7, atsarginis_ratas_yra);
            this.stmt_insertAuto.setBoolean(8, saugos_pagalves_yra);
            this.stmt_insertAuto.setString(9, savininkas);
            this.stmt_insertAuto.setString(10, autoservisas);
            this.stmt_insertAuto.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public void insertZmogus(String asmens_kodas, String vardas, String pavarde, String gimimas, String tel_nr, String menesio_pajamos) throws SQLException {
        try {
            this.stmt_insertVartotojas.setString(1, asmens_kodas);
            this.stmt_insertVartotojas.setString(2, vardas);
            this.stmt_insertVartotojas.setString(3, pavarde);
            this.stmt_insertVartotojas.setString(4, gimimas);
            this.stmt_insertVartotojas.setString(5, tel_nr);
            this.stmt_insertVartotojas.setString(6, menesio_pajamos);

            this.stmt_insertVartotojas.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public ResultSet showNuomAuto(String VN) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + this.scheme + ".Imonese_nuomuojami_automobiliai WHERE Automobilio_keb_nr = '" + VN + "'");
            return result;

        } catch (SQLException sqle) {
            System.out.println(sqle);
            return null;
        } catch (Exception exc) {
            System.out.println(exc);
            return null;
        }
    }

    public int deleteNuomAuto(int nr) throws SQLException {
        try {
            this.stmt_deleteNuomAuto.setInt(1, nr);
            return this.stmt_deleteNuomAuto.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
            return 0;
        } catch (Exception exc) {
            System.out.println(exc);
            return 0;
        }
    }

    public void insertNuomAuto(String imones_kodas, String VN) throws SQLException {
        try {
            this.stmt_insertNuomAuto.setString(1, imones_kodas);
            this.stmt_insertNuomAuto.setString(2, VN);
            this.stmt_insertNuomAuto.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

}
