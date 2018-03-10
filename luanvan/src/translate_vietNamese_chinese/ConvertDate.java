package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ConvertDate {

    private String content;
    private String filePath;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ConvertDate(String content, String filePath) {
        super();
        this.content = content;
        this.filePath = filePath;
    }

    public ConvertDate(String content) {
        super();
        this.content = content;

    }

    public static void listFilesForFolder(final File folderVN, final File folderCN) throws Exception {
        ArrayList<ConvertDate> resultVN = new ArrayList<>();
        ArrayList<ConvertDate> resultCN = new ArrayList<>();
        for (final File file : folderVN.listFiles()) {
            if (file.isDirectory()) {
                listFilesForFolder(file, file);
            } else {
                resultVN.addAll(readFileFromPath(file.getPath()));
            }
        }
        for (final File file : folderCN.listFiles()) {
            if (file.isDirectory()) {
                listFilesForFolder(file, file);
            } else {
                resultCN.addAll(readFileFromPath(file.getPath()));
            }
        }

        similarityFile(resultVN, resultCN);
    }

    public static ArrayList<ConvertDate> readFileFromPath(String filePath) throws Exception {
        ArrayList<ConvertDate> result = new ArrayList<>();
        final BufferedReader bufferedReader;
        try {
            File file = new File(filePath);
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.ready();
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();

            }
            String everything = stringBuilder.toString();
            String key = "-----------------";
            result = handleFileString(everything, key, filePath);
            bufferedReader.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    private static ArrayList<ConvertDate> handleFileString(String content, String key, String filePath)//Return main content
            throws Exception {
        // Check file and remove key to get main contain
        int lastIndex = 0;
        ArrayList<ConvertDate> resultList = new ArrayList<>();
        ArrayList<Integer> listIndex = new ArrayList<>();
        while (lastIndex != -1) {
            lastIndex = content.indexOf(key, lastIndex);
            if (lastIndex != -1) {
                lastIndex += key.length();
                content.substring(lastIndex, content.length());
                listIndex.add(lastIndex);

            }
        }
        String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
        if (filePath.equals("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese")) {
            resultList.addAll(getDayMonthYearVN(mainContent, filePath));
            resultList.addAll(getMonthYearVN(mainContent, filePath));

        }
        if (filePath.equals("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese")) {
            resultList.addAll(getDateMonthYearCN(mainContent, filePath));
            resultList.addAll(getMonthYearCN(mainContent, filePath));

        }
        return resultList;
    }

    private static ArrayList<ConvertDate> getMonthYearVN(String content, String filePath) {
        ArrayList<ConvertDate> listMonthYear = new ArrayList<>();
        int lastIndex = 0;
        String tempString;
        ArrayList<String> listSpecicalChar = new ArrayList<>();
        listSpecicalChar.add("\\.");
        listSpecicalChar.add(",");
        // the character ( and ) and { and } are special character in Regex
        listSpecicalChar.add("\\)");
        listSpecicalChar.add(";");
        listSpecicalChar.add("---");
        String[] listTempString;
        String[] monthYear;
        // Check index not null
        while (lastIndex != -1) {
            lastIndex = content.indexOf("tháng ", lastIndex);
            if (lastIndex != -1) {
                // Plus five because ngày have length is 6 include space
                lastIndex += 7;
                // Get substring include date month year inside
                tempString = content.substring(lastIndex - 7, lastIndex + 11);
                // Check this string contains "/" inside to make sure it's date/
                // month/year of Vietnamese
                if (tempString.contains("/")) {
                    // Check substring contains " " to know this not wrong
                    if (tempString.contains(" ")) {
                        listTempString = tempString.split(" ");
                        for (int i = 0; i < listSpecicalChar.size(); i++) {
                            monthYear = listTempString[1].split(listSpecicalChar.get(i));
                            if (monthYear.length > 0) {
                                listTempString[1] = monthYear[0];

                            } else {

                            }
                        }
                        if (Character.isDigit(listTempString[1].charAt(0))) {
                            tempString = listTempString[0] + " " + listTempString[1];
                        } else {
                            break;
                        }
                    }
                    ConvertDate convertDate = new ConvertDate(tempString.trim(), filePath);
                    listMonthYear.add(convertDate);
                }
            }
            tempString = "";
        }
        return listMonthYear;
    }

    private static ArrayList<ConvertDate> getMonthYearVnWithoutPath(String content) {
        ArrayList<ConvertDate> listMonthYear = new ArrayList<>();
        int lastIndex = 0;
        String tempString;
        ArrayList<String> listSpecicalChar = new ArrayList<>();
        listSpecicalChar.add("\\.");
        listSpecicalChar.add(",");
        // the character ( and ) and { and } are special character in Regex
        listSpecicalChar.add("\\)");
        listSpecicalChar.add(";");
        listSpecicalChar.add("---");
        String[] listTempString;
        String[] monthYear;
        // Check index not null

        try {
            while (lastIndex != -1) {
                lastIndex = content.indexOf("tháng ", lastIndex);
                if (lastIndex != -1) {
                    // Plus five because ngày have length is 6 include space
                    lastIndex += 7;
                    // Get substring include date month year inside

                    tempString = content.substring(lastIndex - 7, lastIndex + 9);
                    // Check this string contains "/" inside to make sure it's date/
                    // month/year of Vietnamese

                    // Check substring contains " " to know this not wrong
//                if (tempString.contains(" ")) {
//                    listTempString = tempString.split(" ");
//                    for (int i = 0; i < listSpecicalChar.size(); i++) {
//                        monthYear = listTempString[1].split(listSpecicalChar.get(i));
//                        if (monthYear.length > 0) {
//                            listTempString[1] = monthYear[0];
//
//                        } else {
//
//                        }
//                    }
//                    if (Character.isDigit(listTempString[1].charAt(0))) {
//                        tempString = listTempString[0] + " " + listTempString[1];
//                    } else {
//                        break;
//                    }
//                }
                    ConvertDate convertDate = new ConvertDate(tempString.trim());
                    //System.out.println(tempString);
                    listMonthYear.add(convertDate);
                }

                tempString = "";
            }
        } catch (StringIndexOutOfBoundsException e) {
            return new ArrayList<>();
        }
        return listMonthYear;
    }

    private static ArrayList<ConvertDate> getDayMonthYearVN(String content, String filePath) {
        ArrayList<ConvertDate> listDateMonthYear = new ArrayList<>();
        int lastIndex = 0;
        String tempString;
        ArrayList<String> listSpecicalChar = new ArrayList<>();
        listSpecicalChar.add("\\.");
        listSpecicalChar.add(",");
        // the character ( and ) and { and } are special character in Regex
        listSpecicalChar.add("\\)");
        listSpecicalChar.add(";");
        listSpecicalChar.add("---");
        String[] listTempString;
        String[] dateMonthYear;
        // Check index not null
        while (lastIndex != -1) {
            lastIndex = content.indexOf("ngày ", lastIndex);
            if (lastIndex != -1) {
                // Plus five because ngày have length is 6 include space
                lastIndex += 6;
                // Get substring include date month year inside
                tempString = content.substring(lastIndex - 6, lastIndex + 18);
                // Check this string contains "/" inside to make sure it's date/
                // month/year of Vietnamese
                if (tempString.contains("/")) {
                    // Check substring contains " " to know this not wrong
                    if (tempString.contains(" ")) {
                        listTempString = tempString.split(" ");
                        for (int i = 0; i < listSpecicalChar.size(); i++) {
                            dateMonthYear = listTempString[1].split(listSpecicalChar.get(i));
                            if (dateMonthYear.length > 0) {
                                listTempString[1] = dateMonthYear[0];

                            } else {

                            }
                        }
                        if (Character.isDigit(listTempString[1].charAt(0))) {
                            tempString = listTempString[0] + " " + listTempString[1];
                        } else {
                            break;
                        }
                    }
                    ConvertDate convertDate = new ConvertDate(tempString.trim(), filePath);
                    listDateMonthYear.add(convertDate);
                }
            }
            tempString = "";
        }

        return listDateMonthYear;

    }

    public static ArrayList<ConvertDate> getDateMonthYearCN(String content, String filePath) throws Exception {
        int lastIndex = 0;
        String subTempString;
        String tempString;
        ArrayList<ConvertDate> listDateMonth = new ArrayList<>();
        while (lastIndex != -1) {
            // Check in substring include year
            lastIndex = content.indexOf("年 ", lastIndex);
            if (lastIndex != -1) {
                // Length of year and space
                lastIndex += 2;
                // Get substring is date month year and space
                tempString = content.substring(lastIndex - 7, lastIndex + 10).trim();
                // Check first is digit to make sure it's year in Chinese
                if (Character.isDigit(tempString.charAt(0))) {
                    // Make sure substring include date
                    if (tempString.contains("月") && tempString.contains(" ") && tempString.contains("日")) {
                        // Check if last character is date in Chinese add it
                        // into list. Else remove and add it later
                        if (tempString.substring(tempString.length() - 1, tempString.length()).trim()
                                .equals("日".trim())) {
                            ConvertDate convertDate = new ConvertDate(tempString, filePath);
                            listDateMonth.add(convertDate);
                        } else {
                            subTempString = tempString.substring(0, tempString.indexOf("日") + 1);
                            ConvertDate convertDate = new ConvertDate(subTempString, filePath);
                            listDateMonth.add(convertDate);
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return listDateMonth;
    }

    public static ArrayList<ConvertDate> getMonthYearCN(String content, String filePath) throws Exception {
        int lastIndex = 0;
        String subTempString;
        String tempString;
        ArrayList<ConvertDate> listDateMonth = new ArrayList<>();
        while (lastIndex != -1) {
            lastIndex = content.indexOf("年 ", lastIndex);
            if (lastIndex != -1) {
                lastIndex += 2;
                tempString = content.substring(lastIndex - 7, lastIndex + 8).trim();

                if (Character.isDigit(tempString.charAt(0))) {
                    if (tempString.contains("月") && tempString.contains(" ") && !tempString.contains("日")) {
                        if (tempString.substring(tempString.length() - 1, tempString.length()).trim()
                                .equals("月".trim())) {
                            ConvertDate convertDate = new ConvertDate(tempString, filePath);
                            listDateMonth.add(convertDate);
                        } else {
                            subTempString = tempString.substring(0, tempString.indexOf("月") + 1);
                            ConvertDate convertDate = new ConvertDate(subTempString, filePath);
                            listDateMonth.add(convertDate);
                        }
                    }
                } else {
                    break;
                }
            }
        }

        return listDateMonth;
    }

    public static ArrayList<ConvertDate> getMonthYearCNWithoutPath(String content) throws Exception {
        int lastIndex = 0;
        String subTempString;
        String tempString;
        ArrayList<ConvertDate> listDateMonth = new ArrayList<>();
        try {
            while (lastIndex != -1) {
                lastIndex = content.indexOf("年", lastIndex);
                System.out.println(lastIndex);
                if (lastIndex != -1) {
                    lastIndex += 2;
                    tempString = content.substring(lastIndex - 6, lastIndex + 3).trim();

                    if (Character.isDigit(tempString.charAt(0))) {
                        if (tempString.contains("月") && !tempString.contains("日")) {
                            if (tempString.substring(tempString.length() - 1, tempString.length()).trim()
                                    .equals("月".trim())) {
                                ConvertDate convertDate = new ConvertDate(tempString);
                                listDateMonth.add(convertDate);
                            } else {
                                subTempString = tempString.substring(0, tempString.indexOf("月") + 1);
                                ConvertDate convertDate = new ConvertDate(subTempString);
                                listDateMonth.add(convertDate);
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            return new ArrayList<>();
        }

        return listDateMonth;
    }

    public static String convertFormatAfterTranslated(String input) {
        input = input.toLowerCase();
        if (input.contains("ngày")) {
            input = input.replace("tháng", "/");
            input = input.replace("năm", "/");
            input = input.replace("đến", "-");
            input = input.replace(" ", "");
            input = input.replace("ngày", "ngày ");
        } else if (!input.contains("ngày")) {

            input = input.replace("năm", "/");
            input = input.replace("đến", "-");
            input = input.replace(" ", "");
            input = input.replace("tháng", "tháng ");
            if (Pattern.compile("(\\d+)(tháng)").matcher(input).find()) {
                input = input.replace("tháng", "/");
                input = input.replace("năm", "/");
                input = input.replace("đến", "-");
                input = input.replace(" ", "");
                input = "ngày " + input;
            }

        }

        return input;
    }

    public static void handleArrayListDate(ArrayList<ConvertDate> listVN, ArrayList<ConvertDate> listCN)
            throws Exception {
        ArrayList<ConvertDate> listCNTranslated = new ArrayList<>();

        // Translate date time and then convert format to easy to compare with
        // Vietnamese
        for (int i = 0; i < listCN.size(); i++) {
            ConvertDate convertDate = new ConvertDate(
                    convertFormatAfterTranslated(Translate.translateChineseToVietnamese(listCN.get(i).getContent())),
                    listCN.get(i).getFilePath());
            listCNTranslated.add(convertDate);
        }

        for (int i = 0; i < listCNTranslated.size(); i++) {

            for (int j = 0; j < listVN.size(); j++) {
                // Check similarity of content and show located of each content
                CompareTitle.printSimilarityDate(listCNTranslated.get(i), listVN.get(j));

            }
        }

    }

    public static ArrayList<SimilarityDate> similarityFile(ArrayList<ConvertDate> listVN, ArrayList<ConvertDate> listCN)
            throws Exception {
        String tempPath = "";
        SimilarityDate similarityDate = null;
        ArrayList<ConvertDate> listCNTranslated = new ArrayList<>();
        ArrayList<SimilarityDate> listSimilarityDate = new ArrayList<>();

        // Translate date time and then convert format to easy to compare with
        // Vietnamese
        for (int i = 0; i < listCN.size(); i++) {
            ConvertDate convertDate = new ConvertDate(
                    convertFormatAfterTranslated(Translate.translateChineseToVietnamese(listCN.get(i).getContent())),
                    listCN.get(i).getFilePath());
            listCNTranslated.add(convertDate);
        }

        for (int i = 0; i < listCNTranslated.size(); i++)// This loop to get
        // fist element in
        // array list
        {
            tempPath = listCN.get(i).getFilePath();
            for (int j = 0; j < listCNTranslated.size(); j++)// This loop to
            // compare all
            // elements with
            // first
            // selected
            // element.
            {
                if (listCNTranslated.get(j).getFilePath().equals(tempPath)) {
                    for (int k = 0; k < listVN.size(); k++) {
                        // Check similarity of content and show located of each
                        // content

                        similarityDate = CompareTitle.printSimilarityDate(listCNTranslated.get(j), listVN.get(k));
                        if (similarityDate != null) {
                            listSimilarityDate.add(similarityDate);
                        }
                    }
                }
            }

        }
        printSimilarity(listSimilarityDate, listVN, listCNTranslated);

        return listSimilarityDate;
    }

    public static int similarityWithoutPath(ArrayList<ConvertDate> listVN, ArrayList<ConvertDate> listCNTranslated)
            throws Exception {

        //System.out.println(listVN.size() + " ," + listCN.size());
        SimilarityDate similarityDate = null;
        int count=0;
        ArrayList<ConvertDate> listConvertDateVN = new ArrayList<>();
        ArrayList<ConvertDate> listConvertDateCN = new ArrayList<>();

        // Translate date time and then convert format to easy to compare with
        // Vietnamese
        for (int i = 0; i < listCNTranslated.size(); i++) {
            ConvertDate convertDate = new ConvertDate(
                    convertFormatAfterTranslated(listCNTranslated.get(i).getContent()));
            //System.out.println(listCN.get(i).getContent());
            listConvertDateCN.add(convertDate);
        }

        for (int i = 0; i < listVN.size(); i++) {
            ConvertDate convertDate = new ConvertDate(
                    convertFormatAfterTranslated(listVN.get(i).getContent()));
            //System.out.println(listVN.get(i).getContent());
            listConvertDateVN.add(convertDate);
        }

        for (int j = 0; j < listConvertDateCN.size(); j++)// This loop to
        // compare all
        // elements with
        // first
        // selected
        // element.
        {
            for (int k = 0; k < listConvertDateVN.size(); k++) {
                // Check similarity of content and show located of each
                // content
               // System.out.println(listConvertDateCN.get(j).getContent() + "," + listConvertDateVN.get(k).getContent());
                int result= CompareTitle.printSimilarityDateWithoutPath(listConvertDateCN.get(j).getContent(), listConvertDateVN.get(k).getContent());
                if(result>92){
                    count++;
                }
    
            }

        }
        //printSimilarity(listSimilarityDate, listVN, listCNTranslated);       
        return (int)((float)count/((listConvertDateVN.size()+listConvertDateCN.size())/2)*100);
    }

    // This function to get which date or month in VN similarity with CN, and
    // show how many % similarity between files
    public static void printSimilarity(ArrayList<SimilarityDate> listSimilarityDate, ArrayList<ConvertDate> listVN,
            ArrayList<ConvertDate> listCN) throws Exception {
        System.out.println("Write file is running!");

        ArrayList<FilePathAndItems> listFilePathAndItemsVN = new ArrayList<>();
        ArrayList<FilePathAndItems> listFilePathAndItemsCN = new ArrayList<>();
        ArrayList<String> filePathAdded = new ArrayList<>();
        ArrayList<String> listDate = new ArrayList<>();
        int tempCount = 0;
        ///////////////////// Start Loop for VN//////////////////////////
        for (int i = 0; i < listVN.size(); i++) {
            for (int j = 0; j < listVN.size(); j++) {
                // Get items same path and not include that item in loop 2
                if (listVN.get(j).getFilePath().equals(listVN.get(i).getFilePath())
                        && !filePathAdded.contains(listVN.get(j).getFilePath())) {
                    listDate.add(listVN.get(j).getContent());
                    tempCount++;
                }
            }

            if (tempCount > 0) {
                FilePathAndItems filePathAndItems = new FilePathAndItems(listVN.get(i).getFilePath(), listDate);
                listFilePathAndItemsVN.add(filePathAndItems);
                listDate = new ArrayList<>();

            }
            tempCount = 0;

        }
        // End loop
        ///////////////////// Start Loop for CN//////////////////////////

        for (int i = 0; i < listCN.size(); i++) {
            for (int j = 0; j < listCN.size(); j++) {
                // Get items same path and not include that item in loop 2
                if (listCN.get(j).getFilePath().equals(listCN.get(i).getFilePath())
                        && !filePathAdded.contains(listCN.get(j).getFilePath())) {
                    listDate.add(listCN.get(j).getContent());
                    tempCount++;
                }
            }

            if (tempCount > 0) {
                FilePathAndItems filePathAndItems = new FilePathAndItems(listCN.get(i).getFilePath(), listDate);
                listFilePathAndItemsCN.add(filePathAndItems);
                listDate = new ArrayList<>();

            }
            tempCount = 0;
        }
        ArrayList<String> listCheckBeforeWriteDownToFile = new ArrayList<>();
        ArrayList<PercentSimilarityTitle> listPercentSimilarity = new ArrayList<>();
        String currentString = new String();
        for (int i = 0; i < listFilePathAndItemsVN.size(); i++) {
            for (int j = 0; j < listFilePathAndItemsVN.get(i).getListDate().size(); j++) {
                for (int k = 0; k < listFilePathAndItemsCN.size(); k++) {
                    for (int h = 0; h < listFilePathAndItemsCN.get(k).getListDate().size(); h++) {

                        if (CompareTitle.printSimilarity1(listFilePathAndItemsCN.get(k).getListDate().get(h),
                                listFilePathAndItemsVN.get(i).getListDate().get(j))) {
                            float percentSimilarity = percentSimilarityBetweenTwoFiles(
                                    listFilePathAndItemsVN.get(i).getListDate(),
                                    listFilePathAndItemsCN.get(k).getListDate());
                            if (percentSimilarity != 0 && percentSimilarity < 101) {
                                PercentSimilarityTitle percentSimilarityObject = new PercentSimilarityTitle(
                                        percentSimilarity, listFilePathAndItemsVN.get(i).getFilePath(),
                                        listFilePathAndItemsCN.get(k).getFilePath());
                                listPercentSimilarity.add(percentSimilarityObject);
                            }
                        }
                    }
                }
            }
        }

        sortByPercentSimilarity(listPercentSimilarity);
        for (int i = 0; i < listPercentSimilarity.size(); i++) {
            currentString = listPercentSimilarity.get(i).getPathVN() + " similarity with "
                    + listPercentSimilarity.get(i).getPathCN() + " about "
                    + listPercentSimilarity.get(i).getPercentSimilarity() + " %";
            if (!listCheckBeforeWriteDownToFile.contains(currentString)) {
                listCheckBeforeWriteDownToFile.add(currentString);
                WriteFile.writeDateTimeSimilarity(currentString + "\n", "SimilarityByDate");
            }
        }
        System.out.println("Write file successfully!");
    }

    private static ArrayList<PercentSimilarityTitle> sortByPercentSimilarity(
            ArrayList<PercentSimilarityTitle> listPercentSimilarity) {
        listPercentSimilarity.sort(Comparator.comparing(PercentSimilarityTitle::getPercentSimilarity));
        return listPercentSimilarity;

    }

    private static float percentSimilarityBetweenTwoFiles(ArrayList<String> listDatesVN,
            ArrayList<String> listDatesCN) {
        int count = 0;
        for (int i = 0; i < listDatesVN.size(); i++) {
            System.out.println("Date VN: " + listDatesVN.get(i) + "\n");
            for (int j = 0; j < listDatesCN.size(); j++) {
                System.out.println("Date CN: " + listDatesCN.get(j) + "\n");
                if (listDatesVN.get(i).equals(listDatesCN.get(j))) {
                    count = count + 1;
                }
            }
        }

        return ((float) count / (float) ((listDatesVN.size() + listDatesCN.size()) / 2)) * 100;

    }

    public static int dateSimilarityTwoString(String contentVN, String contentCNTranslated)  {
        ArrayList<ConvertDate> listCNTranslated = getMonthYearVnWithoutPath(contentCNTranslated);
        ArrayList<ConvertDate> listVN = getMonthYearVnWithoutPath(contentVN);
        //System.out.println(listVN.size() + " ," + listCNTranslated.size());
        if (!listCNTranslated.isEmpty() && !listVN.isEmpty()) {
            try {
                return similarityWithoutPath(listVN, listCNTranslated);
            } catch (Exception ex) {
               return 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
//		final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese");
//		final File folder2 = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese");
        //listFilesForFolder(folder, folder2);
        System.out.println(dateSimilarityTwoString("GDP của Việt Nam tháng 8 năm 2019 tăng 5,52% trong nửa đầu tháng 7 năm 2018", "GDP của Việt tháng 4 năm 2020 Namtrong nửa đầu tháng 7 năm 2018 tăng 5,52% "));
        //ystem.out.println(getMonthYearCNWithoutPath("2018年7月我出去了").size()) ;

        // System.out.println(CompareTitle.printSimilarityDateWithoutPath("2016年上半年越南GDP增长5.52%","GDP của Việt Nam tăng 5,52% trong nửa đầu năm 2016"));
    }
}
