/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese.download_application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Quang
 */
public class Animals {

    private String currentDirectory;
    private String pageName;
    private int articleSize;

    public Animals() {
        articleSize = 0;
        currentDirectory = "";
    }

    public void init(String pageName, String folderName) {
        currentDirectory = folderName;
        this.pageName = pageName;
        makeDirectory(folderName);
    }

    public boolean scrap() {
        getLink(pageName);
        return true;
    }

    public boolean getLink(String page) {
        try {
            Document doc1 = Jsoup.connect(page).get();
            Elements div = doc1.select("#container > main > article > div.az-left-box.az-animals-index > div.content > div.letter > ul > li > a");
            System.out.println(page);
            System.out.println(div.size());
            for (Element i : div) {
                JSONObject obj = new JSONObject();
                if (getDetails(i.attr("abs:href"), obj)) {
                    articleSize++;
                    save(currentDirectory, obj);
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

    public boolean getDetails(String link, JSONObject obj) {

        try {
            Document doc1 = Jsoup.connect(link).timeout(0).get();
            Elements div = doc1.select("#jump-article");
            Elements strong = div.select("> strong");

            Element name = div.select("> h2").first();
            obj.put("name", name.text());

            Element section = div.select("> section").first();
            section.remove();
            Element h2 = div.select("> h2").first();
            h2.remove();
            Element share = div.select("> div.share").first();
            share.remove();

            for (Element element : strong) {
                element.text("!!@" + element.text() + "@!!");
            }

            String all = div.text();

            if (strong.isEmpty()) {
                obj.put("all", all);
            } else {
                Pattern MY_PATTERN = Pattern.compile("(!!@)(.*?)(@!!)");
                Matcher m = MY_PATTERN.matcher(all);

                int preEnd = -1;
                String preSubject = "";
                while (m.find()) {
                    if (preEnd != -1) {
                        obj.put(preSubject, all.substring(preEnd, m.start()));
                    }
                    preEnd = m.end();
                    preSubject = m.group(2);
                }
                obj.put(preSubject, all.substring(preEnd));
            }

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public void makeDirectory(String name) {
        File file = new File(System.getProperty("user.dir") + "/" + name);
        if (!file.exists()) {
            if (file.mkdir()) {
                currentDirectory = name;
                System.out.println(name + " Directory is created!");
            } else {
                currentDirectory = "";
                System.out.println(name + " Failed to create directory!");
            }
        } else {
            currentDirectory = name;
        }
    }

    public void save(String name, JSONObject obj) throws UnsupportedEncodingException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"), "utf-8"))) {

            writer.write(obj.toString());
            System.out.println("Created " + System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt" + " files Successfully");
        }
    }

    public static void main(String[] args) {
        Animals v = new Animals();
        v.init("https://a-z-animals.com/animals/", "animals");
        v.scrap();
        System.out.println("Done");
    }
}
