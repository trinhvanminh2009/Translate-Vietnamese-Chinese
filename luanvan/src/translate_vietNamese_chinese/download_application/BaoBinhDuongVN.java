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

public class BaoBinhDuongVN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;

    public BaoBinhDuongVN() {
        articleSize = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://baobinhduong.vn/chinh-tri":
                directory = "baobinhduongVN/chinhtri";
                break;
            case "http://baobinhduong.vn/kinh-te":
                directory = "baobinhduongVN/kinhte";
                break;
            case "http://baobinhduong.vn/quoc-te":
                directory = "baobinhduongVN/quocte";
                break;
            case "http://baobinhduong.vn/xa-hoi":
                directory = "baobinhduongVN/xahoi";
                break;
            case "http://baobinhduong.vn/the-thao":
                directory = "baobinhduongVN/thethao";
                break;

            case "http://baobinhduong.vn/phan-tich":
                directory = "baobinhduongVN/phantich";
                break;
            case "http://baobinhduong.vn/ban-doc":
                directory = "baobinhduongVN/bandoc";
                break;
            case "http://baobinhduong.vn/phap-luat":
                directory = "baobinhduongVN/phapluat";
                break;
            case "http://baobinhduong.vn/yte":
                directory = "baobinhduongVN/yte";
                break;
            case "http://baobinhduong.vn/van-hoa-van-nghe":
                directory = "baobinhduongVN/vanhoa-vannghe";
                break;
            case "http://baobinhduong.vn/doanh-nghiep-doanh-nhan":
                directory = "baobinhduongVN/doanhnghiep-doanhnhan";
                break;
            case "http://baobinhduong.vn/quoc-phong-an-ninh":
                directory = "baobinhduongVN/quocphong-anninh";
                break;
            case "http://baobinhduong.vn/gia-dinh":
                directory = "baobinhduongVN/giadinh";
                break;
            case "http://baobinhduong.vn/khoahoc-congnghe":
                directory = "baobinhduongVN/khoahoc-congnghe";
                break;
            case "http://baobinhduong.vn/oto-xemay":
                directory = "baobinhduongVN/oto-xemay";
                break;
            case "http://baobinhduong.vn/lao-dong":
                directory = "baobinhduongVN/laodong";
                break;
            case "http://baobinhduong.vn/ho-so-tu-lieu":
                directory = "baobinhduongVN/hoso-tulieu";
                break;
            case "http://baobinhduong.vn/moi-truong":
                directory = "baobinhduongVN/moi-truong";
                break;
                
                
             case "http://baobinhduong.vn/cn/vietnam-and-world-cn":
                directory = "baobinhduongCN/vietnam-and-world";
                break;
            case "http://baobinhduong.vn/cn/sea-islands-cn":
                directory = "baobinhduongCN/sea-islands";
                break;
            case "http://baobinhduong.vn/cn/politic-cn":
                directory = "baobinhduongCN/politic";
                break;
            case "http://baobinhduong.vn/cn/economic-cn":
                directory = "baobinhduongCN/economic";
                break;
            case "http://baobinhduong.vn/cn/international-cn":
                directory = "baobinhduongCN/international";
                break;
            case "http://baobinhduong.vn/cn/society-cn":
                directory = "baobinhduongCN/society";
                break;
            case "http://baobinhduong.vn/cn/health-cn":
                directory = "baobinhduongCN/health";
                break;
            case "http://baobinhduong.vn/cn/culture-cn":
                directory = "baobinhduongCN/culture";
                break;
            case "http://baobinhduong.vn/cn/policy-cn":
                directory = "baobinhduongCN/policy";
                break;
            case "http://baobinhduong.vn/cn/travel-cn":
                directory = "baobinhduongCN/travel";
                break;
            case "http://baobinhduong.vn/cn/technology-cn":
                directory = "baobinhduongCN/technology";
                break;
            case "http://baobinhduong.vn/cn/environment-cn":
                directory = "baobinhduongCN/environment";
                break;
            case "http://baobinhduong.vn/cn/realstate-cn":
                directory = "baobinhduongCN/realstate";
                break;

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download BaoBinhDuongVN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download BaoBinhDuongVN: " + pageName);
    }

    public boolean scrap(int pageNumber) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            getLink(pageName);
        } else {
            if (!getLink(pageName + "/?p=" + pageNumber + "&d=")) {
                return false;
            }
        }
        return true;
    }

    // nếu vượt qua số trang lớn nhất thì nó hiện số trang lớn nhất ở dưới góc
    public int getMaxPageNumber() {
        System.out.println("Finding max page number");
        int result = -1;
        int factor = 50000;
        int maxPage = 0;
        String link;
        try {
            while (true) {
                link = pageName + "/?p=" + factor + "&d=";
                Document doc = Jsoup.connect(link).userAgent("Mozilla").timeout(0).get();
                maxPage = Integer.parseInt(doc.select("div#page ul li:nth-last-child(3)").text());
                if (factor >= maxPage) {
                    result = maxPage;
                    break;
                }
                factor = factor * 2;

            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(BaoBinhDuongVN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int getArticleSize() {
        return articleSize;
    }

    public boolean getLink(String page) {

        try {
            Document doc1 = Jsoup.connect(page).timeout(0).get();
            Elements div = doc1.select("div.padding10 > div.left.w506.marginright10  > div.left.borderbottomdotted.paddingbottom10.paddingtop10");
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
                    System.out.println("Skip " +ar.getLink());
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
            if (doc1.select("div#listcate h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div#listcate h1").first().text());

            if (doc1.select("div#listcate  div#newscontents").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div#listcate  div#newscontents").first().text());
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
        BaoBinhDuongVN v = new BaoBinhDuongVN();
        v.init("http://baobinhduong.vn/xa-hoi");
        for (int i = 1;; i++) {
            if (!v.scrap(i)) {
                break;
            }
        }
        System.out.println(v.articleSize);
        //System.out.println(v.currentPage);
        // System.out.println(v.getMaxPageNumber());

    }

}
