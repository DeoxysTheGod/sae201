package fr.g1b.sae201;

import java.io.FileFilter;
import java.io.FilterReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * La classe qui permet de récupérer les données du CSV, de les stocker et de les exploiter
 */
public class DataGetter {

    private List<String[]> dataset = new ArrayList<>();

    /**
     * @param csvFilePath Chemin qui mène vers le CSV choisi
     */
    public DataGetter(String csvFilePath) {
        String csvFile = csvFilePath;
        String[] line;
        String csvSplitBy = ",";

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            // Lecture des lignes du fichier CSV
            while ((line = reader.readNext()) != null) {
                // Ajout des données dans la liste
                dataset.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return les données sous forme de List<String[]>
     */
    public List<String[]> getDataset() {
        return dataset;
    }

    /**
     * Filtrage des données en fonction de ceux fournis en paramètre
     *
     * @param filter Une String avec un formatage spécial afin de dire quel filtre appliquer et comment
     * @return la base de données avec les filtres appliqués
     */
    public List<String[]> applyFilter(String filter) {
        String[] separatedFilter = filter.split(",");
        List<String[]> filteredDataset = getDataset().stream()
                .skip(1)
                .filter((element) -> {
                    if (!separatedFilter[0].matches("none")) {
                        int dateIndex = DataGetter.findIndexColumnWithColumnName("Date", dataset);
                        String[] value = separatedFilter[0].split("-");
                        String[] date = element[dateIndex].split("/");
                        return ((Integer.parseInt(date[0]) <= Integer.parseInt(value[1]))
                                && (Integer.parseInt(date[0]) >= Integer.parseInt(value[0])));
                    } else {
                        return true;
                    }
                })
                .filter((element) -> {
                    if (!separatedFilter[1].matches("none")) {
                        int dateIndex = DataGetter.findIndexColumnWithColumnName("Date", dataset);
                        return element[dateIndex].equals(separatedFilter[1]);
                    } else {
                        return true;
                    }
                })
                .filter((element) -> {
                    if (!separatedFilter[2].matches("none")) {
                        int dateIndex = DataGetter.findIndexColumnWithColumnName("Région", dataset);
                        return element[dateIndex].toLowerCase().trim().contains(separatedFilter[2].toLowerCase().trim());
                    } else return true;
                })
                .collect(Collectors.toList());

        filteredDataset.add(0, getDataset().get(0));

        return filteredDataset;
    }

    /**
     * Recherche l'index de la colonne qu'on recherche avec son nom
     *
     * @param columnName Le nom de la colonne que l'on veut chercher
     * @param dataset La base de données dans laquel, il faut chercher
     * @return l'index de la colonne correspondante, -1 si elle n'a pas été trouvé
     */
    public static int findIndexColumnWithColumnName(String columnName, List<String[]> dataset) {
        for (int i = 0; i < dataset.get(0).length; i++) {
            if (dataset.get(0)[i].toLowerCase().trim().contains(columnName.toLowerCase().trim())) {
                return i;
            }
        }
        return -1;
    }

}
