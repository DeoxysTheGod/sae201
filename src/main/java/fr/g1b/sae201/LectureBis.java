package fr.g1b.sae201;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class LectureBis {

    private ArrayList<String[]> dataset = new ArrayList<>();

    public LectureBis(String csvFilePath) {
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

    public ArrayList<String[]> getDataset() {
        return dataset;
    }
}
