/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sunish
 */
public class TextIO {

    static ArrayList<String> read(File src) {
        ArrayList<String> inputData = new ArrayList<String>();
        String files[] = src.list();
        for (String file : files) {
            StringBuilder data = new StringBuilder();
            if (!file.equals(".DS_Store")) {
                File srcFile = new File(src, file);
                try {
                    BufferedReader r = new BufferedReader(new FileReader(srcFile));
                    String line = r.readLine();
                    if (line != null) {
                        do {
                            data.append(line + " ");
                            line = r.readLine();
                        } while (line != null);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
                inputData.add(data.toString());
            }
        }
        return inputData;
    }
}
