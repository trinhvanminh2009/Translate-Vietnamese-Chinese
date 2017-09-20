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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SGGPCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;

    public SGGPCN() {
        articleSize = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {            
            case "http://cn.sggp.org.vn/時政/":
                directory = "sggpCN/Politics";
                break;
            case "http://cn.sggp.org.vn/法律/":
                directory = "sggpCN/Law";
                break;
            case "http://cn.sggp.org.vn/經濟/":
                directory = "sggpCN/Economic";
                break;
            case "http://cn.sggp.org.vn/國際/":
                directory = "sggpCN/International";
                break;
            case "http://cn.sggp.org.vn/華人動態/":
                directory = "sggpCN/Chinese dynamic";
                break;

            case "http://cn.sggp.org.vn/教育/":
                directory = "sggpCN/Education";
                break;
            case "http://cn.sggp.org.vn/體育/":
                directory = "sggpCN/Sports";
                break;
            case "http://cn.sggp.org.vn/科技/":
                directory = "sggpCN/Technology";
                break;
            case "http://cn.sggp.org.vn/健康-飲食/":
                directory = "sggpCN/Health";
                break;
            case "http://cn.sggp.org.vn/文娛/":
                directory = "sggpCN/Entertainment";
                break;
            case "http://cn.sggp.org.vn/讀者-慈善/":
                directory = "sggpCN/Readers - Charity";
                break;
            case "http://cn.sggp.org.vn/旅遊/":
                directory = "sggpCN/Travel";
                break;

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download SGGPCN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download SGGPCN: " + pageName);
    }

    public boolean scrap(int pageNumber) throws JSONException {

        System.out.println(pageName + " " + pageNumber);
        String url = pageName + "page-" + pageNumber + ".html";
        if (pageNumber == 1) {
            getLink(pageName);
        } else {
            if (!getLink(url)) {
                return false;
            }
        }
        return true;

    }

    public static String getFinalURL(String url) {
        try {
            String strUrl = java.net.URLDecoder.decode(url, "UTF-8");
            Connection.Response response = Jsoup.connect(strUrl).followRedirects(false).execute();
            if(response.header("location")!=null){
                 return response.header("location");
            }
            else{
                 return "true";
            }                      
        } catch (IOException ex) {
            Logger.getLogger(SGGPCN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " ";
    }

    public boolean checkValidLink(int pageNumber) {

        String url = pageName + "page-" + pageNumber + ".html";
        if (!getFinalURL(url).equals("true")) {
            return false;
        }
        return true;
    }

    public int getMaxPageNumber() throws UnsupportedEncodingException {
        System.out.println("Finding max page number");
        int result = 1000;
        int f = 1000;
        boolean pre = true;
        boolean reachInvalidLink = false;
        while (true) {

            if (checkValidLink(result)) {
                if (reachInvalidLink == true) {
                    f = f / 2;
                    result += f;
                } else {
                    result += f;
                }
                pre = true;
            } else {
                f = f / 2;
                result -= f;

                pre = false;
                reachInvalidLink = true;
            }

            System.out.println(result + " " + checkValidLink(result) + " " + f);
            if (f <= 5) {
                break;
            }
            if (f >= 50000 || f <= 0) {
                return -1;
            }
        }

        if (pre == true) {
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

    public boolean getLink(String page) {

        try {

            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();
            Elements div = doc1.select("#box-content section.zone.story-listing div.zone-content > article");
            if (div.isEmpty()) {
                return false;
            }

            System.out.println(page);
            System.out.println(div.size());
            for (Element i : div) {
                Article ar = new Article();
                ar.setLink(i.select("a[href]").first().attr("abs:href"));
                if (getDetails(ar)) {
                    articleSize++;
                    saveArticle(currentDirectory, ar);
                } else {
                    System.out.println("Skip " + ar.getLink());
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
            // kiem tra neu k co element thi no qua
            if (doc1.select("#box-content  h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("#box-content  h1").first().text());

            if (doc1.select("#box-content > article > div.article-content > div.content.cms-body").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("#box-content > article > div.article-content > div.content.cms-body").first().text());
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
                System.out.println(System.getProperty("user.dir") + "/" + name + " Directory is created!");
            } else {
                System.out.println(System.getProperty("user.dir") + "/" + name + " Failed to create directory!");
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
                    new FileOutputStream(System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"),
                    "utf-8"))) {

                writer.write(a.getLink() + "\n-----------------\n");
                writer.write(a.getTitle() + "\n-----------------\n");
                writer.write(a.getContent() + "\n-----------------\n");
                System.out.println("Created " + System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"
                        + " files Successfully");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SGGPCN v = new SGGPCN();
        v.init("http://cn.sggp.org.vn/時政/");
        System.out.println(v.getMaxPageNumber());
//        for (int i = 1;; i++) {
//            if (!v.scrap(i)) {
//                break;
//            }
//        }
//        System.out.println(v.articleSize);
//        //System.out.println(v.currentPage);
//         System.out.println(v.getMaxPageNumber());

    }
}
