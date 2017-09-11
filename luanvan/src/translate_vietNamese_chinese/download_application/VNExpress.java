/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese.download_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VNExpress {

    private String currentDirectory;
    private String pageName;
    private int articleSize;
    private final String USER_AGENT = "Mozilla/5.0";

    public VNExpress() {
        articleSize = 0;
        currentDirectory = "";
    }

    public static String getCurrentDirectory(String pageName) {
        String directory = "";
        switch (pageName) {
            case "https://vnexpress.net/tin-tuc/thoi-su":
                directory = "vnexpress_tintuc_thoisu";
                break;
            case "https://giaitri.vnexpress.net":
                directory = "vnexpress_giaitri";
                break;
            case "https://vnexpress.net/tin-tuc/phap-luat":
                directory = "vnexpress_tintuc_phapluat";
                break;
            case "https://vnexpress.net/tin-tuc/giao-duc":
                directory = "vnexpress_tintuc_giaoduc";
                break;
            case "https://vnexpress.net/tin-tuc/oto-xe-may":
                directory = "vnexpress_tintuc_otoxemay";
                break;
            case "https://vnexpress.net/tin-tuc/cong-dong":
                directory = "vnexpress_tintuc_congdong";
                break;
            case "https://vnexpress.net/tin-tuc/tam-su":
                directory = "vnexpress_tintuc_tamsu";
                break;
            case "https://vnexpress.net/tin-tuc/the-gioi":
                directory = "vnexpress_tintuc_the-gioi";
                break;
            case "https://kinhdoanh.vnexpress.net":
                directory = "vnexpress_kinhdoanh";
                break;
            case "https://thethao.vnexpress.net":
                directory = "vnexpress_thethao";
                break;
            case "https://suckhoe.vnexpress.net/tin-tuc/suc-khoe":
                directory = "vnexpress_suckhoe";
                break;
            case "https://giadinh.vnexpress.net":
                directory = "vnexpress_giadinh";
                break;
            case "https://dulich.vnexpress.net":
                directory = "vnexpress_dulich";
                break;
            case "https://vnexpress.net/tin-tuc/khoa-hoc":
                directory = "vnexpress_tintuc_khoahoc";
                break;
            case "https://sohoa.vnexpress.net":
                directory = "vnexpress_sohoa";
                break;
            case "https://vnexpress.net/tin-tuc/goc-nhin":
                directory = "vnexpress_gocnhin";
                break;
            case "https://vnexpress.net/tin-tuc/cuoi":
                directory = "vnexpress_tintuc_cuoi";
                break;      
        }
        return directory;
    }

    public void init(String pageName, int articleSize) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        this.articleSize = articleSize;
        makeDirectory(currentDirectory,false);
        System.out.println("Start download VNExpress: " + pageName);
    }

    public void init(String pageName) {
        currentDirectory = getCurrentDirectory(pageName);
        this.pageName = pageName;
        makeDirectory(currentDirectory,true);
        System.out.println("Start download VNExpress: " + pageName);
    }

    public boolean scrapAjaxPage(int pageNumber) {
        try {
            String url = "https://vnexpress.net/tin-tuc/goc-nhin?category_id=1003450&page=" + pageNumber + "&exclude=3&rule=2";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                   System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject jsonObject = new JSONObject(response.toString());
            if (!jsonObject.get("error").equals(1)) {
                return false;
            }
            JSONArray arr = jsonObject.getJSONArray("message");
            System.out.println(arr.length());
            for (int i = 0; i < arr.length(); i++) {
                Article ar = new Article();
                ar.setLink(arr.getJSONObject(i).getString("share_url"));
                System.out.println(arr.getJSONObject(i).getString("share_url"));
                if (getDetailsAjaxPage(ar)) {
                    articleSize++;
                    saveArticle(currentDirectory, ar);
                } else {
                    System.out.println("Skip");
                }
            }

            //print result
            //System.out.println(response.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean getDetailsAjaxPage(Article ar) {
        try {
            Document doc1 = Jsoup.connect(ar.getLink()).userAgent("Mozilla").timeout(0).get();
            //kiem tra neu k co element thi no qua
            if (doc1.select("article#article_detail h1.title_gn_detail").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("article#article_detail h1.title_gn_detail").first().text());
//            System.out.println(doc1.select("div#container div.title_news h1").first().text());
//                System.exit(0);
            if (doc1.select("div.article_body div.fck_detail").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div.article_body div.fck_detail").first().text());
//            System.out.println(doc1.select("div#container div#left_calculator div.fck_detail.width_common.block_ads_connect").first().text());
//            System.exit(0);
            return true;

        } catch (Exception ex) {
            System.out.println("This link error");
            System.out.println(ar.getLink());
            return false;
        }
    }

    public boolean scrap(int pageNumber) throws JSONException {
        System.out.println(pageName+" "+pageNumber);
        if (pageName.equals("https://vnexpress.net/tin-tuc/goc-nhin")) {
            if (!scrapAjaxPage(pageNumber)) {
                return false;
            }
        } else {
            if (pageNumber == 1) {
                getLink(pageName);
            } else {
                if (!getLink(pageName + "/page/" + pageNumber + ".html")) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkValidLink(int pageNumber) {
        if (pageName.equals("https://vnexpress.net/tin-tuc/goc-nhin")) {
            try {
                String url = "https://vnexpress.net/tin-tuc/goc-nhin?category_id=1003450&page=" + pageNumber + "&exclude=3&rule=2";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    //   System.out.println(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                if (!jsonObject.get("error").equals(1)) {
                    return false;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String link = pageName + "/page/" + pageNumber + ".html";
            String originalUrl = null;
            try {
                originalUrl = Jsoup.connect(link).userAgent("Mozilla")
                        .followRedirects(true) //to follow redirects
                        .execute().url().toExternalForm();
            } catch (IOException ex) {
                Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(originalUrl);
            try {
                Document doc1 = Jsoup.connect(link).userAgent("Mozilla").timeout(0).get();

                Elements div = doc1.select("section.container section.sidebar_1 > article.list_news");
                if (div.isEmpty() || !originalUrl.equals(link)) {
                    return false;
                }

            } catch (Exception ex) {
            }
        }

        return true;
    }

    public int getMaxPageNumber() {
        int result = 1;
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
        }
        if (pre == true) {
            for (int i = result; i <= result + 5; i++) {
                if (!checkValidLink(i)) {
                    return i - 1;
                }
            }
        } else {
            for (int i = result; i >= result - 5; i--) {
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
//     public int getCurrentPage() {
//        return currentPage;
//    }

    public boolean getLink(String page) {
        String originalUrl = null;
        try {
            originalUrl = Jsoup.connect(page).userAgent("Mozilla")
                    .followRedirects(true) //to follow redirects
                    .execute().url().toExternalForm();
        } catch (IOException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(originalUrl);
        try {
            Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

            Elements div = doc1.select("section.container section.sidebar_1 > article.list_news");
            if (div.isEmpty() || !originalUrl.equals(page)) {
                //System.out.println(div.isEmpty()+" "+!originalUrl.equals(page));
                return false;
            }

            System.out.println(page);
            System.out.println(div.size());
            for (Element i : div) {
                Article ar = new Article();
                ar.setLink(i.select("h3 a[href]").first().attr("abs:href"));
                //System.out.println(i.select("h3 a[href]").first().attr("abs:href"));
                // System.exit(0);
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
            Document doc1 = Jsoup.connect(ar.getLink()).userAgent("Mozilla").timeout(0).get();
            //kiem tra neu k co element thi no qua
            if (doc1.select("section.container section.sidebar_1 > h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("section.container section.sidebar_1 > h1").first().text());
//            System.out.println(doc1.select("section.container section.sidebar_1 > h1").first().text());
//                System.exit(0);
            if (doc1.select("section.container section.sidebar_1 > article").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("section.container section.sidebar_1 > article").first().text());
//            System.out.println(doc1.select("section.container section.sidebar_1 > article").first().text());
//            System.exit(0);
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
            if (file.mkdir()) {
                System.out.println(name + " Directory is created!");
            } else {
                System.out.println(name + " Failed to create directory!");
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
                    new FileOutputStream(System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"), "utf-8"))) {

                writer.write(a.getLink() + "\n-----------------\n");
                writer.write(a.getTitle() + "\n-----------------\n");
                writer.write(a.getContent() + "\n-----------------\n");
                System.out.println("Created " + System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt" + " files Successfully");
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        VNExpress v = new VNExpress();
//        v.init("https://vnexpress.net/tin-tuc/thoi-su");
//        for (int i = 1;; i++) {
//            if (!v.scrap(i)) {
//                break;
//            }
//        }
//        System.out.println(v.articleSize);
//        //System.out.println(v.currentPage);
//        System.out.println("Done");

        VNExpress v = new VNExpress();
        v.init("https://vnexpress.net/tin-tuc/thoi-su");
        //System.out.println(v.getMaxPageNumber());
        System.out.println(v.checkValidLink(3213));
    }

}
