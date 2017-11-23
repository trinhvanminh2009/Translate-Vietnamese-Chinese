package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;


public class Translate {
	public static void main(String[] args) throws Exception 
	 {
		
	 }
		
	public Translate(){}
	
	public static String translateChineseToVietnamese(String input) throws Exception
	{
		Translate http = new Translate();
		  String wordToEnglish = http.callUrlAndParseResult("zh-CN", "en", input);
		  String wordEnglishToVietnamese = http.callUrlAndParseResult("en", "vi", wordToEnglish);
		  return  wordEnglishToVietnamese;
		 
	}
	
	
	public static void runTranslate()
	{
		//Run translate from Chinese, each folder is an thread
		Runnable culture = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Culture/Culture_chinese");
					try {
						listFilesForFolder(folder,"Culture_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
	
		
		
		Runnable enviroment = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Enviroment/Enviroment_Chinese");
					try {
						listFilesForFolder(folder,"Enviroment_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		
		
		Runnable social = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Social/Social_Chinese");
					try {
						listFilesForFolder(folder,"Social_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable sports = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Sports/Sports_Chinese");
					try {
						listFilesForFolder(folder,"Sports_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable technology = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Technology/Technology_Chinese");
					try {
						listFilesForFolder(folder,"Technology_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable travel = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Travel/Travel_Chinese");
					try {
						listFilesForFolder(folder,"Travel_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable world = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/World/World_Chinese");
					try {
						listFilesForFolder(folder,"World_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
	Runnable apec = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/World_Next/World_Next_Chinese/APEC_Chinese/APEC_Chinese");
					try {
						listFilesForFolder(folder,"Apec_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable asean = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/World_Next/World_Next_Chinese/Asean_Chinese/Asean_Chinese");
					try {
						listFilesForFolder(folder,"Asean_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Runnable bienDoiKhiHau = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/World_Next/World_Next_Chinese/BienDoiKhiHau_Chinese/BienDoiKhiHau_Chinese");
					try {
						listFilesForFolder(folder,"BienDoiKhiHau_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
	Runnable bienDong = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/World_Next/World_Next_Chinese/BienDong_Chinese/BienDong_Chinese");
					try {
						listFilesForFolder(folder,"BienDong_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
		Thread threadCulture = new Thread(culture);
		Thread threadEnviroment = new Thread(enviroment);
		Thread threadSocial = new Thread(social);
		Thread threadSports = new Thread(sports);
		Thread threadTechnology = new Thread(technology);
		Thread threadTravel = new Thread(travel);
		Thread threadWorld = new Thread(world);
		Thread threadApec = new Thread(apec);
		Thread threadAsean = new Thread(asean);
		Thread threadBienDoiKhiHau = new Thread(bienDoiKhiHau);
		Thread threadBienDong = new Thread(bienDong);
		threadCulture.start();
		threadEnviroment.start();
		threadSocial.start();
		threadSports.start();
		threadTechnology.start();
		threadTravel.start();
		threadWorld.start();
		threadApec.start();
		threadAsean.start();
		threadBienDoiKhiHau.start();
		threadBienDong.start();
	}
	
	public static void listFilesForFolder(final File folder,String folderName) throws Exception
	{
		//This function call another function to read all file from folder
		for(final File file : folder.listFiles())
		{
			if(file.isDirectory())
			{
				listFilesForFolder(file,folderName);
			}
			else{	
				readFileFromPath(file.getPath(),folderName);
		
			}
		}
	}
	
	public static void readFileFromPath(String filePath, String folderName) throws Exception 
	{
		final BufferedReader bufferedReader;
		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();
		
			
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			
			while(line != null)//Read each line in folder 
			{
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
				
			}
			String everything = stringBuilder.toString();
			String key = "-----------------";//Key to know what going on in file
			handleFileString(everything, key, file.getName(),folderName);
			bufferedReader.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void handleFileString(String content, String key, String fileName,String folderName) throws Exception
	{
		int lastIndex = 0;

		ArrayList<Integer> listIndex = new ArrayList<>();
		while(lastIndex != -1)
		{
			lastIndex = content.indexOf(key, lastIndex);
			if(lastIndex != -1)
			{
				lastIndex += key.length();
				content.substring(lastIndex, content.length());
				listIndex.add(lastIndex);
			
			}
		}
		String mainContent = content.substring(listIndex.get(0), listIndex.get(1));
		mainContent = mainContent.replace(key, "");
		
		Translate http = new Translate();
		  String wordToEnglish = http.callUrlAndParseResult("zh-CN", "en", mainContent);
		  String wordEnglishToVietnamese = http.callUrlAndParseResult("en", "vi", wordToEnglish);
		  System.out.println(fileName);
		  System.out.println(wordEnglishToVietnamese);
		  writeTranstedToText(folderName, fileName, wordEnglishToVietnamese);
	}
	
	 
	public static void writeTranstedToText(String folderName, String fileName, String content) throws IOException
	{
		 File file = new File(System.getProperty("user.dir") + "/"+folderName);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }
	        
	        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.dir") + "/"+folderName+"/"+fileName), "utf-8"))) {
                	writer.write(content);
                	  System.out.println(fileName + ""+ "Successfully");
                }
	      
	        
	}
	 private String callUrlAndParseResult(String langFrom, String langTo,String word) throws Exception 
	 {

	  String url = "https://translate.googleapis.com/translate_a/single?"+
	    "client=gtx&"+
	    "sl=" + langFrom + 
	    "&tl=" + langTo + 
	    "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");    
	  
	  URL obj = new URL(url);
	  HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
	  con.setRequestProperty("User-Agent", "Mozilla/5.0");
	  BufferedReader in = new BufferedReader(
	    new InputStreamReader(con.getInputStream()));
	  String inputLine;
	  StringBuffer response = new StringBuffer();
	 
	  while ((inputLine = in.readLine()) != null) {
	   response.append(inputLine);
	  }
	  in.close();
	 
	  return parseResult(response.toString());
	 }
	 
	 private String parseResult(String inputJson) throws Exception
	 {  
	  JSONArray jsonArray = new JSONArray(inputJson);
	  JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
	  JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
	  return jsonArray3.get(0).toString();
	 }
}
