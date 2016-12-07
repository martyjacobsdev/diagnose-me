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

    //K = symptom V = Illness mapped to it's probability
    private HashMap<String, HashMap<String, Integer>> illnessProbability;

    //K = symptom , V = Illness
    private HashMap<String, List<String>> illnessLookupTable;

    public Model() {
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public List<String> process() {

        //reads the available data
        illnessLookupTable = readDataset();
        illnessProbability = readDatasetDeriveProbabilities();

        //checks for a possible illness match
        DecisionTree tree = new DecisionTree(symptom, illnessLookupTable, illnessProbability);

        List<String> outcome = tree.decideOutcomeBlindly();

        List<String> probabilisticOutcome = tree.decideOutcomeBasedOnProbability();

        //TODO: HERE - alternate between prob outcome / outcome

        return probabilisticOutcome;
    }


    /**
     * Reads a dataset and processes the probability of illness based on
     * age, gender, race etc. then returns it
     *
     * @return derived probability of each illness based on symptom
     */
    public HashMap<String, HashMap<String, Integer>> readDatasetDeriveProbabilities() {

        String result =
                restTemplate.getForObject(
                        "http://example.com/hotels/{hotel}/bookings/{booking}",
                        String.class,"42", "21"
                );


        //K = symptom V = illness mapped to it's probability
        HashMap<String, HashMap<String, Integer>> dataset = new HashMap();

        //read and store to dataset

        for (String symptom : illnessLookupTable.keySet()) {

            HashMap<String, Integer> map = new HashMap<>();

            for (String illness : illnessLookupTable.get(symptom)) {

                //TODO: Here change 0 to a certain probability
                //all illness have equal probability of occurring
                map.put(illness, 0);

                dataset.put(symptom, map);
            }
        }


        //TEST ONLY
        HashMap<String, Integer> result = dataset.get("fever");
        result.put("delirium", 30);
        result.put("infection", 20);
        result.put("influenza", 70);
        return dataset;
    }

    /**
     * Reads the dataset and converts to a lookup table
     * Source: http://people.dbmi.columbia.edu/~friedma/Projects/DiseaseSymptomKB/index.html
     *
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

                if (data[1] == "") {
                    continue;
                }

                if (!map.containsKey(data[1])) {

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
