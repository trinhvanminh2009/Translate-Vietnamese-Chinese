package translate_vietNamese_chinese.download_application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VietNamPlusCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private final String USER_AGENT = "Mozilla/5.0";

    public VietNamPlusCN() {
        articleSize = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            

            case "http://zh.vietnamplus.vn/politics.vnp":
                directory = "vietnamplusCN/politics";
                break;
            case "http://zh.vietnamplus.vn/world.vnp":
                directory = "vietnamplusCN/world";
                break;
            case "http://zh.vietnamplus.vn/business.vnp":
                directory = "vietnamplusCN/business";
                break;
            case "http://zh.vietnamplus.vn/social.vnp":
                directory = "vietnamplusCN/social";
                break;
            case "http://zh.vietnamplus.vn/culture.vnp":
                directory = "vietnamplusCN/culture";
                break;
            case "http://zh.vietnamplus.vn/sports.vnp":
                directory = "vietnamplusCN/sports";
                break;
            case "http://zh.vietnamplus.vn/technology.vnp":
                directory = "vietnamplusCN/technology";
                break;
            case "http://zh.vietnamplus.vn/environment.vnp":
                directory = "vietnamplusCN/environment";
                break;
            case "http://zh.vietnamplus.vn/Travel.vnp":
                directory = "vietnamplusCN/travel";
                break;
        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        try {
            currentDirectory = getCurrentDirectory(pageName);
            String url = java.net.URLDecoder.decode(pageName,"UTF-8");
            this.pageName = url;
            this.articleSize = articleSize;
            makeDirectory(currentDirectory, false);
            System.out.println("Start download VietNamPlus: " + pageName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VietNamPlusCN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(String pageName) {
        try {
            currentDirectory = getCurrentDirectory(pageName);
            String url = java.net.URLDecoder.decode(pageName,"UTF-8");
            this.pageName = url;
            makeDirectory(currentDirectory, true);
            System.out.println("Start download VietNamPlus: " + pageName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VietNamPlusCN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean scrap(int pageNumber) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName);
        } else {
            String name = pageName.substring(0, pageName.length() - 4);
            if (!getLink(name + "/page" + pageNumber + ".vnp")) {
                return false;
            }
        }
        return true;
    }

    public int getMaxPageNumber() {
        int countPage = -1;
        try {
            Document doc = Jsoup.connect(pageName).timeout(0).get();
            Elements lista = doc.select("#ctl00_mainContent_ctl00_ContentList1_pager ul a[href]");
            countPage = Integer.parseInt(lista.last().text());
        } catch (IOException ex) {
            Logger.getLogger(VietNamPlusVN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countPage;
    }

    public static String getFinalURL(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setInstanceFollowRedirects(false);
            con.connect();
            con.getInputStream();

            if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                String redirectUrl = con.getHeaderField("Location");
                return getFinalURL(redirectUrl);
            }
        } catch (MalformedURLException ex) {
            return url;
        } catch (IOException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    public int getArticleSize() {
        return articleSize;
    }

    public boolean getLink(String page) {

        try {
            Document doc1 = Jsoup.connect(page).timeout(0).get();
            Elements div = doc1.select("div.story-listing.slist-03 > article");
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
            Document doc1 = Jsoup.connect(ar.getLink()).timeout(0).get();
            // kiem tra neu k co element thi no qua
            if (doc1.select("header.article-header >h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("header.article-header >h1").first().text());

            if (doc1.select("div.article-body").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.article-body").first().text());
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
        VietNamPlusCN v = new VietNamPlusCN();
         
        v.init("http://zh.vietnamplus.vn/politics.vnp");
        System.out.println(v.getMaxPageNumber());
		 for (int i = 1;; i++) {
		 if (!v.scrap(i)) {
		 break;
		 }
		 }
		 System.out.println(v.articleSize);
        System.out.println(v.getMaxPageNumber());
    }

}
