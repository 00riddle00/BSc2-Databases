package db_auto_nuoma;


import java.io.*;
import java.sql.*;

public class VartotojoSasaja
{

    public static void init(Duombaze db) {

    }
    public static void main(String[] args) throws Exception
    {
        try
        {
            DB Baze = new DB("studentu", "mano7777", "mano7777", "mano7777");

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String action = "";
            while (!action.equals("0"))
            {

                // Ieskoti TP pagal VIN
                // Ieskoti TP pagal valst. nr.
                // Ieskoti zmogu pagal asmens_koda
                // Keisti TP valst. nr.
                // Keisti TP spalva
                // Keisti TP savininka
                // Iregistruoti nauja TP
                // Iregistruoti zmogu
                // Iregistruoti autoivyki
                // Pasalinti TP
                // Pasalinti zmogu

                System.out.println("\n-------------------------------------------------------");
                System.out.println("-> Automobiliu, ju savininku ir autoivykiu duomenu baze");
                System.out.println("Veiksmai:");
                System.out.println("  1) Surasti Automobili pagal kebulo nr.");
                System.out.println("  2) Surasti Zmogu pagal asmens koda");
                System.out.println("  3) Pakeisti Automobilio valstybini numeri");
                System.out.println("  4) Pakeisti Automobilio savininka");
                System.out.println("  5) Uzregistruoti nauja Automobili");
                System.out.println("  6) Uzregistruoti nauja Zmogu");
                System.out.println("  7) Uzregistruoti Autoivyki");
                System.out.println("  8) Isregistruoti Automobili");
                System.out.println("  9) Isregistruoti Zmogu");
                System.out.println("  0) Iseiti");
                System.out.println("Pasirinkite veiksma: ");
                action = in.readLine();

                try
                {
                    if (action.equals("1"))
                    {
                        System.out.println("\nAutomobilio paieska pagal kebulo nr.");
                        System.out.println("  Iveskite kebulo nr.:");
                        String kebulo_nr = in.readLine();
                        ResultSet result = Baze.searchAutoByVIN(kebulo_nr);
                        if (result == null)
                        {
                            System.out.println("Toks automobilis nerastas!");
                        }
                        else
                        {
                            System.out.println("\nKebulo nr.: " + result.getString("Kebulo_nr"));
                            System.out.println("Valstybinis nr.: " + result.getString("Valst_nr"));
                            System.out.println("Marke: " + result.getString("marke"));
                            System.out.println("Modelis: " + result.getString("modelis"));
                            System.out.println("Spalva: " + result.getString("spalva"));
                            System.out.println("Pagaminimo metai: " + result.getInt("metai"));
                            System.out.println("Savininkas: " + result.getString("vardas") + " " + result.getString("pavarde") + ", " + result.getString("savininkas"));
                        }
                    }
                    if (action.equals("2"))
                    {
                        System.out.println("\nZmogaus paieska pagal asmens koda");
                        System.out.println("  Iveskite asmens koda:");
                        String asmens_kodas = in.readLine();
                        ResultSet result = Baze.searchZmogusByAK(asmens_kodas);
                        if (result == null)
                        {
                            System.out.println("Toks zmogus nerastas!");
                        }
                        else
                        {
                            System.out.println("\nAsmens kodas: " + result.getString("Asmens_kodas"));
                            System.out.println("Vardas: " + result.getString("vardas"));
                            System.out.println("Pavarde: " + result.getString("pavarde"));
                            System.out.println("Kiek autoivykiu sukele: " + result.getString("Sukele_ivykiu"));

                            ResultSet result2 = Baze.searchZmogausAutos(asmens_kodas);
                            if (result2 == null)
                            {
                                System.out.println("Zmogui nepriklauso joks automobilis");
                            }
                            else
                            {
                                System.out.println("Priklausantys automobiliai:");
                                do
                                {
                                    System.out.println("  " + result2.getString("Kebulo_nr") + ", v.n. " + result2.getString("Valst_nr")
                                            + ", " + result2.getString("marke") + " " + result2.getString("modelis")
                                            + ", " + result2.getString("spalva") + ", " + result2.getString("metai") + " gamybos");
                                }
                                while (result2.next());
                            }
                        }
                    }

                    if (action.equals("3"))
                    {
                        System.out.println("\nAutomobilio valstybinio nr. keitimas");
                        System.out.println("  Iveskite automobilio kebulo nr.:");
                        String kebulo_nr = in.readLine();
                        System.out.println("  Iveskite naujaji automobilio valstybini nr.:");
                        String valst_nr = in.readLine();
                        if (Baze.updateVN(valst_nr, kebulo_nr) == 0)
                        {
                            System.out.println("Toks automobilis nerastas!");
                        }
                        else
                        {
                            System.out.println("Automobilio valstybinis nr. sekmingai pakeistas!");
                        }
                    }
                    if (action.equals("4"))
                    {
                        System.out.println("\nAutomobilio savininko keitimas");
                        System.out.println("  Iveskite automobilio kebulo nr.:");
                        String kebulo_nr = in.readLine();
                        System.out.println("  Iveskite naujojo automobilio savininko asmens koda:");
                        String asmens_kodas = in.readLine();
                        if (Baze.updateSav(asmens_kodas, kebulo_nr) == 0)
                        {
                            System.out.println("Toks automobilis nerastas!");
                        }
                        else
                        {
                            System.out.println("Automobilio savininkas sekmingai pakeistas!");
                        }
                    }

                    if (action.equals("5"))
                    {
                        System.out.println("\nNaujo automobilio registracija");
                        System.out.println("  Iveskite kebulo nr.:");
                        String kebulo_nr = in.readLine();
                        System.out.println("  Iveskite valstybini nr.:");
                        String valst_nr = in.readLine();
                        System.out.println("  Iveskite marke:");
                        String marke = in.readLine();
                        System.out.println("  Iveskite modeli:");
                        String modelis = in.readLine();
                        System.out.println("  Iveskite spalva:");
                        String spalva = in.readLine();
                        System.out.println("  Iveskite pagaminimo metus:");
                        int metai = Integer.parseInt(in.readLine());
                        System.out.println("  Iveskite savininko asmens koda:");
                        String asmens_kodas = in.readLine();
                        Baze.insertAuto(kebulo_nr, valst_nr, marke, modelis, spalva, metai, asmens_kodas);
                        System.out.println("Naujas automobilis sekmingai uzregistruotas!");
                    }

                    if (action.equals("6"))
                    {
                        System.out.println("\nNaujo zmogaus registracija");
                        System.out.println("  Iveskite asmens koda:");
                        String asmens_kodas = in.readLine();
                        System.out.println("  Iveskite varda:");
                        String vardas = in.readLine();
                        System.out.println("  Iveskite pavarde:");
                        String pavarde = in.readLine();
                        Baze.insertZmogus(asmens_kodas, vardas, pavarde);
                        System.out.println("Naujas zmogus sekmingai uzregistruotas!");
                    }

                    if (action.equals("7"))
                    {
                        System.out.println("\nAutoivykio registracija");
                        System.out.println("  Iveskite vieta:");
                        String vieta = in.readLine();
                        try
                        {
                            Baze.con.setAutoCommit(false);

                            Baze.insertIvykis(vieta);
                            int nr = Baze.getLastIvykis();
                            System.out.println("  Iveskite ivykio dalyviu skaiciu:");
                            int cnt = Integer.parseInt(in.readLine());
                            for (int i = 1; i <= cnt; i++)
                            {
                                System.out.println("  Iveskite ivykio dalyvio #" + i + " asmens koda:");
                                String asmens_kodas = in.readLine();
                                System.out.println("  Iveskite ivykio dalyvio #" + i + " automobilio kebulo nr.:");
                                String kebulo_nr = in.readLine();
                                System.out.println("  Jei ivykio dalyvis #" + i + " yra kaltininkas, iveskite '1':");
                                String kaltas = in.readLine();
                                if (kaltas.equals("1"))
                                {
                                    Baze.insertDalyvis(nr, kebulo_nr, asmens_kodas, true);
                                }
                                else
                                {
                                    Baze.insertDalyvis(nr, kebulo_nr, asmens_kodas, false);
                                }
                            }

                            Baze.con.commit();
                            Baze.con.setAutoCommit(true);
                            System.out.println("Naujas autoivykis sekmingai uzregistruotas!");
                        }
                        catch (SQLException ex)
                        {
                            System.out.println("Klaida vykdant transakcija: " + ex.getMessage());
                            Baze.con.rollback();
                            Baze.con.setAutoCommit(true);
                        }
                    }

                    if (action.equals("8"))
                    {
                        System.out.println("\nAutomobilio isregistravimas");
                        System.out.println("  Iveskite automobilio kebulo nr.:");
                        String kebulo_nr = in.readLine();
                        if (Baze.deleteAuto(kebulo_nr) == 0)
                        {
                            System.out.println("Toks automobilis nerastas!");
                        }
                        else
                        {
                            System.out.println("Automobilis sekmingai isregistruotas!");
                        }
                    }
                    if (action.equals("9"))
                    {
                        System.out.println("\nZmogaus isregistravimas");
                        System.out.println("  Iveskite zmogaus asmens koda:");
                        String asmens_kodas = in.readLine();
                        if (Baze.deleteZmogus(asmens_kodas) == 0)
                        {
                            System.out.println("Toks zmogus nerastas!");
                        }
                        else
                        {
                            System.out.println("Zmogus sekmingai isregistruotas!");
                        }
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Klaida dirbant su duomenu baze: " + ex.getMessage());
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        System.out.println("Viso gero!");
    }
}

