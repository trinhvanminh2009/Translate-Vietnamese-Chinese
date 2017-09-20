package translate_vietNamese_chinese.download_application;

import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class Test {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
                    String url = java.net.URLDecoder.decode("http://cn.sggp.org.vn/%E6%99%82%E6%94%BF/page-2000.html","UTF-8");
            Connection.Response response = Jsoup.connect(url).followRedirects(false).execute();       
                    System.out.println( response.header("location"));
        } catch (Exception e) {
           
        }
	}

}
