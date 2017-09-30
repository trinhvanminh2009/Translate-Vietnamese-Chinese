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

public class NhanDanCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public NhanDanCN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://cn.nhandan.com.vn/leader":
                directory = "nhandanCN/leader";
                break;
            case "http://cn.nhandan.com.vn/political":
                directory = "nhandanCN/political";
                break;
            case "http://cn.nhandan.com.vn/international":
                directory = "nhandanCN/international";
                break;
            case "http://cn.nhandan.com.vn/economic":
                directory = "nhandanCN/economic";
                break;
            case "http://cn.nhandan.com.vn/society":
                directory = "nhandanCN/society";
                break;

            case "http://cn.nhandan.com.vn/culture":
                directory = "nhandanCN/culture";
                break;
            case "http://cn.nhandan.com.vn/sports":
                directory = "nhandanCN/sports";
                break;
            case "http://cn.nhandan.com.vn/tourism":
                directory = "nhandanCN/tourism";
                break;
            case "http://cn.nhandan.com.vn/friendshipbridge":
                directory = "nhandanCN/friendshipbridge";
                break;
                  case "http://cn.nhandan.com.vn/documentation":
                directory = "nhandanCN/documentation";
                break;

            case "http://cn.nhandan.com.vn/local":
                directory = "nhandanCN/local";
                break;
            case "http://cn.nhandan.com.vn/asean":
                directory = "nhandanCN/asean";
                break;
            case "http://cn.nhandan.com.vn/vietnamwindow":
                directory = "nhandanCN/vietnamwindow";
                break;
            case "http://cn.nhandan.com.vn/the-nation-of-vietnam":
                directory = "nhandanCN/the-nation-of-vietnam";
                break;
           

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download NhandanCN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download NhandanCN: " + pageName);
    }

    public int getPosition() {
        return position;
    }

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName, position);
        } else {
            getLink(pageName + "?limitstart=" + pageNumber * 15, position);
        }
    }

    public boolean checkValidLink(int pageNumber) {

        try {
            String url = pageName + "?limitstart=" + pageNumber * 15;
            Document doc1 = Jsoup.connect(url).userAgent("Mozilla").timeout(0).get();
            Elements div = doc1.select("div.container.cndcontainer > div:nth-child(1)> div:nth-child(6) >div.col-sm-8.col-xs-12 > div.col-sm-12.col-xs-12:last-child>div.col-sm-12.col-xs-12:not(:empty)");
            if (div.isEmpty()) {
                return false;
            }
            for (Element element : div) {
                if(!element.text().trim().isEmpty()){
                   return true;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(NhanDanVN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getMaxPageNumber() {
        System.out.println("Finding max page number");
        int result = 1000;
        int f = 1000;
        boolean reachInvalidLink = false;
        while (true) {
            System.out.println(f + " " + result);
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

            Elements div = doc1.select("div.container.cndcontainer > div:nth-child(1)> div:nth-child(6) >div.col-sm-8.col-xs-12 > div.col-sm-12.col-xs-12:last-child>div.col-sm-12.col-xs-12:not(:empty)");
            div.remove(0);
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
//                     System.out.println(div.get(i).select("a[href]").first().attr("abs:href"));
//                     System.exit(0);
                    if (getDetails(ar)) {
                        if (ar.getContent().length() > 100) {
                            articleSize++;
                            saveArticle(currentDirectory, ar);
                        }

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
            // kiem tra neu k co element thi no qua
            if (doc1.select("table > tbody > tr:nth-child(1)").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("table > tbody > tr:nth-child(1)").first().text());

            if (doc1.select("table > tbody > tr:nth-last-child(3)").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("table > tbody > tr:nth-last-child(3)").first().text());
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

    public static void main(String[] args) throws Exception {
        NhanDanCN v = new NhanDanCN();
        v.init("http://cn.nhandan.com.vn/the-nation-of-vietnam");
       // System.out.println(v.getMaxPageNumber());
        for (int i = 1;; i++) {
            v.scrap(i, 0);
        }

    }
}
