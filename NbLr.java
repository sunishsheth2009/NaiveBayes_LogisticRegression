/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sunish
 */
public class NbLr {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // ----- Command Line Arguments -----
        String train_ham = args[0];
        String train_spam = args[1];
        String test_ham = args[2];
        String test_spam = args[3];
        double eita = Double.valueOf(args[4]);
        double lamda = Double.valueOf(args[5]);
        double no_of_iterations = Double.valueOf(args[6]);
        
        // ------- Naive Bayes -------
        // ------ Inputs --------
        File srcHam = new File(train_ham);
        ArrayList<String> inputHam = new ArrayList<String>();
        inputHam = TextIO.read(srcHam);

        File srcSpam = new File(train_spam);
        ArrayList<String> inputSpam = new ArrayList<String>();
        inputSpam = TextIO.read(srcSpam);

        File srcTestHam = new File(test_ham);
        ArrayList<String> TestHam = new ArrayList<String>();
        TestHam = TextIO.read(srcTestHam);

        File srcTestSpam = new File(test_spam);
        ArrayList<String> TestSpam = new ArrayList<String>();
        TestSpam = TextIO.read(srcTestSpam);


        // -------- Naive Bayes ------------
        System.out.println("--------------------- Naive Bayes including Stop Words ---------------------");
        // ------- Tokenization --------
        HashMap<String, Integer> ham = new HashMap<String, Integer>();
        for (String data : inputHam) {
            ham = Tokenization.Tokenization(data, ham);
        }
        double TotalHam = Tokenization.totalToken;
        double TotalUniqueHam = Tokenization.totalUniqueToken;

        //System.out.println("Total Unique Ham " + TotalUniqueHam);

        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        HashMap<String, Integer> spam = new HashMap<String, Integer>();
        for (String data : inputSpam) {
            spam = Tokenization.Tokenization(data, spam);
        }
        double TotalSpam = Tokenization.totalToken;
        double TotalUniqueSpam = Tokenization.totalUniqueToken;


        //System.out.println("Total Unique Spam " + TotalUniqueSpam);

        Set<String> Ham = ham.keySet();
        Set<String> Spam = spam.keySet();
        Set<String> Vocab = new HashSet<String>();
        for (String h : Ham) {
            Vocab.add(h);
        }
        for (String s : Spam) {
            Vocab.add(s);
        }
        Double VocabCount = Double.valueOf(Vocab.size());
        Double probHam = Double.valueOf((double) inputHam.size() / (double) (inputHam.size() + inputSpam.size()));
        Double probSpam = Double.valueOf((double) inputSpam.size() / (double) (inputHam.size() + inputSpam.size()));
        //System.out.println(VocabCount);
        // ------ Compute Probability -------
        HashMap<String, Double> probabHam = findProbility.probability(ham, TotalHam, VocabCount);
        HashMap<String, Double> probabSpam = findProbility.probability(spam, TotalSpam, VocabCount);

        // ------ Testing ------
        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        HashMap<String, Integer> testingTokens = new HashMap<String, Integer>();
        for (String data : TestHam) {
            testingTokens = new HashMap<String, Integer>();
            testingTokens = Tokenization.Tokenization(data, testingTokens);
            Testing.testing(probabHam, ham, probHam, probabSpam, spam, probSpam, testingTokens, TotalHam, TotalSpam, VocabCount);
        }
        int countHam = Testing.countHam;
        int countSpam = Testing.countSpam;
        int totalHamFile = countHam + countSpam;
        int correctHam = countHam;
        System.out.println("Ham Accuracy ---> " + ((double) (correctHam) / (double) totalHamFile) * 100 + " %");

        Testing.countHam = 0;
        Testing.countSpam = 0;
        for (String data : TestSpam) {
            testingTokens = new HashMap<String, Integer>();
            testingTokens = Tokenization.Tokenization(data, testingTokens);
            Testing.testing(probabHam, ham, probHam, probabSpam, spam, probSpam, testingTokens, TotalHam, TotalSpam, VocabCount);
        }
        countHam = Testing.countHam;
        countSpam = Testing.countSpam;
        int totalSpamFile = countHam + countSpam;
        int correctSpam = countSpam;
        System.out.println("Spam Accuracy ---> " + ((double) (correctSpam) / (double) totalSpamFile) * 100 + " %");

