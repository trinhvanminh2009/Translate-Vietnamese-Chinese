package translate_vietNamese_chinese.file_size_filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;

import translate_vietNamese_chinese.Translate;

public class TranslateAllFile {

	/**
	 * @param content
	 * @param key
	 * @param fileName
	 * @param folderName
	 * @throws Exception
	 */
	private static void handleFileString(String content, String key, String fileName, String folderName)
			throws Exception {
		try {
			int lastIndex = 0;
			ArrayList<Integer> listIndex = new ArrayList<>();
			while (lastIndex != -1) {
				lastIndex = content.indexOf(key, lastIndex);
				if (lastIndex != -1) {
					lastIndex += key.length();
					content.substring(lastIndex, content.length());
					listIndex.add(lastIndex);

				}
			}
			// Starting cut content in main paper.
			lastIndex = 0;
			String title = content.substring(listIndex.get(0), listIndex.get(1));
			String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
			ArrayList<String> listMainContentCutted = new ArrayList<>();
			ArrayList<String> listMainContentTranslated = new ArrayList<>();
			ArrayList<Integer> listIndexMainContent = new ArrayList<>();

			for (int i = 0; i < mainContent.length(); i++) {
				lastIndex = mainContent.indexOf("。", lastIndex);
				if (lastIndex != -1) {
					if (listIndexMainContent.size() > 1 && lastIndex == listIndexMainContent.get(0)) {
						break;
					} else {
						listIndexMainContent.add(lastIndex);
					}
				}
				lastIndex++;
			}
			System.out.println(fileName);
			// Get content by sentences
			if (listIndexMainContent.size() > 0) {
				listMainContentCutted.add(mainContent.substring(0, listIndexMainContent.get(0)));
				for (int i = 0; i < mainContent.length(); i++) {
					if (getIndexOfElementInArrrayList(listIndexMainContent,
							listIndexMainContent.get(i)) == listIndexMainContent.size() - 1) {
						break;
					} else {
						if (listIndexMainContent.get(i) == listIndexMainContent.get(i + 1)) {
							break;
						} else {
							listMainContentCutted.add(mainContent.substring(listIndexMainContent.get(i),
									listIndexMainContent.get(i + 1)));
						}
					}
				}
			}
			// Starting translate each sentence
			TranslateAllFile http = new TranslateAllFile();
			listMainContentTranslated.add(http.callUrlAndParseResult("zh-CN", "vi",title));
			listMainContentTranslated.add(key);
			for (int i = 0; i < listMainContentCutted.size(); i++) {
				listMainContentTranslated.add(http.callUrlAndParseResult("zh-CN", "vi",
						listMainContentCutted.get(i).trim().replace("。", "")));
			}
			//Convert list 
			String contentVN = listMainContentTranslated.get(0) + listMainContentTranslated.get(1)+"\n" ;
			for(int i = 2 ; i < listMainContentTranslated.size(); i++){
				contentVN = contentVN + listMainContentTranslated.get(i) + ".\n" ;
			}
			System.out.println(contentVN);
			Translate.writeTranstedToText(folderName, fileName, contentVN);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static int getIndexOfElementInArrrayList(ArrayList<Integer> arrayList, int currentNumber) {
		for (int i = 0; i < arrayList.size(); i++) {
			if (currentNumber == arrayList.get(i)) {
				return i;
			}
		}
		return 0;
	}

	public static void getContent(String filePath, String folderName) throws Exception // Return
																						// title
	// and content
	// by list
	{
		final BufferedReader bufferedReader;

		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();

			}
			String everything = stringBuilder.toString();
			String key = "-----------------";
			handleFileString(everything, key, file.getName(), folderName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getEverything(String filePath) // Return everything
	// in file
	{
		String result = "";
		final BufferedReader bufferedReader;
		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();

			}
			String everything = stringBuilder.toString();
			result = everything;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static void readFileFromPath(String filePath, String folderName) throws Exception {
		final BufferedReader bufferedReader;
		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();

			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();

			while (line != null)// Read each line in folder
			{
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();

			}
			String everything = stringBuilder.toString();
			String key = "-----------------";// Key to know what going on in
												// file
			handleFileString(everything, key, file.getName(), folderName);
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String callUrlAndParseResult(String langFrom, String langTo, String word) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" + langFrom + "&tl="
				+ langTo + "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return parseResult(response.toString());
	}

	private String parseResult(String inputJson) throws Exception {
		JSONArray jsonArray = new JSONArray(inputJson);
		JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
		JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
		return jsonArray3.get(0).toString();
	}

	public TranslateAllFile() {

	}

	public static void listFilesForFolder(final File folder, String folderName) throws Exception {
		// This function call another function to read all file from folder
		for (final File file : folder.listFiles()) {
			if (file.isDirectory()) {
				listFilesForFolder(file, folderName);
			} else {
				readFileFromPath(file.getPath(), folderName);

			}
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final File folder = new File("G:/IT/luanvan/Git/Translate-Vietnamese-Chinese/luanvan/Politis_Chinese1");
		try {
			listFilesForFolder(folder, "Politics_Chinese_Translted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
