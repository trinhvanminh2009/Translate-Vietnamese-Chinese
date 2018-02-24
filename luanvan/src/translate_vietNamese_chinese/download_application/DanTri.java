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
public class DanTri {

	private ArrayList<Article> articles;

	public DanTri() {
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
				if (!getLink(pageName + "/trang-" + i + ".htm")) {
					break;
				}
			}

		}
	}

	public boolean getLink(String page) {

		try {
			Document doc1 = Jsoup.connect(page).timeout(0).get();
			Elements div = doc1.select("div.container div#listcheckepl > div.mt3");
			if (div.isEmpty()) {
				return false;
			}

			System.out.println(page);
			System.out.println(div.size());
			for (Element i : div) {
				Article ar = new Article();
				ar.setLink(i.select("a[href]").first().attr("abs:href"));
				System.out.println(i.select("a[href]").first().attr("abs:href"));
				// System.exit(0);
				if (getDetails(ar)) {
					articles.add(ar);
				} else {
					System.out.println("bo qua");
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
			if (doc1.select("div.container div#ctl00_IDContent_ctl00_divContent > h1").isEmpty()) {
				return false;
			}
			ar.setTitle(doc1.select("div.container div#ctl00_IDContent_ctl00_divContent > h1").first().text());
			// System.out.println(doc1.select("div.container
			// div#ctl00_IDContent_ctl00_divContent > h1").first().text());
			// System.exit(0);
			if (doc1.select("div.container div#ctl00_IDContent_ctl00_divContent > div#divNewsContent").isEmpty()) {
				return false;
			}
			ar.setContent(doc1.select("div.container div#ctl00_IDContent_ctl00_divContent > div#divNewsContent").first()
					.text());
			// System.out.println(doc1.select("div.container
			// div#ctl00_IDContent_ctl00_divContent >
			// div#divNewsContent").first().text());
			// System.exit(0);
			return true;

		} catch (Exception ex) {
			System.out.println("ex: " + "@@@@@@@@@@@");
			System.out.println("ex: " + ar.getLink());
			System.out.println("ex: " + ar.getTitle());
			System.out.println("ex: " + ar.getContent());
			System.out.println("ex: " + ex);
			System.out.println("ex: " + "@@@@@@@@@@@");
			return false;
		}
	}

	public static void main(String[] args) throws IOException, Exception {

		DanTri s = new DanTri();
		s.scrap("http://dantri.com.vn/su-kien");
		File file = new File(System.getProperty("user.dir") + "/dantri_su-kien");
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		int i = 1;
		for (Article a : s.articles) {

			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(System.getProperty("user.dir") + "/dantri_su-kien/" + i + ".txt"), "utf-8"))) {
				writer.write(a.getLink() + "\n-----------------\n");
				writer.write(a.getTitle() + "\n-----------------\n");
				writer.write(a.getContent() + "\n-----------------\n");
			}
			i++;
		}
	}
}