        // -------- Accuracy Calculations ---------
        int TotalFile = totalHamFile + totalSpamFile;
        int TotalCorrect = correctHam + correctSpam;

        double accuracy = ((double) TotalCorrect / (double) TotalFile) * 100;

        System.out.println("Average accuracy ---> " + accuracy + " %");


        // ------- Array of Stop Words --------
        String stopWord = "a about above after again against all am an and any are aren't as at be because been before being below between both but by can't cannot could couldn't did didn't do does doesn't doing don't down during each few for from further had hadn't has hasn't have haven't having he he'd he'll he's her here here's hers herself him himself his how how's i i'd i'll i'm i've if in into is isn't it it's its itself let's me more most mustn't my myself no nor not of off on once only or other ought our ours	 ourselves out over own same shan't she she'd she'll she's should shouldn't so some such than that that's the their theirs them themselves then there there's these they they'd they'll they're they've this those through to too under until up very was wasn't we we'd we'll we're we've were weren't what what's when when's where where's which while who who's whom why why's with won't would wouldn't you you'd you'll you're you've your yours yourself yourselves";
        String words[] = stopWord.split(" ");
        Set<String> stopWords = new HashSet<String>();
        for (int i = 0; i < words.length; i++) {
            stopWords.add(words[i]);
        }

        // -------- Naive Bayes removing StopWords --------
        System.out.println("--------------------- Naive Bayes removing Stop Words ---------------------");
        // ------- Tokenization with out stop points --------
        HashMap<String, Integer> hamWOSP = new HashMap<String, Integer>();
        HashMap<String, Integer> spamWOSP = new HashMap<String, Integer>();
        hamWOSP.putAll(ham);
        spamWOSP.putAll(spam);

        double TotalHamWOSP = 0;
        double TotalSpamWOSP = 0;
        for (String s : stopWords) {
            if (hamWOSP.containsKey(s)) {
                int count = hamWOSP.get(s);
                TotalHamWOSP = TotalHamWOSP + count;
                hamWOSP.remove(s);
            }
            if (spamWOSP.containsKey(s)) {
                int count = spamWOSP.get(s);
                TotalSpamWOSP = TotalSpamWOSP + count;
                spamWOSP.remove(s);
            }
        }
        TotalHamWOSP = TotalHam - TotalHamWOSP;
        TotalSpamWOSP = TotalSpam - TotalSpamWOSP;
        double TotalUniqueHamWOSP = hamWOSP.size();
        double TotalUniqueSpamWOSP = spamWOSP.size();


        Set<String> HamWOSP = hamWOSP.keySet();
        Set<String> SpamWOSP = spamWOSP.keySet();
        Set<String> VocabWOSP = new HashSet<String>();
        for (String h : HamWOSP) {
            VocabWOSP.add(h);
        }
        for (String s : SpamWOSP) {
            VocabWOSP.add(s);
        }
        Double VocabCountWOSP = Double.valueOf(VocabWOSP.size());

        //System.out.println(VocabCountWOSP);
        // ------ Compute Probability with out stop points -------
        HashMap<String, Double> probabHamWOSP = findProbility.probability(hamWOSP, TotalHamWOSP, VocabCountWOSP);
        HashMap<String, Double> probabSpamWOSP = findProbility.probability(spamWOSP, TotalSpamWOSP, VocabCountWOSP);

