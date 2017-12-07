/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
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
public class ComparePercentV2 {

    private final int MIN_PERCEN = 30;
    private final int NUMBER_SENTENCES=5;

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    public void readFileResult(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                getResultValue(line);
                //System.exit(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(ComparePercentV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //if percen is bigger than 30% then compare two file within it
    public void getResultValue(String str) {
        String[] split = str.split(" ");

        if (Integer.parseInt(split[5].substring(0, split[5].length() - 1)) >= MIN_PERCEN) {
            Pattern pattern = Pattern.compile("\\\\(\\d+\\.txt)");
            Matcher file1 = pattern.matcher(split[0]);
            Matcher file2 = pattern.matcher(split[3]);
            if (file1.find()&&file2.find()) {
                compare2File(file1.group(1),file2.group(1));
            }
        }
    }
    
    public String readSentences(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String all="";
            while ((line = br.readLine()) != null) {
                all+=line;
                //System.exit(0);
            }
            String[] split = all.split("-----------------");
            String[] splitContent=split[split.length-1].split(".");
            
            for (int i = 0, splitIndex=0; i < NUMBER_SENTENCES; ) {
                String value=splitContent[splitIndex];
                splitIndex++;
                i++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ComparePercentV2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void compare2File(String file1,String file2) {
        //System.out.println(file1+" "+file2 );
          readSentences("./DATA/Politics/Politics_VietNamese/"+file1);
        //readSentences("./DATA/Politics_Chinese_Translted/"+file2);
        System.exit(0);
    }

    public static void main(String[] args) {
        ComparePercentV2 c = new ComparePercentV2();
        //c.listFilesForFolder(new File("../"));
        c.readFileResult("../SimilarityByPercent.txt");
    }
}
