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

public class BaoChinhPhuVN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public BaoChinhPhuVN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://baochinhphu.vn/Chinh-tri/442.vgp":
                directory = "baochinhphuVN/chinhtri";
                break;
            case "http://baochinhphu.vn/Kinh-te/7.vgp":
                directory = "baochinhphuVN/kinhte";
                break;
            case "http://baochinhphu.vn/Van-hoa/249.vgp":
                directory = "baochinhphuVN/vanhoa";
                break;
            case "http://baochinhphu.vn/Xa-hoi/449.vgp":
                directory = "baochinhphuVN/xahoi";
                break;
            case "http://baochinhphu.vn/Khoa-giao/451.vgp":
                directory = "baochinhphuVN/khoagiao";
                break;

            case "http://baochinhphu.vn/Quoc-te/6.vgp":
                directory = "baochinhphuVN/quocte";
                break;
            case "http://baochinhphu.vn/Hoi-nhap/453.vgp":
                directory = "baochinhphuVN/hoinhap";
                break;
        

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download BaoChinhPhuVN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download BaoChinhPhuVN: " + pageName);
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
            getLink(name + "/trang" + pageNumber + ".vgp", position);
        }
    }

    // nó hiện số trang lớn nhất ở dưới góc
    public int getMaxPageNumber() {
        int maxPage=-1;
        try {
            System.out.println("Finding max page number");           
            Document doc = Jsoup.connect(pageName).userAgent("Mozilla").timeout(0).get();
            String number=doc.select("#ctl00_leftContent_ctl01_pager_lastControl").last().attr("href");
            String[] arrStr=number.split("/trang");
            arrStr=arrStr[1].split("\\.");
            maxPage = Integer.parseInt(arrStr[0]);                     
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

            Elements div = doc1.select("div.maincontents > div.zonelisting >div.story");
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
            if (doc1.select("div.maincontents div.article-header h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div.maincontents div.article-header h1").first().text());

            if (doc1.select("div.maincontents div.wrap-article div.article-body").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.maincontents div.wrap-article div.article-body").first().text());
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
        BaoChinhPhuVN v = new BaoChinhPhuVN();
        v.init("http://baochinhphu.vn/Chinh-tri/442.vgp");
         System.out.println(v.getMaxPageNumber());
        for (int i = 1;i<10; i++) {
            v.scrap(i, 0);
        }
        System.out.println(v.articleSize);
      
    }
}
