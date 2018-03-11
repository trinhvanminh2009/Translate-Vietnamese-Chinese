/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Quang
 */
public class SentenceMatching_SimilyratyByTitle {

    private final int MIN_PERCEN = 30;
    private final int MIN_SENTENCE_NUMBER_CHARACTER = 20;
    private final String RESULT_FOLDER = WriteSentences.FOLDER;
    private int index = 0;
    private String folderVN;
    private final int MAX_ABS_NUMBER_CHARACTER = 30;
    private String folderCN;

    public void setFolderVN(String folderVN) {
        this.folderVN = folderVN;
    }

    public void setFolderCN(String folderCN) {
        this.folderCN = folderCN;
    }

    public SentenceMatching_SimilyratyByTitle() {
        index = WriteSentences.getFileIndex();
        File file = new File(RESULT_FOLDER);
        if (file.exists()) {

        } else {
            new File(RESULT_FOLDER).mkdir();
        }
    }

    public void readFileSimilyratyByTitle(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int i = 0;
            String statement = "";
            while ((line = br.readLine()) != null) {
                if (i == 1) {
                    statement += line;

                    getResultFileName(statement);
                    //System.out.println(statement+" |");
                    //System.exit(0);
                    i = 0;
                    statement = "";
                } else {
                    statement += line;
                    i++;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ComparePercentV2Title.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeTitle(String str, String filenameCN, String strPercent) {
        Pattern patternTitle1 = Pattern.compile("title (.+) similarity");

        Matcher mTile1 = patternTitle1.matcher(str);
        if (mTile1.find()) {
            //System.out.println(mTile1.group(1));
            String title1 = mTile1.group(1);

            String titleCN = CompareTitle.readFileFromPath(folderCN + filenameCN);
            WriteSentences.writeAppend(title1, titleCN, strPercent, index);
            index++;
        }
    }

    public void matchingSentences(String fileNameVN, String fileNameCN) {
        ArrayList<String> sentencesListVN;
        ArrayList<String> sentencesListCN;
        ArrayList<String> translatedSentencesListCN;
        int sentenceMatched = 0;
        int percent = 0;
        File fileVN = new File(folderVN + fileNameVN);
        File fileCN = new File(folderCN + fileNameCN);

        if (fileVN.exists() && fileCN.exists()) {
            sentencesListVN = readFileVN(folderVN + fileNameVN);
            sentencesListCN = readFileCN(folderCN + fileNameCN);
            if (sentencesListVN.isEmpty() || sentencesListCN.isEmpty()) {
                return;
            }
            translatedSentencesListCN = new ArrayList<>();

            // dich cn to vn
            for (int j = 0; j < sentencesListCN.size(); j++) {
                try {
                    translatedSentencesListCN.add(Translate.translateChineseToVietnamese(sentencesListCN.get(j)).replace("\"", ""));
                } catch (Exception ex) {
                    Logger.getLogger(SentenceMatching_SimilyratyByTitle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ////// tim cau giong nhat
            for (String str : sentencesListVN) {
                sentenceMatched = 0;
                percent = (int) (similarity(str, translatedSentencesListCN.get(0)) * 100);
                for (int j = 1; j < translatedSentencesListCN.size(); j++) {
                    String strTranslated = translatedSentencesListCN.get(j);
                    if (Math.abs(str.length() - strTranslated.length()) < MAX_ABS_NUMBER_CHARACTER) {
                        int indexPercen = (int) (similarity(str, strTranslated) * 100);
                        //System.out.println(str + "\n" + translatedSentencesListCN.get(j));
                        //System.out.println(percent + " " + indexPercen);
                        if (percent < indexPercen) {
                            sentenceMatched = j;
                            percent = indexPercen;
                        }
                    }

                }
                if (percent >= MIN_PERCEN) {
                    //// ghi file
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(RESULT_FOLDER + index + ".txt"), "utf-8"))) {
                        writer.write(str + "\n");
                        writer.write("-----------------\n");
                        writer.write(sentencesListCN.get(sentenceMatched) + "\n");
                        writer.write("-----------------\n");
                        writer.write("similarity about " + percent + "%\n");
                    } catch (IOException ex) {
                        Logger.getLogger(SentenceMatching_SimilyratyByTitle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    index++;
                }

            }

        }
    }

// public static double similarity(String s1, String s2) {
//        String longer = s1, shorter = s2;
//        if (s1.length() < s2.length()) { // longer should always have greater
//            // length
//            longer = s2;
//            shorter = s1;
//        }
//        int longerLength = longer.length();
//        if (longerLength == 0) {
//            return 1.0;
//            /* both strings are zero length */
//        }
//        /*
//		 * // If you have StringUtils, you can use it to calculate the edit
//		 * distance: return (longerLength -
//		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
//		 * longerLength;
//         */
//        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
//
//    }

    //if percen is bigger than 30% then compare two file within it
    public void getResultFileName(String str) {
        try {
            Pattern patternPercent = Pattern.compile("about (\\d+)%");
            System.out.println(str);
            Matcher percent = patternPercent.matcher(str);
            if (percent.find()) {
                //System.out.println(percent.group(1));
                String strPercent = percent.group(1);

                if (Double.parseDouble(strPercent) >= MIN_PERCEN) {
                    Pattern pattern = Pattern.compile("\\\\(\\d+\\.txt)");
                    Pattern nullTitle = Pattern.compile(" title null");
                    if (!nullTitle.matcher(str).find()) {
                        Matcher file1 = pattern.matcher(str);
                        String filename1 = "", filename2 = "";
                        if (file1.find()) {
                            //System.out.println(file1.group(1));
                            filename1 = file1.group(1);
                            if (file1.find()) {
                                filename2 = file1.group(1);
                                writeTitle(str, filename2, strPercent);
                                //System.out.println(filename1+" "+filename2);
                                //System.exit(0);
                                matchingSentences(filename1, filename2);
                            }
                        }
                    }

                }
            }

        } catch (NumberFormatException e) {
            System.out.println("ex  " + e);
            System.out.println("ex  " + str);
        }
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
        double editDistancePercent=(longerLength - editDistance(longer, shorter)) / (double) longerLength;       
        int percent=ComparePercent.percentSimilarityTwoString(s1, s2);
        int datePercent=ConvertDate.dateSimilarityTwoString(s1, s2);
       // System.out.println("asd"+editDistancePercent+"percent :"+percent+" date: "+datePercent);
        if(percent>=50&&datePercent>=50){
           // System.out.println("-----------------1");
            return (editDistancePercent*0.8+percent*0.002+datePercent*0.002);
        }
        else if(percent>=50){
            // System.out.println("-----------------2");
            return (editDistancePercent*0.8+percent*0.002);
        }
        else if(datePercent>=50){
            // System.out.println("-----------------3");
            return (editDistancePercent*0.8+datePercent*0.002);
        }
        else{
            // System.out.println("-----------------4");
            return (editDistancePercent);
        }

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

    public ArrayList readFileVN(String filename) {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String all = "";
            while ((line = br.readLine()) != null) {
                all += line;
            }

            String[] split = all.split("-----------------");
            String delimiters = "[^th.s|^t.s|^Th.s|^T.s|^Mr.|^mr.|^Ms.|^ms.]\\.";

            String[] splitContent = split[2].split(delimiters);

            for (int i = 0; i < splitContent.length; i++) {
                String value = splitContent[i].trim();
                if (value.length() >= MIN_SENTENCE_NUMBER_CHARACTER) {
                    result.add(value);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return new ArrayList<>();
        }
        return result;
    }

    public ArrayList readFileCN(String filename) {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String all = "";
            while ((line = br.readLine()) != null) {
                all += line;
            }

            String[] split = all.split("-----------------");
            String[] splitContent = split[2].split("\\。");
            for (int i = 0; i < splitContent.length; i++) {
                String value = splitContent[i].trim();
                if (value.length() >= MIN_SENTENCE_NUMBER_CHARACTER) {
                    result.add(value);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return new ArrayList<>();
        }
        return result;
    }

    public void writeFilterFileByPercent(String source, String dest, int minPercent, int maxPercent) {

        File destFolder = new File(dest);
        if (destFolder.exists()) {

        } else {
            destFolder.mkdir();
        }

        File[] files = new File(source).listFiles();
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(source + file.getName()))) {
                String line;
                String all = "";
                while ((line = br.readLine()) != null) {
                    all += line;
                }

                String[] split = all.split("-----------------");
                String number = split[2].split(" ")[2];
                //        >
                int percent = Integer.parseInt(number.substring(0, number.length() - 1));
                if (percent >= minPercent && percent <= maxPercent) {     
                    copyFileUsingJava7Files(file, new File(dest + file.getName()));
                }

            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        System.out.println(destFolder.listFiles().length);

    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    public static void main(String[] args) {
        SentenceMatching_SimilyratyByTitle s = new SentenceMatching_SimilyratyByTitle();
        //  s.writeFilterFileByPercent("SentenceMatching/","95-100/", 95,100);sys
        s.setFolderVN("G:\\IT\\luanvan\\DATA\\grimmstories.com\\vn\\");
        s.setFolderCN("G:\\IT\\luanvan\\DATA\\grimmstories.com\\cn\\");
        s.readFileSimilyratyByTitle("SimilyratyByTitle-grim.txt");
    }
}
