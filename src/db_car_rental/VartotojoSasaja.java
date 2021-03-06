package db_auto_nuoma;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.*;

import static java.lang.Float.parseFloat;

public class VartotojoSasaja {

    public static void init(Duombaze Baze) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String action = "";
        while (!action.equals("0")) {

            // Ieskoti Automobilio pagal Kebulo Nr.
            // Ieskoti Vartotojo pagal AK
            // Keisti Automobilio valst. nr.
            // Keisti Automobilio Savininka
            // Iregistruoti nauja Automobili
            // Iregistruoti nauja Vartotoja
            // Pasalinti Automobili
            // Pasalinti Vartotoja
            // Isregistruoti sena Nuoma ir iregistruoti nauja


            System.out.println("\n|------------------------------------------------------|");
            System.out.println("|         ______                                       |");
            System.out.println("|        /|_||_\\`.__                                   |");
            System.out.println("|       (   _    _ _\\                                  |");
            System.out.println("|       =`-(_)--(_)-'                                  |");
            System.out.println("|------------------------------------------------------|");

            System.out.println("-> Automobiliu nuomos duomenu baze (vartotojai, automobiliai, dalijimosi imones, autoservisai)");
            System.out.println("Veiksmai:");
            System.out.println("  1) Surasti automobili pagal kebulo nr.");
            System.out.println("  2) Surasti vartotoja pagal asmens koda");
            System.out.println("  3) Pakeisti Automobilio valstybini numeri");
            System.out.println("  4) Pakeisti Automobilio savininka");
            System.out.println("  5) Uzregistruoti nauja automobili");
            System.out.println("  6) Uzregistruoti nauja vartotoja");
            System.out.println("  7) Isregistruoti automobili");
            System.out.println("  8) Isregistruoti vartotoja");
            System.out.println("  9) Perkelti automobilio nuoma is vienos dalijimosi imones i kita");
            System.out.println("  0) Iseiti");
            System.out.println("Pasirinkite veiksma: ");
            action = in.readLine();

            try {
                if (action.equals("1")) {
                    System.out.println("\nAutomobilio paieska pagal kebulo nr.");
                    System.out.println("  Iveskite kebulo nr.:");
                    String kebulo_nr = in.readLine();
                    ResultSet result = Baze.searchAutoByVIN(kebulo_nr);
                    if (result == null) {
                        System.out.println("Toks automobilis nerastas!");
                    } else {
                        System.out.println("\nKebulo nr.: " + result.getString("Kebulo_nr"));
                        System.out.println("Valstybinis nr.: " + result.getString("Valst_nr"));
                        System.out.println("Gamybos metai: " + result.getInt("Gamybos_metai"));
                        System.out.println("Marke: " + result.getString("Marke"));
                        System.out.println("Modelis: " + result.getString("Modelis"));
                        System.out.println("Kuro sanaudos: " + result.getString("Kuro_sanaudos") + "l/100km");

                        String yra_ratas = result.getString("Atsarginis_ratas_yra");
                        yra_ratas = yra_ratas.equals("t") ? "Yra" : "Nera";
                        System.out.println("Atsarginis ratas: " + yra_ratas);

                        String yra_pagalves = result.getString("Saugos_pagalves_yra");
                        yra_pagalves = yra_pagalves.equals("t") ? "Yra" : "Nera";
                        System.out.println("Saugos pagalves: " + yra_pagalves);

                        System.out.println("Savininkas: " + result.getString("Savininkas"));

                        String autoservisas = result.getString("Autoservisas");
                        if (autoservisas == null) autoservisas = "Nera";
                        System.out.println("Autoservisas: " + autoservisas);
                    }
                }

                if (action.equals("2")) {
                    System.out.println("\nVartotojo paieska pagal asmens koda");
                    System.out.println("  Iveskite asmens koda:");
                    String asmens_kodas = in.readLine();
                    ResultSet result = Baze.searchZmogusByAK(asmens_kodas);
                    if (result == null) {
                        System.out.println("Toks vartotojas nerastas!");
                    } else {
                        System.out.println("\nAsmens kodas: " + result.getString("AK"));
                        System.out.println("Vardas: " + result.getString("Vardas"));
                        System.out.println("Pavarde: " + result.getString("Pavarde"));
                        System.out.println("Gimimas: " + result.getString("Gimimas"));
                        System.out.println("Telefono numeris: " + result.getString("Tel_nr"));
                        System.out.println("Menesio pajamos: " + result.getString("Menesio_pajamos"));

                        ResultSet result2 = Baze.searchZmogausAutos(asmens_kodas);
                        if (result2 == null) {
                            System.out.println("Vartotojui nepriklauso joks automobilis");
                        } else {
                            System.out.println("Priklausantys automobiliai:");
                            do {
                                System.out.println("  " + result2.getString("Kebulo_nr") + ", v.n. " + result2.getString("Valst_nr")
                                        + ", " + result2.getString("Marke") + " " + result2.getString("Modelis")
                                        + ", " + result2.getString("Kuro_sanaudos") + "l/100km, " + result2.getInt("Gamybos_metai") + " gamybos");
                            }
                            while (result2.next());
                        }
                    }
                }

                if (action.equals("3")) {
                    System.out.println("\nAutomobilio valstybinio nr. keitimas");
                    System.out.println("  Iveskite automobilio kebulo nr.:");
                    String kebulo_nr = in.readLine();
                    System.out.println("  Iveskite naujaji automobilio valstybini nr.:");
                    String valst_nr = in.readLine();
                    if (Baze.updateVN(valst_nr, kebulo_nr) == 0) {
                        System.out.println("Toks automobilis nerastas!");
                    } else {
                        System.out.println("Automobilio valstybinis nr. sekmingai pakeistas!");
                    }
                }
                if (action.equals("4")) {
                    System.out.println("\nAutomobilio savininko keitimas");
                    System.out.println("  Iveskite automobilio kebulo nr.:");
                    String kebulo_nr = in.readLine();
                    System.out.println("  Iveskite naujojo automobilio savininko asmens koda:");
                    String asmens_kodas = in.readLine();
                    if (Baze.updateSav(asmens_kodas, kebulo_nr) == 0) {
                        System.out.println("Toks automobilis nerastas!");
                    } else {
                        System.out.println("Automobilio savininkas sekmingai pakeistas!");
                    }
                }

                if (action.equals("5")) {
                    System.out.println("\nNaujo automobilio registracija");
                    System.out.println("  Iveskite kebulo nr.:");
                    String kebulo_nr = in.readLine();
                    System.out.println("  Iveskite valstybini nr.:");
                    String valst_nr = in.readLine();
                    System.out.println("  Iveskite gamybos metus:");
                    int gamybos_metai = Integer.parseInt(in.readLine());
                    System.out.println("  Iveskite marke:");
                    String marke = in.readLine();
                    System.out.println("  Iveskite modeli:");
                    String modelis = in.readLine();
                    System.out.println("  Iveskite kuro sanaudas:");
                    String kuro_sanaudos = in.readLine();


                    System.out.println("  Jei automobilis turi atsargini rata, iveskite '1':");
                    String input_atsarginis_ratas_yra = in.readLine();
                    boolean atsarginis_ratas_yra;

                    if (input_atsarginis_ratas_yra.equals("1")) {
                        atsarginis_ratas_yra = true;
                    } else {
                        atsarginis_ratas_yra = false;
                    }


                    System.out.println("  Jei automobilis turi saugos pagalves, iveskite '1':");
                    String input_saugos_pagalves_yra = in.readLine();
                    boolean saugos_pagalves_yra;

                    if (input_saugos_pagalves_yra.equals("1")) {
                        saugos_pagalves_yra = true;
                    } else {
                        saugos_pagalves_yra = false;
                    }

                    System.out.println("  Iveskite savininko asmens koda:");
                    String savininkas = in.readLine();

                    System.out.println("  Iveskite autoserviso imones koda: (Palikite tuscia (Enter), jei nera informacijos)");
                    String autoservisas = in.readLine();
                    if (autoservisas.equals("")) {
                        autoservisas = "NULL";
                    }

                    Baze.insertAuto(kebulo_nr, valst_nr, gamybos_metai, marke, modelis, kuro_sanaudos, atsarginis_ratas_yra, saugos_pagalves_yra, savininkas, autoservisas);
                    System.out.println("Naujas automobilis sekmingai uzregistruotas!");
                }

                if (action.equals("6")) {
                    System.out.println("\nNaujo vartotojo registracija");
                    System.out.println("  Iveskite asmens koda:");
                    String asmens_kodas = in.readLine();
                    System.out.println("  Iveskite varda:");
                    String vardas = in.readLine();
                    System.out.println("  Iveskite pavarde:");
                    String pavarde = in.readLine();
                    System.out.println("  Iveskite gimimo data:");
                    String gimimas = in.readLine();
                    System.out.println("  Iveskite telefono numeri:");
                    String tel_nr = in.readLine();

                    System.out.println("  Iveskite menesio pajamas: (Palikite tuscia (Enter), jei nera informacijos)");
                    String menesio_pajamos = in.readLine();

                    Baze.insertZmogus(asmens_kodas, vardas, pavarde, gimimas, tel_nr, menesio_pajamos);
                    System.out.println("Naujas zmogus sekmingai uzregistruotas!");
                }

                if (action.equals("7")) {
                    System.out.println("\nAutomobilio isregistravimas");
                    System.out.println("  Iveskite automobilio kebulo nr.:");
                    String kebulo_nr = in.readLine();
                    if (Baze.deleteAuto(kebulo_nr) == 0) {
                        System.out.println("Toks automobilis nerastas!");
                    } else {
                        System.out.println("Automobilis sekmingai isregistruotas!");
                    }
                }
                if (action.equals("8")) {
                    System.out.println("\nVartotojo isregistravimas");
                    System.out.println("  Iveskite vartotojo asmens koda:");
                    String asmens_kodas = in.readLine();
                    if (Baze.deleteZmogus(asmens_kodas) == 0) {
                        System.out.println("Toks zmogus nerastas!");
                    } else {
                        System.out.println("Zmogus sekmingai isregistruotas!");
                    }
                }


                if (action.equals("9")) {
                    System.out.println("\nAutomobilio nuomos perkelimas i kita dalijimosi imone (paslaugu atsisakymas ir uzsisakymas tu paciu paslaugu kitoje imoneje");
                    System.out.println("  Iveskite automobilio kebulo nr.:");
                    String kebulo_nr = in.readLine();

                    ResultSet result = Baze.showNuomAuto(kebulo_nr);

                    if (result == null) {
                        System.out.println("Toks automobilis nerastas!");
                    } else {
                        System.out.println("\nSarasas imoniu, kuriose sis automobilis nuomuojamas: ");
                        while (result.next()) {
                            System.out.println("\nEiles nr.: " + result.getInt("Eil_nr"));
                            System.out.println("Nuomuojancios imones kodas: " + result.getString("Imones_kodas"));
                        }
                    }

                    try {
                        Baze.con.setAutoCommit(false);

                        System.out.println("  Pazymekite imone, is kurios paslaugu yra atsisakoma (Iveskite iraso eiles numeri):");
                        int nr = Integer.parseInt(in.readLine());

                        if (Baze.deleteNuomAuto(nr) == 0) {
                            System.out.println("Toks irasas nerastas!");
                        } else {
                            System.out.println("Nuomos imoneje sekmingai atsisakyta!");
                        }

                        ResultSet result2 = Baze.showNuomAuto(kebulo_nr);

                        if (result2 == null) {
                            System.out.println("Automobilis siuo metu niekur nenuomuojamas!");
                        } else {
                            System.out.println("\nSarasas imoniu, kuriose sis automobilis nuomuojamas: ");
                            while (result2.next()) {
                                System.out.println("\nEiles nr.: " + result2.getInt("Eil_nr"));
                                System.out.println("Nuomuojancios imones kodas: " + result2.getString("Imones_kodas"));
                            }
                        }

                        System.out.println("  Iveskite koda kitos imones, kurios paslaugu yra uzsisakoma:");
                        String naujos_im_kodas = in.readLine();

                        Baze.insertNuomAuto(naujos_im_kodas, kebulo_nr);
                        System.out.println("Automobilio nuoma naujoje imoneje sekmingai uzregistruota!");


                        ResultSet result3 = Baze.showNuomAuto(kebulo_nr);

                        if (result3 == null) {
                            System.out.println("Automobilis siuo metu niekur nenuomuojamas!");
                        } else {
                            System.out.println("\nSarasas imoniu, kuriose sis automobilis nuomuojamas: ");
                            while (result3.next()) {
                                System.out.println("\nEiles nr.: " + result3.getInt("Eil_nr"));
                                System.out.println("Nuomuojancios imones kodas: " + result3.getString("Imones_kodas"));
                            }
                        }

                        Baze.con.commit();
                        Baze.con.setAutoCommit(true);
                    } catch (SQLException ex) {
                        System.out.println("Klaida vykdant transakcija: " + ex.getMessage());
                        System.out.println("\nAutomobilio perkelti i kita imone nepavyko");
                        Baze.con.rollback();
                        Baze.con.setAutoCommit(true);
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Klaida dirbant su duomenu baze: " + ex.getMessage());
            }
        }

        System.out.println("Darbo pabaiga");
    }
}

