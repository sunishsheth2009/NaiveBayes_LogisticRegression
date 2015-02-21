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
public class Testing {

    static int countHam = 0;
    static int countSpam = 0;

    static void testing(HashMap<String, Double> probabHam, HashMap<String, Integer> ham, double probHam, HashMap<String, Double> probabSpam, HashMap<String, Integer> spam, double probSpam, HashMap<String, Integer> testingTokens, double TotalHam, double TotalSpam, double VocabCount) {

        // Calculating for Ham
        double scoreHam = (double) Math.log(probHam) / Math.log(2);
        Set<String> iteratorForHam = testingTokens.keySet();
        for (String i : iteratorForHam) {
            if (ham.containsKey(i)) {
                double count = testingTokens.get(i);
                while (count > 0) {
                    scoreHam = scoreHam + Math.log(probabHam.get(i)) / Math.log(2);
                    count--;
                }
            } else {
                double count = testingTokens.get(i);
                while (count > 0) {
                    double x = (1) / (TotalHam + VocabCount);
                    scoreHam = scoreHam + Math.log(x) / Math.log(2);
                    count--;
                }
            }
        }



        double scoreSpam = (double) Math.log(probSpam) / Math.log(2);
        Set<String> iteratorForSpam = testingTokens.keySet();
        for (String i : iteratorForSpam) {
            if (spam.containsKey(i)) {
                double count = testingTokens.get(i);
                while (count > 0) {
                    scoreSpam = scoreSpam + Math.log(probabSpam.get(i)) / Math.log(2);
                    count--;
                }
            } else {
                double count = testingTokens.get(i);
                while (count > 0) {
                    double x = (1) / (TotalSpam + VocabCount);
                    scoreSpam = scoreSpam + Math.log(x) / Math.log(2);
                    count--;
                }
            }
        }

        if (scoreHam >= scoreSpam) {
            countHam++;
        } else {
            countSpam++;
        }


    }
}
