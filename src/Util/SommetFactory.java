package Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SommetFactory {

    public static Map<Integer, Sommet> getDataFromDb() {
        Map<Integer, Sommet> sommets = new HashMap<>();
        int i_id = 0;
        int i_x = 1;
        int i_y = 2;
        int i_q = 3;
        try {
            FileReader fileReader = new FileReader("src/data/data01.txt");
            BufferedReader db = new BufferedReader(fileReader);
            String chaine;
            int i = 1;
            boolean depotInitialise = false;
            while((chaine = db.readLine())!= null)
            {
                if(i > 1)
                {
                    int id = 0;
                    int quantite = 0;
                    String[] tabChaine = chaine.split(";");
                    //Tu effectues tes traitements avec les données contenues dans le tableau
                    //La première information se trouve à l'indice 0
                    Coordonnee coordonnee = new Coordonnee();
                    for (int x = 0; x < tabChaine.length; x++) {
                        if (x == i_id) {
                            id = Integer.parseInt(tabChaine[x]);
                        } else if (x == i_x) {
                            coordonnee.setX(Integer.parseInt(tabChaine[x]));
                        } else if (x == i_y) {
                            coordonnee.setY(Integer.parseInt(tabChaine[x]));
                        } else if(x == i_q) {
                            quantite = Integer.parseInt(tabChaine[x]);
                        }
                    }
                    if (!depotInitialise) {
                        Depot depot = new Depot(coordonnee);
                        depotInitialise = true;
                        sommets.put(depot.getIdSommet(), depot);
                    } else {
                        Client client = new Client(id, coordonnee, quantite);
                        sommets.put(client.getIdSommet(), client);
                    }
                }
                i++;
            }
            db.close();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Le fichier est introuvable !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sommets;
    }
}
