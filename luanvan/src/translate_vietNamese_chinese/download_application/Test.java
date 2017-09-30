package translate_vietNamese_chinese.download_application;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class Test {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		for(int i=1;i<10;i++){
                    System.out.println(Jsoup.connect("http://cn.news.chinhphu.vn/Home/tin-bo-nganh/trang-"+i+".vgp").userAgent("Mozilla").timeout(0).get());
                }
                System.out.println("translate_vietNamese_chinese.download_application.Test.main()");
	}

}
