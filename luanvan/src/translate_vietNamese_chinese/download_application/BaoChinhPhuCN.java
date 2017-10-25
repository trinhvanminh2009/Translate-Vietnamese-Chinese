package translate_vietNamese_chinese.download_application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BaoChinhPhuCN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public BaoChinhPhuCN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://cn.news.chinhphu.vn/Home/hoat-dong-dang-cp.vgp":
                directory = "baochinhphuCN/Government activity";
                break;
            case "http://cn.news.chinhphu.vn/Home/thu-tuong-chinh-phu.vgp":
                directory = "baochinhphuCN/Prime minister";
                break;
            case "http://cn.news.chinhphu.vn/Home/chi-dao-ttcp.vgp":
                directory = "baochinhphuCN/Government prime minister guidance and decision";
                break;
            case "http://cn.news.chinhphu.vn/Home/tin-bo-nganh.vgp":
                directory = "baochinhphuCN/Government department activities";
                break;
            case "http://cn.news.chinhphu.vn/Home/tin-dia-phuong.vgp":
                directory = "baochinhphuCN/Provinces and cities activities";
                break;

            case "http://cn.news.chinhphu.vn/Home/kinhte-xahoi.vgp":
                directory = "baochinhphuCN/Economic Society";
                break;
            case "http://cn.news.chinhphu.vn/Home/kh-cn.vgp":
                directory = "baochinhphuCN/Science & Technology";
                break;               
                 case "http://cn.news.chinhphu.vn/Home/vanhoa-dulich.vgp":
                directory = "baochinhphuCN/Cultural Tourism";
                break;
            case "http://cn.news.chinhphu.vn/Home/thegioi-vietnam.vgp":
                directory = "baochinhphuCN/Vietnam and the world";
                break;
            case "http://cn.news.chinhphu.vn/Home/thongtin-kinhte-xahoi.vgp":
                directory = "baochinhphuCN/Statistical Bulletin of National Economic and Social Development";
                break;
        

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download BaoChinhPhuCN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download BaoChinhPhuCN: " + pageName);
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
            getLink(name + "/trang-" + pageNumber + ".vgp", position);
        }
    }

    // nó hiện số trang lớn nhất ở dưới góc
    public int getMaxPageNumber() {
        int maxPage=-1;
        try {
            System.out.println("Finding max page number");           
            Document doc = Jsoup.connect(pageName).userAgent("Mozilla").timeout(0).get();
            String number=doc.select("div.pageViewNo a.pageViewNoItem").last().text();           
            maxPage = Integer.parseInt(number);                     
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

            Elements div = doc1.select("div.contentBodyCenter > div.catItemBody");
            int divSize = div.size();
            System.out.println(page);
            System.out.println(divSize);
            Article ar;
            for (int i = position; i < divSize; i++) {
                 this.position = i;
                if (ScrapingThread.stop == true) {
                   
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
            String url=URLDecoder.decode(ar.getLink(), "UTF-8");
            Document doc1 = Jsoup.connect(url).timeout(0).get();
            // kiem tra neu k co element thi no qua
            if (doc1.select("div.contentBodyCenter > div.dtContentHl").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div.contentBodyCenter > div.dtContentHl").first().text());

            if (doc1.select("div.contentBodyCenter > div.dtContentTxt").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.contentBodyCenter > div.dtContentTxt").first().text());
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
        BaoChinhPhuCN v = new BaoChinhPhuCN();
        v.init("http://cn.news.chinhphu.vn/Home/hoat-dong-dang-cp.vgp");
         System.out.println(v.getMaxPageNumber());
        for (int i = 1;i<10; i++) {
            v.scrap(i, 0);
        }
        System.out.println(v.articleSize);
      
    }
}
