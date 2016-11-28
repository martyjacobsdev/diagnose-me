package application;

import processor.DecisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marty on 26/11/2016.
 */
public class Model {

    private String symptom;

    //K = Symptom , V = Illness
    private HashMap<String, String> illnessLookupTable;


    public Model() {
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String process() {

        //reads the available data
        illnessLookupTable = readDataset();

        //checks for a possible illness match
        DecisionTree tree = new DecisionTree(symptom, illnessLookupTable);
        String outcome = tree.decideOutcome();

        return outcome;
    }

    /**
     * Reads the dataset and converts to a lookup table
     * Source: http://people.dbmi.columbia.edu/~friedma/Projects/DiseaseSymptomKB/index.html
     * @return readible K/V pairs (symptom to illnesses)
     */
    public HashMap<String, String> readDataset() {

        //reads and parses the possible illnesses
        //checks the input to see if it is valid
        HashMap<String, String> map = new HashMap<String, String>();
        String csvFile = "dataset_clean1.csv";
        String line = "";
        String del = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(del);

                map.put(data[1], data[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }



}
