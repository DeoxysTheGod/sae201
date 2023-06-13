package fr.g1b.sae201;

import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    // Fichier CSV à tester
    String csvFilePath = ("src/main/resources/fr/g1b/sae201/seismes.csv");

    @Test
    public void testCsvContientColonnes() throws Exception {

        // Les colonnes attendues
        String[] expectedColumns = {"Identifiant", "Date (AAAA/MM/JJ)", "Heure", "Nom", "Région épicentrale", "Choc", "X RGF93/L93", "Y RGF93/L93", "Latitude en WGS 84", "Longitude en WGS 84", "Intensité épicentrale"};

        // Lecture du CSV avec OpenCSV
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            // Lecture du titre de chaque colonne
            String[] headerRow = reader.readNext();

            // On vérifie que le titre de la colonne n'est pas vide
            assertTrue(headerRow != null);

            // Vérifie que le fichier CSV contient bien les titres des données requises
            if (headerRow.length == expectedColumns.length) {
                for (int i = 0; i < headerRow.length; i++) {
                    assertEquals(headerRow[i], expectedColumns[i]);
                }
            }
        }
    }

    @Test
    public void testFiltreEntreDeuxDate() {
        DataGetter dataset = new DataGetter("src/main/resources/fr/g1b/sae201/seismesTest.csv");
        DataGetter expectedDataset = new DataGetter("src/main/resources/fr/g1b/sae201/seismesTestFiltreDate.csv");

        List<String[]> filteredDataset = dataset.applyFilter("1962-1999,none,none");

        assertEquals(expectedDataset.getDataset().size(), filteredDataset.size());

        int index = DataGetter.findIndexColumnWithColumnName("Date", dataset.getDataset());

        for (int i = 0; i < expectedDataset.getDataset().size(); i++) {
            assertEquals(expectedDataset.getDataset().get(i)[index], filteredDataset.get(i)[index]);
        }

    }
}

