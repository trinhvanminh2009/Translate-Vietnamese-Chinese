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

public class TapChiCongSanVN {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private int position;

    public TapChiCongSanVN() {
        articleSize = 0;
        position = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "http://www.tapchicongsan.org.vn/Home/xay-dung-dang.aspx":
                directory = "tapchicongsanVN/chinhtri";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Nghiencuu-Traodoi.aspx":
                directory = "tapchicongsanVN/nghiencuu";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Binh-luan.aspx":
                directory = "tapchicongsanVN/binhluan";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Thong-tin-ly-luan.aspx":
                directory = "tapchicongsanVN/thongtinlyluan";
                break;
            case "http://www.tapchicongsan.org.vn/Home/The-gioi-van-de-su-kien.aspx":
                directory = "tapchicongsanVN/thegioi";
                break;

            case "http://www.tapchicongsan.org.vn/Home/kinh-te.aspx":
                directory = "tapchicongsanVN/kinhte";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Van-hoa-xa-hoi.aspx":
                directory = "tapchicongsanVN/vanhoa";
                break;
            case "http://www.tapchicongsan.org.vn/Home/An-ninh-quoc-phong.aspx":
                directory = "tapchicongsanVN/anninh";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Doi-ngoai-va-hoi-nhap.aspx":
                directory = "tapchicongsanVN/doingoai";
                break;
            case "http://www.tapchicongsan.org.vn/Home/Sinh-hoat-tu-tuong.aspx":
                directory = "tapchicongsanVN/sinhhoat-tutuong";
                break;

        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory, false);
        System.out.println("Start download TapChiCongSanVN: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory, true);
        System.out.println("Start download TapChiCongSanVN: " + pageName);
    }

    public int getArticleSize() {
        return articleSize;
    }

    public int getPosition() {
        return position;
    }

    public void scrap(int pageNumber, int position) throws JSONException {
        System.out.println(pageName + " " + pageNumber);

        if (pageNumber == 1) {
            //page number start at 0
            getLink(pageName, position);
            getLink(pageName + "?page=1", position);
        } else {
            getLink(pageName + "?page=" + pageNumber, position);
        }
    }

    public boolean checkValidLink(int pageNumber) {
        try {
            Document doc1 = Jsoup.connect(pageName + "?page=" + pageNumber).userAgent("Mozilla").timeout(0).get();
            
            Elements div = doc1.select("div.zonepage > div.zonepage-l > div.zoneteaser ul li:not(.sep)");
           if(div.isEmpty()){
               return false;
           }
        } catch (IOException ex) {
            Logger.getLogger(TapChiCongSanVN.class.getName()).log(Level.SEVERE, null, ex);
        }
         return true;
    }

    public int getMaxPageNumber() {
        System.out.println("Finding max page number");
        int result = 1000;
        int f = 1000;
        boolean reachInvalidLink = false;
        while (true) {

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

    public void getLink(String page, int position) {
        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("div.zonepage > div.zonepage-l > div.zoneteaser ul li:not(.sep)");
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
//                      System.exit(0);
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
            if (doc1.select("div.zonepage > div.zonepage-l >div.story_detail div.story_head span.headline").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div.zonepage > div.zonepage-l >div.story_detail div.story_head span.headline").first().text());

            if (doc1.select("div.zonepage > div.zonepage-l >div.story_detail > div.story_body").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.zonepage > div.zonepage-l >div.story_detail > div.story_body").first().text());
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
        TapChiCongSanVN v = new TapChiCongSanVN();
        v.init("http://www.tapchicongsan.org.vn/Home/xay-dung-dang.aspx");
        System.out.println(v.getMaxPageNumber());
        for (int i = 1;i<10; i++) {
            v.scrap(i, 0);
        }
        System.out.println(v.articleSize);
        System.out.println(v.getMaxPageNumber());
    }
}
