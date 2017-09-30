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

public class VietNamPlusCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
     private int position;

    public VietNamPlusCN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            

            case "https://zh.vietnamplus.vn/politics.vnp":
                directory = "vietnamplusCN/politics";
                break;
            case "https://zh.vietnamplus.vn/world.vnp":
                directory = "vietnamplusCN/world";
                break;
            case "https://zh.vietnamplus.vn/business.vnp":
                directory = "vietnamplusCN/business";
                break;
            case "https://zh.vietnamplus.vn/social.vnp":
                directory = "vietnamplusCN/social";
                break;
            case "https://zh.vietnamplus.vn/culture.vnp":
                directory = "vietnamplusCN/culture";
                break;
            case "https://zh.vietnamplus.vn/sports.vnp":
                directory = "vietnamplusCN/sports";
                break;
            case "https://zh.vietnamplus.vn/technology.vnp":
                directory = "vietnamplusCN/technology";
                break;
            case "https://zh.vietnamplus.vn/environment.vnp":
                directory = "vietnamplusCN/environment";
                break;
            case "https://zh.vietnamplus.vn/Travel.vnp":
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
    public int getPosition() {
        return position;
    }

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName, position);
        } else {
            String name = pageName.substring(0, pageName.length() - 4);
            getLink(name + "/page" + pageNumber + ".vnp", position);
        }
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

    public int getArticleSize() {
        return articleSize;
    }
 public void getLink(String page, int position) {
        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("div.story-listing.slist-03 > article");
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