        // ------ Testing with out stop points ------
        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        Testing.countHam = 0;
        Testing.countSpam = 0;
        HashMap<String, Integer> testingTokensWOSP = new HashMap<String, Integer>();
        for (String data : TestHam) {
            testingTokensWOSP = new HashMap<String, Integer>();
            testingTokensWOSP = Tokenization.Tokenization(data, testingTokensWOSP);
            for (String s : stopWords) {
                if (testingTokensWOSP.containsKey(s)) {
                    testingTokensWOSP.remove(s);
                }
            }
            Testing.testing(probabHamWOSP, hamWOSP, probHam, probabSpamWOSP, spamWOSP, probSpam, testingTokensWOSP, TotalHamWOSP, TotalSpamWOSP, VocabCountWOSP);
        }
        int countHamWOSP = Testing.countHam;
        int countSpamWOSP = Testing.countSpam;
        int totalHamFileWOSP = countHamWOSP + countSpamWOSP;
        int correctHamWOSP = countHamWOSP;
        System.out.println("Ham Accuracy ---> " + ((double) (correctHamWOSP) / (double) totalHamFileWOSP) * 100 + " %");

        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        Testing.countHam = 0;
        Testing.countSpam = 0;
        for (String data : TestSpam) {
            testingTokensWOSP = new HashMap<String, Integer>();
            testingTokensWOSP = Tokenization.Tokenization(data, testingTokensWOSP);
            for (String s : stopWords) {
                if (testingTokensWOSP.containsKey(s)) {
                    testingTokensWOSP.remove(s);
                }
            }
            Testing.testing(probabHamWOSP, hamWOSP, probHam, probabSpamWOSP, spamWOSP, probSpam, testingTokensWOSP, TotalHamWOSP, TotalSpamWOSP, VocabCountWOSP);
        }
        countHamWOSP = Testing.countHam;
        countSpamWOSP = Testing.countSpam;
        int totalSpamFileWOSP = countHamWOSP + countSpamWOSP;
        int correctSpamWOSP = countSpamWOSP;
        System.out.println("Spam Accuracy ---> " + ((double) (correctSpamWOSP) / (double) totalSpamFileWOSP) * 100 + " %");

        // -------- Accuracy Calculations with out stop points ---------
        int TotalFileWOSP = totalHamFileWOSP + totalSpamFileWOSP;
        int TotalCorrectWOSP = correctHamWOSP + correctSpamWOSP;

        double accuracyWOSP = ((double) TotalCorrectWOSP / (double) TotalFileWOSP) * 100;

        System.out.println("Average Accuracy ---> " + accuracyWOSP + " %");


        // -------- Logistic Regression --------
        System.out.println("--------------------- Logistic Regression including Stop Words ---------------------");
        // -------- Inputs for LR ---------
        HashMap<Integer, HashMap<String, Integer>> hamLR = new HashMap<Integer, HashMap<String, Integer>>();
        int i = 1;
        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        HashMap<String, Integer> vocabHam = new HashMap<String, Integer>();
        HashMap<String, Integer> rowHam = new HashMap<String, Integer>();
        for (String data : inputHam) {
            vocabHam = new HashMap<String, Integer>();
            for (String v : Vocab) {
                vocabHam.put(v, 0);
            }
            rowHam = new HashMap<String, Integer>();
            rowHam = Tokenization.Tokenization(data, rowHam);
            Set<String> iterator = rowHam.keySet();
            for (String iterate : iterator) {
                if (vocabHam.containsKey(iterate)) {
                    vocabHam.put(iterate, new Integer(rowHam.get(iterate)));
                } else {
                    System.out.println("Vocab Error");
                    System.exit(1);
                }
            }
            hamLR.put(i, vocabHam);
            i++;
        }

        HashMap<Integer, HashMap<String, Integer>> spamLR = new HashMap<Integer, HashMap<String, Integer>>();
        i = 1;
        Tokenization.totalToken = 0;
        Tokenization.totalUniqueToken = 0;
        HashMap<String, Integer> vocabSpam = new HashMap<String, Integer>();
        HashMap<String, Integer> rowSpam = new HashMap<String, Integer>();
        for (String data : inputSpam) {
            vocabSpam = new HashMap<String, Integer>();
            for (String v : Vocab) {
                vocabSpam.put(v, 0);
            }
            rowSpam = new HashMap<String, Integer>();
            rowSpam = Tokenization.Tokenization(data, rowSpam);
            Set<String> iterator = rowSpam.keySet();
            for (String iterate : iterator) {
                if (vocabSpam.containsKey(iterate)) {
                    vocabSpam.put(iterate, new Integer(rowSpam.get(iterate)));
                } else {
                    System.out.println("Vocab Error");
                    System.exit(1);
                }
            }
            spamLR.put(i, vocabSpam);
            i++;
        }

        // -------- Weight Vector calculation and tuning ---------
        HashMap<String, Double> weightVector = new HashMap<String, Double>();
        weightVector.put("0", 0.15);
        for (String v : Vocab) {
            weightVector.put(v, 0.15);
        }

        weightVector = TunningW.tunning(spamLR, hamLR, weightVector, Vocab, eita, lamda, no_of_iterations);

