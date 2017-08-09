/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translate_vietNamese_chinese;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class VNExpress {

    private ArrayList<Article> articles;

    public VNExpress() {
        articles = new ArrayList<>();
    }

    public void scrap(String pageName) throws IOException {
        getAllLink(pageName);
        System.out.println(articles.size());

    }

    public void getAllLink(String pageName) {
     

        for (int i = 1;; i++) {
            if (i == 1) {
                getLink(pageName);
            } else {
                if (!getLink(pageName + "/page/" + i + ".html")) {
                    break;
                }
            }

        }
    }

    public boolean getLink(String page) {
           String originalUrl = null;
        try {
            originalUrl = Jsoup.connect(page)
                    .followRedirects(true) //to follow redirects
                    .execute().url().toExternalForm();
        } catch (IOException ex) {
            Logger.getLogger(VNExpress.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(originalUrl);

        try {
            Document doc1 = Jsoup.connect(page).timeout(0).get();

            Elements div = doc1.select("div#container ul#news_home>li");
            if (div.isEmpty()||!originalUrl.equals(page)) {
                return false;
            }
            
            System.out.println(page);
            System.out.println(div.size());
            for (Element i : div) {
                Article ar = new Article();
                ar.setLink(i.select("h3 a[href]").first().attr("abs:href"));
                System.out.println(i.select("h3 a[href]").first().attr("abs:href"));
//                System.exit(0);
                if (getDetails(ar)) {
                    articles.add(ar);
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
            Document doc1 = Jsoup.connect(ar.getLink()).timeout(0).get();
            //kiem tra neu k co element thi no qua
            if (doc1.select("div#container div.title_news h1").isEmpty()) {
                return false;
            }
            ar.setTitle(doc1.select("div#container div.title_news h1").first().text());
//            System.out.println(doc1.select("div#container div.title_news h1").first().text());
//                System.exit(0);
            if (doc1.select("div#container div#left_calculator div.fck_detail.width_common.block_ads_connect").isEmpty()) {
                return false;
            }
            ar.setContent(doc1.select("div#container div#left_calculator div.fck_detail.width_common.block_ads_connect").first().text());
//            System.out.println(doc1.select("div#container div#left_calculator div.fck_detail.width_common.block_ads_connect").first().text());
//            System.exit(0);
            return true;

        } catch (Exception ex) {
        	 System.out.println("This link error");
             System.out.println(ar.getLink());
            return false;
        }
    }
    
    public void downloadVNExpress(String page, String name)throws IOException, Exception {
    
    	   VNExpress s = new VNExpress();
           s.scrap(page);
           File file = new File(System.getProperty("user.dir") + "/"+name);
           if (!file.exists()) {
               if (file.mkdir()) {
                   System.out.println("Directory is created!");
               } else {
                   System.out.println("Failed to create directory!");
               }
           }

           int i = 1;
           int j = 0;
           for (Article a : s.articles) {
        	   if(a.getTitle()!= null && a.getContent().length()>100 && a.getLink() != null )
        	   {
        		   try (Writer writer = new BufferedWriter(new OutputStreamWriter(
   	                    new FileOutputStream(System.getProperty("user.dir") + "/"+name+"/"+i+".txt"), "utf-8"))) {
   	                
   	                	writer.write(a.getLink()+"\n-----------------\n");
   	                	 writer.write(a.getTitle()+"\n-----------------\n");
   	                     writer.write(a.getContent()+"\n-----------------\n");
   	                     j++;
   	                     i++;
   	                } 
        	   }
               
           }
           System.out.println("Created "+j+ " files Successfully");
    }

}
