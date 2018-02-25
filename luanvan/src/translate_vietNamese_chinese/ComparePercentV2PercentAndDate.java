/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quang
 */
public class ComparePercentV2PercentAndDate {

    private final int MIN_PERCEN = 30;
    private final int NUMBER_SENTENCES = 5;
    private final int MIN_NUMBER_CHARACTER = 20;

    public void readFileResult(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                getResultValue(line);
                //System.exit(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(ComparePercentV2PercentAndDate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //if percen is bigger than 30% then compare two file within it
    public void getResultValue(String str) {
        String[] split = str.split(" ");

        if (Double.parseDouble(split[5].substring(0, split[5].length() - 1)) >= MIN_PERCEN) {
            Pattern pattern = Pattern.compile("\\\\(\\d+\\.txt)");
            Matcher file1 = pattern.matcher(split[0]);
            Matcher file2 = pattern.matcher(split[3]);
            if (file1.find() && file2.find()) {
                compare2File(file1.group(1), file2.group(1));
            }
        }
    }

    public String readSentences(String filename) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String all = "";
            while ((line = br.readLine()) != null) {
                all += line;
                //System.exit(0);
            }

            String[] split = all.split("-----------------");
            String[] splitContent = split[split.length - 1].split("\\.");

            for (int i = 0, splitIndex = 0; i < NUMBER_SENTENCES&&splitIndex<splitContent.length;) {
                String value = splitContent[splitIndex].trim();
                if (value.length() >= MIN_NUMBER_CHARACTER) {
                    i++;
                    result += value;
                }
                splitIndex++;
            }

        } catch (IOException ex) {
            return "";
        }
        return result;
    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater
            // length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
            /* both strings are zero length */
        }
        /*
		 * // If you have StringUtils, you can use it to calculate the edit
		 * distance: return (longerLength -
		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
		 * longerLength;
         */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }

    public void compare2File(String file1, String file2) {
        String content1 = readSentences("./DATA/Politics/Politics_VietNamese/" + file1);
        String content2 = readSentences("./DATA/Politics_Chinese_Translted/" + file2);
        if (!"".equals(content1) && !"".equals(content2)) {
            //System.out.println(content1);
            //System.out.println(content2);
            String currentString = "";
            int percen=(int)(similarity(content1,content2)*100);
            if (percen > 0 && percen < 101) {
					currentString = "./DATA/Politics/Politics_VietNamese/" + file1 + " similarity with "
							+ "./DATA/Politics_Chinese_Translted/" + file2 + " about " +percen + "% ";
					System.out.println(currentString);
					WriteFile.writeDateTimeSimilarity(currentString + "\n", "SimilarityByPercentV2_2");
					
				}
        }
    }

    public static void main(String[] args) {
        ComparePercentV2PercentAndDate c = new ComparePercentV2PercentAndDate();
        c.readFileResult("../SimilarityByPercent.txt");
        //c.readFileResult("../SimilarityByDate.txt");
    }
}