        // -------- Testing for LR -------------
        TestingLR.countHam = 0;
        TestingLR.countSpam = 0;
        HashMap<String, Integer> testingTokensLR = new HashMap<String, Integer>();
        HashMap<String, Integer> testing = new HashMap<String, Integer>();
        for (String data : TestHam) {
            testing = new HashMap<String, Integer>();
            for (String v : Vocab) {
                testing.put(v, 0);
            }
            testingTokensLR = new HashMap<String, Integer>();
            testingTokensLR = Tokenization.Tokenization(data, testingTokensLR);
            Set<String> iterator = testingTokensLR.keySet();
            for (String iterate : iterator) {
                if (testing.containsKey(iterate)) {
                    testing.put(iterate, new Integer(testingTokensLR.get(iterate)));
                }
            }
            TestingLR.testingLR(testing, weightVector);
        }
        int countHamLR = TestingLR.countHam;
        int countSpamLR = TestingLR.countSpam;
        int totalHamFileLR = countHamLR + countSpamLR;
        int correctHamLR = countHamLR;
        double accuracyHamLR = (double) correctHamLR / (double) totalHamFileLR;
        System.out.println("Ham Accuracy ---> " + accuracyHamLR * 100 + " %");

        TestingLR.countHam = 0;
        TestingLR.countSpam = 0;
        for (String data : TestSpam) {
            testing = new HashMap<String, Integer>();
            for (String v : Vocab) {
                testing.put(v, 0);
            }
            testingTokensLR = new HashMap<String, Integer>();
            testingTokensLR = Tokenization.Tokenization(data, testingTokensLR);
            Set<String> iterator = testingTokensLR.keySet();
            for (String iterate : iterator) {
                if (testing.containsKey(iterate)) {
                    testing.put(iterate, new Integer(testingTokensLR.get(iterate)));                    
                }
            }
            TestingLR.testingLR(testing, weightVector);
        }

        countHamLR = TestingLR.countHam;
        countSpamLR = TestingLR.countSpam;
        int totalSpamFileLR = countHamLR + countSpamLR;
        int correctSpamLR = countSpamLR;
        double accuracySpamLR = (double) correctSpamLR / (double) totalSpamFileLR;
        System.out.println("Spam Accuracy ---> " + accuracySpamLR * 100 + " %");

        // ------- Accuracy for LR --------
        int TotalFileLR = totalHamFileLR + totalSpamFileLR;
        int TotalCorrectLR = correctHamLR + correctSpamLR;

        double accuracyLR = ((double) TotalCorrectLR / (double) TotalFileLR) * 100;

        System.out.println("Average accuracy ---> " + accuracyLR + " %");









        // -------- Logistic Regression without Stop Words --------
        System.out.println("--------------------- Logistic Regression removing Stop Words ---------------------");
        // -------- Inputs for LR ---------

        HashMap<Integer, HashMap<String, Integer>> hamLRWOSP = new HashMap<Integer, HashMap<String, Integer>>();
        HashMap<Integer, HashMap<String, Integer>> spamLRWOSP = new HashMap<Integer, HashMap<String, Integer>>();
        hamLRWOSP.putAll(hamLR);
        spamLRWOSP.putAll(spamLR);

        HashMap<Integer, HashMap<String, Integer>> hamLRWOSPClone = new HashMap<Integer, HashMap<String, Integer>>();
        HashMap<Integer, HashMap<String, Integer>> spamLRWOSPClone = new HashMap<Integer, HashMap<String, Integer>>();

        hamLRWOSPClone.putAll(hamLR);
        spamLRWOSPClone.putAll(spamLR);
        Set<Integer> iterateHamLRWOSP = hamLRWOSPClone.keySet();
        Set<Integer> iterateSpamLRWOSP = spamLRWOSPClone.keySet();

        for (Integer iHamLRWOSP : iterateHamLRWOSP) {
            HashMap<String, Integer> rowHamLR = new HashMap<String, Integer>();
            rowHamLR.putAll(hamLRWOSP.get(iHamLRWOSP));
            for (String s : stopWords) {
                if (rowHamLR.containsKey(s)) {
                    rowHamLR.remove(s);
                }
            }
            hamLRWOSP.remove(iHamLRWOSP);
            hamLRWOSP.put(iHamLRWOSP, rowHamLR);
        }
        for (Integer iSpamLRWOSP : iterateSpamLRWOSP) {
            HashMap<String, Integer> rowSpamLR = new HashMap<String, Integer>();
            rowSpamLR.putAll(spamLRWOSP.get(iSpamLRWOSP));
            for (String s : stopWords) {
                if (rowSpamLR.containsKey(s)) {
                    rowSpamLR.remove(s);
                }
            }
            spamLRWOSP.remove(iSpamLRWOSP);
            spamLRWOSP.put(iSpamLRWOSP, rowSpamLR);
        }


