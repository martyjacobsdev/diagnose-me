package processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marty on 26/11/2016.
 */
public class DecisionTree {

    private String symptom;
    private HashMap<String, HashMap<String, Integer>> illnessProbability;
    private HashMap<String, List<String>> illnessLookupTable;

    //constructor - this will either take a map/list of data to construct the tree
    public DecisionTree(String symptom, HashMap<String, List<String>> illnessLookupTable,
                        HashMap<String, HashMap<String, Integer>> illnessProbability) {

        this.symptom = symptom;
        this.illnessLookupTable = illnessLookupTable;
        this.illnessProbability = illnessProbability;
        illnessLookupTable.remove("");
    }

    //blindly selects illnesses
    public List<String> decideOutcomeBlindly() {
        return illnessLookupTable.get(symptom);
    }


    //selects illnesses based on probability of them occurring
    public List<String> decideOutcomeBasedOnProbability() {

        List<String> threeMostProbable = new ArrayList<String>();

        HashMap<String, Integer> possibleIllnesses = illnessProbability.get(symptom);

        List<Integer> values = new ArrayList<Integer>();

        if(possibleIllnesses == null) {
            return null;
        }
        for (String illness : possibleIllnesses.keySet()) {
            int result = possibleIllnesses.get(illness);
            values.add(result);
        }

        Collections.sort(values);

        int val1 = values.get(values.size() - 1);
        int val2 = values.get(values.size() - 2);
        int val3 = values.get(values.size() - 3);

        for (String key : possibleIllnesses.keySet()) {

            if (threeMostProbable.size() == 3) break;

            if (val1 == val2 && val2 == val3) {
                //highest 3 values are the same - choose illness at random
                threeMostProbable.add(key);
                continue;
            } //otherwise
            if (possibleIllnesses.get(key) == val1 && val1 != 0) {
                threeMostProbable.add(key);
            } else if (possibleIllnesses.get(key) == val2 && val2 != 0) {
                threeMostProbable.add(key);
            } else if (possibleIllnesses.get(key) == val3 && val3 != 0) {
                threeMostProbable.add(key);
            }
        }


        for (int j = 0; j < threeMostProbable.size(); j++) {
            threeMostProbable.set(j, "\n" + threeMostProbable.get(j) +"\n" + "at chance: " +
                    possibleIllnesses.get(threeMostProbable.get(j)) + "%");
        }

        return threeMostProbable;
    }
}
