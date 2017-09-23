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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ThoiDaiVN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public ThoiDaiVN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://thoidai.com.vn/thoi-su_t113c3":
                directory = "thoidaiVN/thoisu";
                break;
            case "http://thoidai.com.vn/kinh-te_t113c14":
                directory = "thoidaiVN/kinhte";
                break;
            case "http://thoidai.com.vn/giao-duc_t113c20":
                directory = "thoidaiVN/giaoduc";
                break;
            case "http://thoidai.com.vn/suc-khoe_t113c23":
                directory = "thoidaiVN/suckhoe";
                break;
            case "http://thoidai.com.vn/ban-nam-chau_t113c34":
                directory = "thoidaiVN/ban-nam-chau";
                break;
            case "http://thoidai.com.vn/tam-long-be-ban_t113c35":
                directory = "thoidaiVN/tam-long-be-ban";
                break;
            case "http://thoidai.com.vn/van-hoa-du-lich_t113c9":
                directory = "thoidaiVN/van-hoa-du-lich";
                break;
            case "http://thoidai.com.vn/the-gioi_t113c6":
                directory = "thoidaiVN/the-gioi";
                break;
            case "http://thoidai.com.vn/an-toan-giao-thong_t113c95":
                directory = "thoidaiVN/an-toan-giao-thong";
                break;
            case "http://thoidai.com.vn/ban-doc_t113c30":
                directory = "thoidaiVN/ban-doc";
                break;
            case "http://thoidai.com.vn/old/phap-luat_t113c54":
                directory = "thoidaiVN/phap-luat";
                break;
            case "http://thoidai.com.vn/old/gia-dinh-viet_t113c49":
                directory = "thoidaiVN/gia-dinh-viet";
                break;
            case "http://thoidai.com.vn/old/nong-thon_t113c46":
                directory = "thoidaiVN/nong-thon";
                break;
            case "http://thoidai.com.vn/old/the-thao_t113c59":
                directory = "thoidaiVN/the-thao";
                break;
            case "http://thoidai.com.vn/old/moi-truong_t113c62":
                directory = "thoidaiVN/moi-truong";
                break;
            case "http://thoidai.com.vn/nhan-ai_t113c27":
                directory = "thoidaiVN/nhan-ai";
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

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName, position);
        } else {
            getLink(pageName + "p" + pageNumber, position);
        }
    }

    public int getMaxPageNumber() {
        int countPage = -1;
        try {
            Document doc = Jsoup.connect(pageName).timeout(0).get();
            countPage = Integer.parseInt(doc.select("#dnn_ctr530_Main_UserNewsCategory__TotalPage").first().text());
        } catch (IOException ex) {
            Logger.getLogger(VietNamPlusVN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countPage;
    }

    public int getArticleSize() {
        return articleSize;
    }

    public int getPosition() {
        return position;
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