        // ------- New Vocab list --------
        Set<String> VocabLRWOSP = new HashSet<String>();
        VocabLRWOSP.addAll(Vocab);
        for (String s : stopWords) {
            if (VocabLRWOSP.contains(s)) {
                VocabLRWOSP.remove(s);
            }
        }

        // -------- Weight Vector calculation and tuning ---------
        HashMap<String, Double> weightVectorWOSP = new HashMap<String, Double>();
        weightVectorWOSP.put("0", 0.15);
        for (String v : VocabLRWOSP) {
            weightVectorWOSP.put(v, 0.15);
        }

        weightVectorWOSP = TunningW.tunning(spamLRWOSP, hamLRWOSP, weightVectorWOSP, VocabWOSP, eita, lamda, no_of_iterations);

        // -------- Testing for LR -------------
        TestingLR.countHam = 0;
        TestingLR.countSpam = 0;
        HashMap<String, Integer> testingTokensLRWOSP = new HashMap<String, Integer>();
        HashMap<String, Integer> testingWOSP = new HashMap<String, Integer>();
        for (String data : TestHam) {
            testingWOSP = new HashMap<String, Integer>();
            for (String v : VocabLRWOSP) {
                testingWOSP.put(v, 0);
            }
            testingTokensLRWOSP = new HashMap<String, Integer>();
            testingTokensLRWOSP = Tokenization.Tokenization(data, testingTokensLRWOSP);
            Set<String> iterator = testingTokensLRWOSP.keySet();
            for (String iterate : iterator) {
                if (testingWOSP.containsKey(iterate) && !stopWords.contains(iterate)) {
                    testingWOSP.put(iterate, new Integer(testingTokensLRWOSP.get(iterate)));
                }
            }
            TestingLR.testingLR(testingWOSP, weightVectorWOSP);
        }
        int countHamLRWOSP = TestingLR.countHam;
        int countSpamLRWOSP = TestingLR.countSpam;
        int totalHamFileLRWOSP = countHamLRWOSP + countSpamLRWOSP;
        int correctHamLRWOSP = countHamLRWOSP;
        System.out.println("Ham Accuracy ---> " + ((double) (correctHamLRWOSP) / (double) totalHamFileLRWOSP) * 100 + " %");

        TestingLR.countHam = 0;
        TestingLR.countSpam = 0;
        for (String data : TestSpam) {
            testingWOSP = new HashMap<String, Integer>();
            for (String v : VocabWOSP) {
                testingWOSP.put(v, 0);
            }
            testingTokensLRWOSP = new HashMap<String, Integer>();
            testingTokensLRWOSP = Tokenization.Tokenization(data, testingTokensLRWOSP);
            Set<String> iterator = testingTokensLRWOSP.keySet();
            for (String iterate : iterator) {
                if (testingWOSP.containsKey(iterate) && !stopWords.contains(iterate)) {
                    testingWOSP.put(iterate, new Integer(testingTokensLRWOSP.get(iterate)));
                }
            }
            TestingLR.testingLR(testingWOSP, weightVectorWOSP);
        }

        countHamLRWOSP = TestingLR.countHam;
        countSpamLRWOSP = TestingLR.countSpam;
        int totalSpamFileLRWOSP = countHamLRWOSP + countSpamLRWOSP;
        int correctSpamLRWOSP = countSpamLRWOSP;
        System.out.println("Spam Accuracy ---> " + ((double) (correctSpamLRWOSP) / (double) totalSpamFileLRWOSP)*100 +" %");

        // ------- Accuracy for LR --------
        int TotalFileLRWOSP = totalHamFileLRWOSP + totalSpamFileLRWOSP;
        int TotalCorrectLRWOSP = correctHamLRWOSP + correctSpamLRWOSP;

        double accuracyLRWOSP = ((double) TotalCorrectLRWOSP / (double) TotalFileLRWOSP) * 100;

        System.out.println("Average Accuracy---> " + accuracyLRWOSP + " %");

    }
}
