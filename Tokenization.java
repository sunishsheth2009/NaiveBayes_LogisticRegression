/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;

/**
 *
 * @author Sunish
 */
public class Tokenization {

    static double totalToken = 0;
    static double totalUniqueToken = 0;

    static HashMap<String, Integer> Tokenization(String data, HashMap<String, Integer> token) {
        try {
            String removeDash = data.toString().replace("-", "");
            String removeColon = removeDash.toString().replace(":", "");
            String removeSlash = removeColon.toString().replace("/", "");
            String removeFullStop = removeSlash.toString().replace(".", "");
            String removeComma = removeFullStop.toString().replace(",", "");
            String finalData = data.toLowerCase();
            Object array[] = finalData.split(" ");

            for (int i = 0; i < array.length; i++) {
                //System.out.println(array[i]);
                if (!array[i].equals("") && ((array[i].toString().charAt(0) >= 'a' && array[i].toString().charAt(0) <= 'z') || (array[i].toString().charAt(0) >= 1 && array[i].toString().charAt(0) <= 9))) {
                    totalToken++;
                    if (!token.containsKey(array[i].toString())) {
                        totalUniqueToken++;
                        token.put(array[i].toString(), 1);
                    } else {
                        int count = token.get(array[i].toString());
                        count++;
                        token.put(array[i].toString(), new Integer(count));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return token;
    }
}
