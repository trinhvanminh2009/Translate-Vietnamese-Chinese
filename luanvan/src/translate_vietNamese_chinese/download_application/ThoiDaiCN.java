/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese.download_application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ThoiDaiCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public ThoiDaiCN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://shidai.vn/政治_t113c3":
                directory = "thoidaiCN/Politics";
                break;
            case "http://shidai.vn/经济_t113c89":
                directory = "thoidaiCN/Economic";
                break;
            case "http://shidai.vn/文化-体育_t113c6":
                directory = "thoidaiCN/Cultural-Sport";
                break;
            case "http://shidai.vn/越南家庭_t113c9":
                directory = "thoidaiCN/Vietnamese-Family";
                break;
            case "http://shidai.vn/社会_t113c14":
                directory = "thoidaiCN/Society";
                break;
            case "http://shidai.vn/热爱祖国_t113c20":
                directory = "thoidaiCN/Love-the-motherland";
                break;
            case "http://shidai.vn/越中关系_t113c23":
                directory = "thoidaiCN/Vietnamese-relations";
                break;
            case "http://shidai.vn/四海兄弟_t113c27":
                directory = "thoidaiCN/Four-Seas-Brothers";
                break;
            case "http://shidai.vn/国际_t113c30":
                directory = "thoidaiCN/International";
                break;

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download ThoiDai: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download ThoiDai: " + pageName);
    }

    public int getPosition() {
        return position;
    }

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName, position);
        } else {
            getLink(pageName + "p" + pageNumber, position);
        }
    }

    public boolean checkValidLink(int pageNumber) {

        try {
            Document doc1 = Jsoup.connect(pageName + "p" + pageNumber).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("#dnn_ctr530_ModuleContent div.contentx > div.xitem");
            if (div.isEmpty()) {
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(ThoiDaiCN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public int getMaxPageNumber() {
        System.out.println("Finding max page number");
        int result = 1000;
        int f = 1000;
        boolean reachInvalidLink = false;
        while (true) {

            if (f <= 5) {
                break;
            }
            if (f >= 50000 || f <= 0) {
                return -1;
            }
            if (checkValidLink(result)) {
                if (reachInvalidLink == true) {
                    f = f / 2;
                    result += f;
                } else {
                    result += f;
                }
            } else {
                f = f / 2;
                result -= f;
                reachInvalidLink = true;
            }

        }
        if (checkValidLink(result) == true) {
            //neu chay 10000 lan k co ket qua thi dung 
            for (int i = result; i < 10000; i++) {
                if (!checkValidLink(i)) {

                    return i - 1;
                }
            }
        } else {
            for (int i = result; i >= 1; i--) {
                if (checkValidLink(i)) {

                    return i;
                }
            }
        }
        return -1;
    }

    public int getArticleSize() {
        return articleSize;
    }

    public void getLink(String page, int position) {
        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("#dnn_ctr530_ModuleContent div.contentx > div.xitem");
            int divSize = div.size();
            System.out.println(page);
            System.out.println(divSize);
            Article ar;
            for (int i = position; i < divSize; i++) {
                if (ScrapingThread.stop == true) {
                    this.position = i;
                    break;
                } else {
                    ar = new Article();
                    ar.setLink(div.get(i).select("a[href]").first().attr("abs:href"));
                    // System.out.println(i.select("a[href]").first().attr("abs:href"));
                    //  System.exit(0);
                    if (getDetails(ar)) {
                        articleSize++;
                        saveArticle(currentDirectory, ar);
                    } else {
                        System.out.println("Skip");
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println("ex1: " + page);
            System.out.println("ex1: " + ex);
        }
    }

    public boolean getDetails(Article ar) {

        try {
            Document doc1 = Jsoup.connect(ar.getLink()).userAgent("Mozilla").timeout(0).get();
            //kiem tra neu k co element thi no qua
            if (doc1.select("#dnn_ctr519_Main_UserNewsDetail_up h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("#dnn_ctr519_Main_UserNewsDetail_up h1").first().text());
            //  System.out.println(doc1.select("#dnn_ctr519_Main_UserNewsDetail_up h1").first().text());
            //       System.exit(0);
            if (doc1.select("#dnn_ctr519_Main_UserNewsDetail_up > div.xcontents").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("#dnn_ctr519_Main_UserNewsDetail_up > div.xcontents").first().text());
            // System.out.println(doc1.select("#dnn_ctr519_Main_UserNewsDetail_up > div.xcontents").first().text());
            // System.exit(0);
            return true;

        } catch (Exception ex) {
            System.out.println("This link error");
            System.out.println(ar.getLink());
            return false;
        }
    }

    public void makeDirectory(String name, boolean deleteAll) {
        File file = new File(System.getProperty("user.dir") + "/" + name);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println(name + " Directory is created!");
            } else {
                System.out.println(name + " Failed to create directory!");
            }
        } else {
            if (deleteAll) {
                for (File f : new File(name).listFiles()) {
                    f.delete();
                }
            }
        }
    }

    public void saveArticle(String name, Article a) throws UnsupportedEncodingException, IOException {
        if (a.getTitle() != null && a.getContent().length() > 100 && a.getLink() != null) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"), "utf-8"))) {

                writer.write(a.getLink() + "\n-----------------\n");
                writer.write(a.getTitle() + "\n-----------------\n");
                writer.write(a.getContent() + "\n-----------------\n");
                System.out.println("Created " + System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt" + " files Successfully");
            }
        }
    }

    public static void main(String[] args) throws Exception {

    }

}
