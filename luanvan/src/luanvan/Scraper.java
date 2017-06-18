package luanvan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author quang
 */
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

        doc = Jsoup.connect(pageName).timeout(0).get();
        //getAllLink(pageName, 0, getCountPages(pageName));
        getAllLink(pageName, 0, 2);
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
                //System.out.println(pageName);
            } else {
                String name = pageName.substring(0, pageName.length() - 4);//.vnp
                //System.out.println(name + "/trang" + i+".vnp");
                getLink(name + "/trang" + i + ".vnp");
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
            ar.setTitle(doc1.select("h1.cms-title").first().text());
            ar.setContent(doc1.select("div.cms-body").first().text());
        } catch (Exception ex) {
            System.out.println("This link error");
            System.out.println(ar.getLink());
          
        }
    }

    public static void main(String[] args) throws IOException, Exception {

        Scraper s = new Scraper();
        s.scrapVN("http://www.vietnamplus.vn/kinhte/doanhnghiep.vnp");
        File file = new File(System.getProperty("user.dir") + "/VNDATA13");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        int i=1;
        for (Article a : s.articles) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.dir") + "/VNDATA13/"+i+".txt"), "utf-8"))) {
                writer.write(a.getLink()+"\n-----------------\n");
                if(a.getTitle()!= null && a.getContent() != null)
                {
                	 writer.write(a.getTitle()+"\n-----------------\n");
                     writer.write(a.getContent()+"\n-----------------\n");
                }   
            }
           i++;
        }
        System.out.println("Successfully");
    }
}
