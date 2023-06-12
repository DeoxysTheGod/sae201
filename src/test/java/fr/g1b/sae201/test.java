package fr.g1b.sae201;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class test {
    
    // Fichier CSV à tester
    String csvFilePath = ("src/main/resources/fr/g1b/sae201/seismes.csv");
    
    @Test
    public void testCsvContientColonnes() throws Exception {

        // Les colonnes attendues
        String[] expectedColumns = {"Identifiant","Date (AAAA/MM/JJ)","Heure","Nom","Région épicentrale","Choc","X RGF93/L93","Y RGF93/L93","Latitude en WGS 84","Longitude en WGS 84","Intensité épicentrale"};

        // Lecture du CSV avec OpenCSV
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            // Lecture du titre de chaque colonne
            String[] headerRow = reader.readNext();

            // On vérifie que le titre de la colonne n'est pas vide
            assertTrue("Le titre de la colonne CSV est vide", headerRow != null);

            // Vérifie que le fichier CSV contient bien les titres des données requises
            for (String expectedColumn : expectedColumns) {
                assertTrue("Le fichier CSV ne contient pas la colonne: " + expectedColumn,
                        Arrays.asList(headerRow).contains(expectedColumn));
            }
        }
    }
    
    /**@Test
    public void testFiltres() throws Exception {
        DataGetter test = new DataGetter(csvFilePath);
        List<String[]> filteredDataset = test.applyFilter("Date");
        System.out.println(filteredDataset);
        Assertions.assertNotNull(filteredDataset);
        Assertions.assertTrue(filteredDataset.isEmpty());
        
    }**/
}

