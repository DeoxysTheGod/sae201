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

public class DataGetter {

    private List<String[]> dataset = new ArrayList<>();

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

    public List<String[]> getDataset() {
        return dataset;
    }

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

    public static int findIndexColumnWithColumnName(String columnName, List<String[]> dataset) {
        for (int i = 0; i < dataset.get(0).length; i++) {
            if (dataset.get(0)[i].toLowerCase().trim().contains(columnName.toLowerCase().trim())) {
                return i;
            }
        }
        return -1;
    }

}
