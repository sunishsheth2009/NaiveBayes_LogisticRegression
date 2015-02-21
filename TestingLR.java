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
public class TestingLR {

    static int countHam = 0;
    static int countSpam = 0;

    static void testingLR(HashMap<String, Integer> testingTokens, HashMap<String, Double> weightVector) {
        double probability = weightVector.get("0");
        Set<String> iterator = testingTokens.keySet();
        for (String i : iterator) {
            probability = probability + (weightVector.get(i) * testingTokens.get(i));
        }

        if (probability >= 0) {
            countHam++;
        } else {
            //System.out.println("Spam");
            countSpam++;
        }
    }
}