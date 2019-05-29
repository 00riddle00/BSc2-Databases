package db_auto_nuoma;

/**
 * @author Tomas Giedraitis 2k. 2gr. MIF VU INFO 2019
 */

import java.io.IOException;
import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;
import java.util.Properties;

public class AutoNuoma {

    /********************************************************/
    public static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Couldn't find driver class!");
            cnfe.printStackTrace();
            System.exit(1);
        }
    }

    /********************************************************/
    public static Connection getConnection() {



        Connection postGresConn = null;
        try {
//            String url = "jdbc:postgresql:/pgsql3.mif/studentu";
//            Properties props = new Properties();
//            props.setProperty("user","togi3017");
//            props.setProperty("password","asdfjkl8gb");
//            props.setProperty("ssl","true");

//            postGresConn = DriverManager.getConnection(url, props);

            postGresConn = DriverManager.getConnection("jdbc:postgresql:/pgsql3.mif/studentu", "togi3017", "asdfjkl8gb");

        } catch (SQLException sqle) {
            System.out.println("Couldn't connect to database!");
            sqle.printStackTrace();
            return null;
        }
        System.out.println("Successfully connected to Postgres Database");

        return postGresConn;
    }


    /********************************************************/
    public static void main(String[] args) {
        loadDriver();
        Connection con = getConnection();

        if (null != con) {
            Duombaze db = new Duombaze("togi3017");
            db.initPreparedStatements();

            try {
                VartotojoSasaja.init(db);
            } catch (IOException exp) {
                System.out.println("IO Error!");
                exp.printStackTrace();
            }

            db.closePreparedStatements();
        }

        if (null != con) {
            try {
                con.close();
            } catch (SQLException exp) {
                System.out.println("Can not close connection!");
                exp.printStackTrace();
            }
        }
    }
    /********************************************************/
}






