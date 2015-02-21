/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.Set;


/**
 *
 * @author Sunish
 */
public class findProbility {
    
    static HashMap<String, Double> probability(HashMap<String, Integer> token, double TotalCount, double vocabCount){
        double denominator = TotalCount + vocabCount;
        HashMap<String, Double> probability = new HashMap<String, Double>();
        Set<String> iterator = token.keySet();
        for(String i : iterator){
            double wordCount = token.get(i) + 1;
            double probab = wordCount / denominator;
            probability.put(i, probab);
        }
        return probability;
    }
}
