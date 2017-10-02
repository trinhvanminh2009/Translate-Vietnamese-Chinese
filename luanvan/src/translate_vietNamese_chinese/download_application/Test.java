package translate_vietNamese_chinese.download_application;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.jsoup.Jsoup;


public class Test {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
                    System.out.println(Jsoup.connect(URLDecoder.decode("http://cn.news.chinhphu.vn/Home/%e8%b6%8a%e5%8d%97%e5%9b%bd%e5%ae%b6%e4%b8%bb%e5%b8%ad%e9%99%88%e5%a4%a7%e5%85%89%e4%bc%9a%e8%a7%81%e4%bf%9d%e5%8a%a0%e5%88%a9%e4%ba%9a%e9%a9%bb%e8%b6%8a%e5%a4%a7%e4%bd%bf/20179/23310.vgp", "UTF-8")).userAgent("Mozilla").timeout(0).get());
                System.out.println("translate_vietNamese_chinese.download_application.Test.main()");
	}

}
