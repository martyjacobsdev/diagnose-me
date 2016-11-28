package processor;

import java.util.HashMap;

/**
 * Created by Marty on 26/11/2016.
 */
public class DecisionTree {

    private String symptom;
    private HashMap<String, String> illnessLookupTable;

    //constructor - this will either take a map/list of data to construct the tree
    public DecisionTree(String symptom, HashMap<String, String> illnessLookupTable) {
        this.symptom = symptom;
        this.illnessLookupTable = illnessLookupTable;
    }

    //constructs the tree - decides the outcome
    public String decideOutcome() {


        return null;
    }



}
