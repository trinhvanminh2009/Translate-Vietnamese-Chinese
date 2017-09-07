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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NgoiSao {

    private String currentDirectory;
    private String pageName;
    //private int currentPage;
    private int articleSize;

    public NgoiSao() {
       // currentPage = 0;
        articleSize = 0;
        currentDirectory = "";
    }

    public void init(String pageName, String folderName) {
        currentDirectory = folderName;
        this.pageName = pageName;
        makeDirectory(folderName);
        System.out.println("Start download NgoiSao: "+pageName);
    }

    public boolean scrap(int pageNumber) {

        if (pageNumber == 1) {
            getLink(pageName);
        } else {
            if (!getLink(pageName + "/page/" + pageNumber + ".html")) {
                return false;
            }
        }
        //currentPage = pageNumber;
        return true;
    }


    public int getArticleSize() {
        return articleSize;
    }
//     public int getCurrentPage() {
//        return currentPage;
//    }

    public boolean getLink(String page) {
        String originalUrl = null;
        try {
            originalUrl = Jsoup.connect(page).userAgent("Mozilla")
                    .followRedirects(true) //to follow redirects
                    .execute().url().toExternalForm();
        } catch (IOException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(originalUrl);
        //System.out.println(originalUrl);
        //System.out.println(originalUrl);
        //System.out.println(originalUrl);

        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("ul#news_home div.avata");
            if (div.isEmpty() || !originalUrl.equals(page)) {
                return false;
            }

            System.out.println(page);
            System.out.println(div.size());
            for (Element i : div) {
                Article ar = new Article();
                   ar.setLink(i.select("a[href]").first().attr("abs:href"));
                System.out.println(i.select("a[href]").first().attr("abs:href"));
//                System.exit(0);
                if (getDetails(ar)) {
                    articleSize++;
                    saveArticle(currentDirectory, ar);
                } else {
                    System.out.println("Skip");
                }
            }

        } catch (Exception ex) {
            System.out.println("ex1: " + page);
            System.out.println("ex1: " + ex);
        }
        return true;
    }

    public boolean getDetails(Article ar) {

        try {
            Document doc1 = Jsoup.connect(ar.getLink()).userAgent("Mozilla").timeout(0).get();
            //kiem tra neu k co element thi no qua
            if (doc1.select("div#page_container div.detailCT h1.title").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div#page_container div.detailCT h1.title").first().text());
//            System.out.println(doc1.select("div#container div.title_news h1").first().text());
//                System.exit(0);
            if (doc1.select("div#page_container div.detailCT div.fck_detail.width_common.block_ads_connect").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div#page_container div.detailCT div.fck_detail.width_common.block_ads_connect").first().text());
//            System.out.println(doc1.select("div#container div#left_calculator div.fck_detail.width_common.block_ads_connect").first().text());
//            System.exit(0);
            return true;

        } catch (Exception ex) {
            System.out.println("This link error");
            System.out.println(ar.getLink());
            return false;
        }
    }

    public void makeDirectory(String name) {
        File file = new File(System.getProperty("user.dir") + "/" + name);
        if (!file.exists()) {
            if (file.mkdir()) {
                currentDirectory = name;
                System.out.println(name+" Directory is created!");
            } else {
                currentDirectory = "";
                System.out.println(name+" Failed to create directory!");
            }
        } else {
            currentDirectory = name;
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
        NgoiSao v = new NgoiSao();
        v.init("http://ngoisao.net/tin-tuc/hau-truong", "ad");
        for (int i = 1;; i++) {
            if(!v.scrap(i)){
                break;
            }
        }
        System.out.println(v.articleSize);
       //System.out.println(v.currentPage);
        System.out.println("Done");
    }

}
