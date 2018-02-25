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
public class ComparePercentV2Title {

    private final int MIN_PERCEN = 30;
    private final int NUMBER_SENTENCES = 5;
    private final int MIN_NUMBER_CHARACTER = 20;
    private String folderVN;
    private String folderCN;

    public void setFolderVN(String folderVN) {
        this.folderVN = folderVN;
    }

    public void setFolderCN(String folderCN) {
        this.folderCN = folderCN;
    }

    public void readFileResult(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int i=0;
            String statement="";
            while ((line = br.readLine()) != null) {
                if(i==1){
                    statement+=line;
                    
                    getResultValue(statement);
                    //System.out.println(statement);
                    //System.exit(0);
                    i=0;
                    statement="";
                }
                else{
                    statement+=line;
                    i++;
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ComparePercentV2Title.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //if percen is bigger than 30% then compare two file within it
    public void getResultValue(String str) {

        
        //System.out.println(Double.parseDouble(split[split.length-1].substring(0, split[split.length-1].length() - 1)));
        //System.exit(0);
        //System.out.println(str);
        try{
            Pattern patternPercent = Pattern.compile("about (\\d+)%");
            System.out.println(str);
            Matcher percent = patternPercent.matcher(str);
            if (percent.find()) {
                System.out.println(percent.group(1));
                String strPercent=percent.group(1);
    
                if (Double.parseDouble(strPercent) >= MIN_PERCEN) {                   
                    Pattern pattern = Pattern.compile("\\\\(\\d+\\.txt)");
                    Matcher file1 = pattern.matcher(str);
                    String filename1="",filename2="";
                    if (file1.find()) {
                        //System.out.println(file1.group(1));
                        filename1=file1.group(1);
                        if(file1.find()){                          
                            filename2=file1.group(1);
                            System.out.println(filename1+" "+ filename2);
                             compare2File(filename1, filename2);
                        }
                    }
                    
                }
            }
 
        }catch(NumberFormatException e){
            System.out.println(str);
           
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
        String content1 = readSentences(folderVN + file1);
        String content2 = readSentences(folderCN + file2);
        if (!"".equals(content1) && !"".equals(content2)) {
//            System.out.println(content1);
//            System.out.println(content2);
//            System.exit(0);
            String currentString = "";
            int percen=(int)(similarity(content1,content2)*100);
            if (percen > 0 && percen < 101) {
					currentString = folderVN + file1 + " similarity with "
							+ folderCN + file2 + " about " +percen + "% ";
					System.out.println(currentString);
					WriteFile.writeDateTimeSimilarity(currentString + "\n", "SimilyratyByTitleV2_grimmstories");
					
				}
        }
    }

    public static void main(String[] args) {
        ComparePercentV2Title c = new ComparePercentV2Title();
        //c.setFolderVN("./DATA/Politics/Politics_VietNamese/");
        //c.setFolderCN("./DATA/Politics_Chinese_Translted/");
        
        //c.readFileResult("../SimilarityByPercent.txt");
        
        c.setFolderVN("grimmstories.com/vn/");
        c.setFolderCN("grimmstories_translated/");
        c.readFileResult("SimilyratyByTitle1.txt");
    }
}
