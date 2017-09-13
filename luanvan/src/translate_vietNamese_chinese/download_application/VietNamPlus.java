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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VietNamPlus {

	private String currentDirectory;
	private String pageName;
	private int articleSize;
	private final String USER_AGENT = "Mozilla/5.0";

	public VietNamPlus() {
		articleSize = 0;
		currentDirectory = "";
	}

	public static String getCurrentDirectory(String pageName) {
		String directory = "";
		switch (pageName) {
		case "http://www.vietnamplus.vn/kinhte.vnp":
			directory = "vietnamplusVN/kinhte";
			break;
		case "http://www.vietnamplus.vn/chinhtri.vnp":
			directory = "vietnamplusVN/chinhtri";
			break;
		case "http://www.vietnamplus.vn/xahoi.vnp":
			directory = "vietnamplusVN/xahoi";
			break;
		case "http://www.vietnamplus.vn/thegioi.vnp":
			directory = "vietnamplusVN/thegioi";
			break;
		case "http://www.vietnamplus.vn/doisong.vnp":
			directory = "vietnamplusVN/doisong";
			break;
		case "http://www.vietnamplus.vn/vanhoa.vnp":
			directory = "vietnamplusVN/vanhoa";
			break;
		case "http://www.vietnamplus.vn/thethao.vnp":
			directory = "vietnamplusVN/thethao";
			break;
		case "http://www.vietnamplus.vn/khoahoc.vnp":
			directory = "vietnamplusVN/khoahoc";
			break;
		case "http://www.vietnamplus.vn/congnghe.vnp":
			directory = "vietnamplusVN/congnghe";
			break;
		case "http://www.vietnamplus.vn/chuyenla.vnp":
			directory = "vietnamplusVN/chuyenla";
			break;

		case "http://zh.vietnamplus.vn/politics.vnp":
			directory = "vietnamplusCN/politics";
			break;
		case "http://zh.vietnamplus.vn/world.vnp":
			directory = "vietnamplusCN/world";
			break;
		case "http://zh.vietnamplus.vn/business.vnp":
			directory = "vietnamplusCN/business";
			break;
		case "http://zh.vietnamplus.vn/social.vnp":
			directory = "vietnamplusCN/social";
			break;
		case "http://zh.vietnamplus.vn/culture.vnp":
			directory = "vietnamplusCN/culture";
			break;
		case "http://zh.vietnamplus.vn/sports.vnp":
			directory = "vietnamplusCN/sports";
			break;
		case "http://zh.vietnamplus.vn/technology.vnp":
			directory = "vietnamplusCN/technology";
			break;
		case "http://zh.vietnamplus.vn/environment.vnp":
			directory = "vietnamplusCN/environment";
			break;
		case "http://zh.vietnamplus.vn/Travel.vnp":
			directory = "vietnamplusCN/travel";
			break;
		}
		return directory;
	}

	public void init(String pageName, int articleSize) {
		currentDirectory = getCurrentDirectory(pageName);
		this.pageName = pageName;
		this.articleSize = articleSize;
		makeDirectory(currentDirectory, false);
		System.out.println("Start download VietNamPlus: " + pageName);
	}

	public void init(String pageName) {
		currentDirectory = getCurrentDirectory(pageName);
		this.pageName = pageName;
		makeDirectory(currentDirectory, true);
		System.out.println("Start download VietNamPlus: " + pageName);
	}

	public boolean scrap(int pageNumber) throws JSONException {
		System.out.println(pageName + " " + pageNumber);

		if (pageNumber == 1) {
			getLink(pageName);
		} else {
			 String name = pageName.substring(0, pageName.length() - 4);
			if (!getLink(pageName + "/trang" + pageNumber + ".vnp")) {
				return false;
			}
		}
		return true;
	}

	public boolean checkValidLink(int pageNumber) {
		
			String link = pageName + "/page/" + pageNumber + ".html";
			String originalUrl = null;
			try {
				originalUrl = Jsoup.connect(link).userAgent("Mozilla").followRedirects(true) // to
																								// follow
																								// redirects
						.execute().url().toExternalForm();
			} catch (IOException ex) {
				Logger.getLogger(VietNamPlus.class.getName()).log(Level.SEVERE, null, ex);
			}
			// System.out.println(originalUrl);
			try {
				Document doc1 = Jsoup.connect(link).userAgent("Mozilla").timeout(10000).get();

				Elements div = doc1.select("section.container section.sidebar_1 > article.list_news");
				if (div.isEmpty() || !originalUrl.equals(link)) {
					return false;
				}

			} catch (Exception ex) {
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


	public boolean getLink(String page) {

		try {
			Document doc1 = Jsoup.connect(page).userAgent("Mozilla").timeout(0).get();

			Elements div = doc1.select("section.container section.sidebar_1 > article.list_news");
			if (div.isEmpty()) {
				return false;
			}

			System.out.println(page);
			System.out.println(div.size());
			for (Element i : div) {
				Article ar = new Article();
				ar.setLink(i.select("h3 a[href]").first().attr("abs:href"));
				// System.out.println(i.select("h3
				// a[href]").first().attr("abs:href"));
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
			Document doc1 = Jsoup.connect(ar.getLink()).userAgent("Mozilla").timeout(10000).get();
			// kiem tra neu k co element thi no qua
			if (doc1.select("section.container section.sidebar_1 > h1").isEmpty()) {
				return false;
			}
			ar.setTitle(doc1.select("section.container section.sidebar_1 > h1").first().text());
			// System.out.println(doc1.select("section.container
			// section.sidebar_1 > h1").first().text());
			// System.exit(0);
			if (doc1.select("section.container section.sidebar_1 > article").isEmpty()) {
				return false;
			}
			ar.setContent(doc1.select("section.container section.sidebar_1 > article").first().text());
			// System.out.println(doc1.select("section.container
			// section.sidebar_1 > article").first().text());
			// System.exit(0);
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
		// VNExpress v = new VNExpress();
		// v.init("https://vnexpress.net/tin-tuc/thoi-su");
		// for (int i = 1;; i++) {
		// if (!v.scrap(i)) {
		// break;
		// }
		// }
		// System.out.println(v.articleSize);
		// //System.out.println(v.currentPage);
		// System.out.println("Done");

		VietNamPlus v = new VietNamPlus();
		v.init("https://vnexpress.net/tin-tuc/thoi-su");
		// System.out.println(v.getMaxPageNumber());
		System.out.println(v.checkValidLink(3213));
	}

}
