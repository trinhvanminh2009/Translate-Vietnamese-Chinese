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

public class BaoThaiNguyenCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public BaoThaiNguyenCN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://baothainguyen.org.vn/chuyen-muc/tieng-trung-quoc_31.html":
                directory = "baothainguyenCN/Chinese";
                break;
            

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download BaoThaiNguyenCN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download BaoThaiNguyenCN: " + pageName);
    }

    public int getPosition() {
        return position;
    }

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName, position);
        } else {
            getLink(pageName + "?Page=" + pageNumber, position);
        }
    }

    // nó hiện số trang lớn nhất ở dưới góc
    public int getMaxPageNumber() {
        int maxPage=-1;
        try {
            System.out.println("Finding max page number");           
            Document doc = Jsoup.connect(pageName).userAgent("Mozilla").timeout(0).get();
            maxPage = Integer.parseInt(doc.select("ul.page_view a[href]").last().attr("href").split("=")[1]);                     
        } catch (IOException ex) {
            Logger.getLogger(BaoThaiNguyenVN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxPage;
    }

    public int getArticleSize() {
        return articleSize;
    }

    public void getLink(String page, int position) {
        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("div.col_cm > div.info_news_cm_2");
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
            if (doc1.select("div.title_detail a").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div.title_detail a").first().text());

            if (doc1.select("div.news_detail").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.news_detail").first().text());
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

    public static void main(String[] args) throws JSONException {
        BaoThaiNguyenCN v = new BaoThaiNguyenCN();
        v.init("http://baothainguyen.org.vn/chuyen-muc/tieng-trung-quoc_31.html");
         // System.out.println(v.getMaxPageNumber());
        for (int i = 1;i<10; i++) {
            v.scrap(i, 0);
        }
        System.out.println(v.articleSize);
      
    }
}
