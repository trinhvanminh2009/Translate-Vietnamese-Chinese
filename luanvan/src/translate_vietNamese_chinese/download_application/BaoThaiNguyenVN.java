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

public class BaoThaiNguyenVN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public BaoThaiNguyenVN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://baothainguyen.org.vn/chuyen-muc/thoi-su-trong-tinh_205.html":
                directory = "baothainguyenVN/thoisu-trongtinh";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/chinh-tri_97.html":
                directory = "baothainguyenVN/chinhtri";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/kinh-te_108.html":
                directory = "baothainguyenVN/kinhte";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/xa-hoi_85.html":
                directory = "baothainguyenVN/xahoi";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/van-hoa_98.html":
                directory = "baothainguyenVN/vanhoa";
                break;

            case "http://baothainguyen.org.vn/chuyen-muc/giao-duc_100.html":
                directory = "baothainguyenVN/giaoduc";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/phap-luat_101.html":
                directory = "baothainguyenVN/phapluat";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/the-thao_83.html":
                directory = "baothainguyenVN/thethao";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/khoa-hoc-cn_99.html":
                directory = "baothainguyenVN/khoahoc-congnghe";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/giao-thong_103.html":
                directory = "baothainguyenVN/giaothong";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/o-to-xe-may_110.html":
                directory = "baothainguyenVN/o-to-xe-may";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/net-dep-doi-thuong_116.html":
                directory = "baothainguyenVN/net-dep-doi-thuong";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/van-ban-chinh-sach-moi_184.html":
                directory = "baothainguyenVN/van-ban-chinh-sach-moi";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/thong-tin-quang-cao_38.html":
                directory = "baothainguyenVN/thong-tin-quang-cao";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/dat-va-nguoi-thai-nguyen_40.html":
                directory = "baothainguyenVN/dat-va-nguoi-thai-nguyen";
                break;
            case "http://baothainguyen.org.vn/chuyen-muc/que-huong-dat-nuoc_104.html":
                directory = "baothainguyenVN/que-huong-dat-nuoc";
                break;

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download BaoThaiNguyenVN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download BaoThaiNguyenVN: " + pageName);
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
        BaoThaiNguyenVN v = new BaoThaiNguyenVN();
        v.init("http://baothainguyen.org.vn/chuyen-muc/thoi-su-trong-tinh_205.html");
         // System.out.println(v.getMaxPageNumber());
        for (int i = 1;i<10; i++) {
            v.scrap(i, 0);
        }
        System.out.println(v.articleSize);
      
    }
}
