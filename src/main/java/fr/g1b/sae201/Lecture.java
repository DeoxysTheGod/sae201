package fr.g1b.sae201;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lecture {
    public static void main(String[] args) {
        String csvFile = "src/main/resources/fr/g1b/sae201/seismes.csv";
        String line;
        String csvSplitBy = ",";

        List<String> col1 = new ArrayList<>();
        List<String> col2 = new ArrayList<>();
        List<String> col3 = new ArrayList<>();
        List<String> col4 = new ArrayList<>();
        List<String> col5 = new ArrayList<>();
        List<String> col6 = new ArrayList<>();
        List<String> col7 = new ArrayList<>();
        List<String> col8 = new ArrayList<>();
        List<String> col9 = new ArrayList<>();
        List<String> col10 = new ArrayList<>();
        List<String> col11 = new ArrayList<>();
        List<String> col12 = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                col1.add(data[0]);
                col2.add(data[1]);
                col3.add(data[2]);
                col4.add(data[3]);
                col5.add(data[4]);
                col6.add(data[5]);
                col7.add(data[6]);
                col8.add(data[7]);
                col9.add(data[8]);
                col10.add(data[9]);
                col11.add(data[10]);
                col12.add(data[11]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(col1);

        // Maintenant, vous avez vos données CSV dans 12 listes distinctes (tableaux dynamiques)
    }
}

// Identifiant,
// "Date (AAAA/MM/JJ)"
// ,Heure,
// Nom,
// "Région épicentrale",
// Choc,
// "X RGF93/L93",
// "Y RGF93/L93",
// "Latitude en WGS 84",
// "Longitude en WGS 84",
// "Intensité épicentrale",
// "Qualité intensité épicentrale"