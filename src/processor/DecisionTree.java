package processor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marty on 26/11/2016.
 */
public class DecisionTree {

    private String symptom;
    private HashMap<String, List<String>> illnessLookupTable;

    //constructor - this will either take a map/list of data to construct the tree
    public DecisionTree(String symptom, HashMap<String, List<String>> illnessLookupTable) {
        this.symptom = symptom;
        this.illnessLookupTable = illnessLookupTable;
    }

    //constructs the tree - decides the outcome
    public List<String> decideOutcome() {

        //TODO: fix this later
        illnessLookupTable.remove("");
        return illnessLookupTable.get(symptom);
    }

}
