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
		runTranslate();
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

				  final File folder = new File("G:/IT/luanvan/Git/Translate-Vietnamese-Chinese/luanvan/DATA/Politics/Politis_Chinese");
					try {
						listFilesForFolder(folder,"Politis_Chinese_Translted");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		};
	
		
		
		
		Thread threadCulture = new Thread(culture);

		threadCulture.start();

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
		 File file = new File(folderName);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }
	        
	        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(folderName+"/"+fileName), "utf-8"))) {
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
