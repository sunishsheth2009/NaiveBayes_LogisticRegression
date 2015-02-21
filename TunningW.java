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
public class TunningW {

//    static double eita = 0.001;
//    static double lamda = 10;
//    static int no_of_iteration = 500;

    static HashMap<String, Double> tunning(HashMap<Integer, HashMap<String, Integer>> spamLR, HashMap<Integer, HashMap<String, Integer>> hamLR, HashMap<String, Double> weightVector, Set<String> Vocab, double eita, double lamda, double no_of_iteration) {

        HashMap<Integer, Double> ProbabilityHam = new HashMap<Integer, Double>();
        HashMap<Integer, Double> ProbabilitySpam = new HashMap<Integer, Double>();

        for (int run = 0; run < no_of_iteration; run++) {
            HashMap<String, Double> Summation = new HashMap<String, Double>();
            for (String v : Vocab) {
                Summation.put(v, Double.valueOf(0.0));
            }
            Summation.put("0", Double.valueOf(0.0));
            Set<Integer> iteratorHam = hamLR.keySet();
            for (Integer iterate : iteratorHam) {
                HashMap<String, Integer> rowHam = new HashMap<String, Integer>();
                rowHam.putAll(hamLR.get(iterate));
                double probSum = weightVector.get("0");
                Set<String> rowHamIterator = rowHam.keySet();
                for (String i : rowHamIterator) {
                    probSum = probSum + (weightVector.get(i) * (rowHam.get(i)));
                }
                double exp = 1 / (1 + Math.exp(-probSum));
                probSum = 1 - exp;
                ProbabilityHam.put(iterate, probSum);
                for (String i : rowHamIterator) {
                    double x = rowHam.get(i) * ProbabilityHam.get(iterate);
                    double value = Summation.get(i);
                    value = value + x;
                    Summation.put(i, new Double(value));
                    //Sum = Sum + (rowHam.get(i) * (1 - probSum));
                }
            }

            Set<Integer> iteratorSpam = spamLR.keySet();
            for (Integer iterate : iteratorSpam) {
                HashMap<String, Integer> rowSpam = new HashMap<String, Integer>();
                rowSpam.putAll(spamLR.get(iterate));
                double probSum = weightVector.get("0");
                Set<String> rowSpamIterator = rowSpam.keySet();
                for (String i : rowSpamIterator) {
                    probSum = probSum + (weightVector.get(i) * (rowSpam.get(i)));
                }
                double exp = 1 / (1 + Math.exp(-probSum));
                probSum = 0 - exp;
                ProbabilitySpam.put(iterate, probSum);
                for (String i : rowSpamIterator) {
                    double x = rowSpam.get(i) * ProbabilitySpam.get(iterate);
                    double value = Summation.get(i);
                    value = value + x;
                    Summation.put(i, new Double(value));
                    //Sum = Sum + (rowSpam.get(i) * (0 - probSum));
                }
            }

            Set<String> weightVectorIterator = weightVector.keySet();
            double partial = 0;
//            String a = "Weights -- > ", b = "Summations --> ";
//            for (String iterate : weightVectorIterator) {
//                a = a + weightVector.get(iterate).toString() + " ";
//            }
//            for (String iterate : weightVectorIterator) {
//                b = b + Summation.get(iterate).toString() + " ";
//            }
//            System.out.println(a);
//            System.out.println(b);
            for (String iterate : weightVectorIterator) {
                double newWeight = weightVector.get(iterate) + eita * ((Summation.get(iterate)) - (lamda * weightVector.get(iterate)));
                weightVector.put(iterate, new Double(newWeight));
                partial = partial + (Summation.get(iterate)) - (lamda * weightVector.get(iterate));
            }
            if (partial == 0) {
                System.out.println("Partial Derivative is zero");
                return weightVector;
            }
        }
        return weightVector;
    }
}
