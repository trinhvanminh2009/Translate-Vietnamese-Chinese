package translate_vietNamese_chinese.download_application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                ArrayList<String[]> obj=new ArrayList<>();
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

    public boolean getDetails(String link, ArrayList<String[]> obj) {

        try {
            Document doc1 = Jsoup.connect(link).timeout(0).get();
            Elements div = doc1.select("#jump-article");
            Elements strong = div.select("> strong");
            
            Elements table = doc1.select("#container > main > article> section.az-right-box >div.content >table.az-facts tr");
            
            Element name = div.select("> h2").first();
            obj.add(new String[]{"name",name.text()});
            
            for (Element element : table) {
                if(element.select("td > b> a").isEmpty()){
                    continue;
                }
                String td1=element.select("td > b> a").get(0).text();
                String td2=element.select("td").get(1).text();
                if("Kingdom".equals(td1)){
                      obj.add(new String[]{"kingdom",td2});
                }
                else if("Class".equals(td1)){
                      obj.add(new String[]{"class",td2});                   
                }
                else if(td1.contains("Size (")){
                    obj.add(new String[]{"size",td2});    
                }
                else if("Weight".equals(td1)||"Average Weight".equals(td1)){
                    obj.add(new String[]{"weight",td2});
                }
                else if("Average Life Span".equals(td1)||"Life Span".equals(td1)){
                    obj.add(new String[]{"life span",td2});
                }
            }

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
            obj.add(new String[]{"decription",all});
            } else {
                Pattern MY_PATTERN = Pattern.compile("(!!@)(.*?)(@!!)");
                Matcher m = MY_PATTERN.matcher(all);

                int preEnd = -1;
                String preSubject = "";
                while (m.find()) {
                    if (preEnd != -1) {
                        obj.add(new String[]{preSubject,all.substring(preEnd, m.start())});
                    }
                    preEnd = m.end();
                    preSubject = m.group(2);
                }
                       obj.add(new String[]{preSubject,all.substring(preEnd)});
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

    public void save(String name, ArrayList<String[]> obj) throws UnsupportedEncodingException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(System.getProperty("user.dir") + "/" + name + "/" + articleSize + ".txt"), "utf-8"))) {
            for (String[] strings : obj) {
                if(strings[0]=="kingdom"||strings[0]=="class"||strings[0]=="size"||strings[0]=="weight"||strings[0]=="life span"||strings[0]=="name"){
                  writer.write("<"+strings[0]+">"+strings[1]+"</"+strings[0]+">\n");                     
                }
                else{
                 writer.write("<strong>"+strings[0]+"</strong>\n"+strings[1]+"\n");  
                }
            }           
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