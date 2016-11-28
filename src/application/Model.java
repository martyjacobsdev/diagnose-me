package application;

import processor.DecisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marty on 26/11/2016.
 */
public class Model {

    private String symptom;

    //K = Symptom , V = Illness
    private HashMap<String, List<String>> illnessLookupTable;

    public Model() {
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public List<String> process() {

        //reads the available data
        illnessLookupTable = readDataset();

        //checks for a possible illness match
        DecisionTree tree = new DecisionTree(symptom, illnessLookupTable);
        List<String> outcome = tree.decideOutcome();

        return outcome;
    }

    /**
     * Reads the dataset and converts to a lookup table
     * Source: http://people.dbmi.columbia.edu/~friedma/Projects/DiseaseSymptomKB/index.html
     * @return readible K/V pairs (symptom to illnesses)
     */
    public HashMap<String, List<String>> readDataset() {

        //reads and parses the possible illnesses
        //checks the input to see if it is valid
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String csvFile = "dataset.csv";
        String line = "";
        String del = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            List<String> illnesses = new ArrayList<String>();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(del);

                if(data[1] == "") {
                    continue;
                }
                if(!map.containsKey(data[1])) {

                    illnesses = new ArrayList<String>();
                    illnesses.add(data[0]);
                    map.put(data[1], illnesses);

                } else {

                    List<String> result = map.get(data[1]);
                    result.add(data[0]);
                    map.put(data[1], result);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }



}
