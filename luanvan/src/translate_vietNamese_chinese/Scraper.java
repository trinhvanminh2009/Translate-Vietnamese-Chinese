package translate_vietNamese_chinese;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.media.sound.JavaSoundAudioClip;
import com.sun.xml.internal.bind.CycleRecoverable;

public class Scraper {

    private ArrayList<Article> articles;
    private Document doc;

    public Scraper() {
        articles = new ArrayList<>();
    }

    public void scrapVN(String pageName) throws IOException {

        doc = Jsoup.connect(pageName).timeout(0).get();
        getAllLink(pageName, 1, getCountPagesVN());
      

        //getAllLink(pageName, 1, 30);
        System.out.println(articles.size());

    }

    public void scrapCN(String pageName) throws IOException {

       // doc = Jsoup.connect(pageName).timeout(0).get();
    	
       //  getAllLink(pageName, 1, getCountPagesCN());
      //  getAllLink(pageName, 0, getCountPages(pageName));
       getAllLink(pageName, 0,9);
        System.out.println(articles.size());

    }

    public void getLink(String page) {

        try {
            Document doc1 = Jsoup.connect(page).timeout(0).get();

            Elements div = doc1.select("div.story-listing.slist-03 > article");

            for (Element i : div) {
                Article ar = new Article();
                ar.setLink(i.select("a[href]").first().attr("abs:href"));
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                }
                getDetails(ar);
                articles.add(ar);

            }
        } catch (IOException ex) {
            System.out.println(page);
        }

    }

    public void getAllLink(String pageName, int begin, int end) throws IOException {

    	
        for (int i = begin; i <= end; i++) {
//            if (i % 100 == 0) {
//
//                try {
//                    Thread.sleep(60000);
//                } catch (InterruptedException ex) {
//                }
//
//            }
            if (i == 1) {
            	getLink(pageName);
              //  getLink(pageName);
          
                //System.out.println(pageName);
            } else {
            	 String name = pageName.substring(0, pageName.length() - 4);//.vnp
                //String name = pageName.substring(0, pageName.length() - 4);//.vnp
                //System.out.println(name + "/trang" + i+".vnp");
                getLink(name + "/page" + i + ".vnp");
            }

        }
    }

    private int getCountPagesVN() {
        Elements lista = doc.select("#mainContent_ctl00_ContentList1_pager ul a[href]");
        int countPage = Integer.parseInt(lista.last().text());
        return countPage;
    }

    private int getCountPagesCN() {
        Elements lista = doc.select("#ctl00_mainContent_ctl00_ContentList1_pager ul a[href]");
        int countPage = Integer.parseInt(lista.last().text());
        return countPage;
    }

    public void getDetails(Article ar) {

        try {
            Document doc1 = Jsoup.connect(ar.getLink()).timeout(0).get();
            ar.setTitle(doc1.select("header.article-header >h1").first().text());
            ar.setContent(doc1.select("div.article-body").first().text());
        } catch (Exception ex) {
            System.out.println("This link error");
            System.out.println(ar.getLink());
          
        }
    }

    public static void main(String[] args) throws IOException, Exception {
    	
        Scraper s = new Scraper();
        String url = java.net.URLDecoder.decode("http://zh.vietnamplus.vn/topic/2017%E5%B9%B4%E8%B6%8A%E5%8D%97apec%E4%BC%9A%E8%AE%AE/136.vnp","UTF-8");
        s.scrapCN(url);
        File file = new File(System.getProperty("user.dir") + "/VNDATA48");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        int i=1;
        int j = 0;
        for (Article a : s.articles) {
        	  if(a.getTitle()!= null && a.getContent() != null && a.getLink() != null )
              {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.dir") + "/VNDATA48/"+i+".txt"), "utf-8"))) {
                
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