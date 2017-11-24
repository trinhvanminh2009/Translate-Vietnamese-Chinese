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
		try{
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
			// System.out.println(content.length());
			lastIndex = 0;
			String title = content.substring(listIndex.get(0), listIndex.get(1));
			String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
			ArrayList<String> listMainContent = new ArrayList<>();
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
			if (listIndexMainContent.size() > 0) {
				System.out.println(mainContent.substring(0, listIndexMainContent.get(0)));
				for (int i = 0; i < mainContent.length(); i++) {
					System.out.println(listIndexMainContent.get(i));
					System.out.println("size: " + listIndexMainContent.size());
					if (returnIndexOfElementInArrrayList(listIndexMainContent,
							listIndexMainContent.get(i)) == listIndexMainContent.size() - 1) {
						break;
					} else {
						System.out.println(
								"Substring from " + listIndexMainContent.get(i) + " to " + listIndexMainContent.get(i + 1));
						if (listIndexMainContent.get(i) == listIndexMainContent.get(i + 1)) {
							break;
						} else {
							System.out.println(
									mainContent.substring(listIndexMainContent.get(i), listIndexMainContent.get(i + 1)));
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	

		// System.out.println(mainContent.length());
		// mainContent = mainContent.replace(key, "");
		/*
		 * String test =
		 * "越共中央经济部部长会见越南欧洲商会制药协会区域高级代表团（图片来源：越通社） 越通社河内—— 越共中央政治局委员、中央书记处书记、中央经济部长阮文平会见越南欧洲商会制药协会区域高级代表团。  阮文平向越南欧洲商会制药协会区域高级代表团介绍越南党和政府为改善营商环境和提高竞争力所采取的大方向和措施，旨在鼓励企业扩大包括制药领域在内的各领域经营投资合作。他表示，越南十分重视保持社会政策和谐，努力让群众用低廉的费用获取优质的医疗卫生服务。 阮文平强调， 越南政府愿为包括欧洲商会企业在内的各国制药企业扩大在越投资并设立药物研发中心，同越南企业合作，将越南发展成为东南亚地区制药基地等创造便利条件。 越南欧洲商会制药协会副会长Nicholas Jones对越南经济社会发展前景以及越南政府为改善营商环境、简化行政审批制度等充满信心。 欧洲企业对越南宏观经济的稳定充满信心 他对越南在制药和医疗卫生体系所遇到的困难和挑战表示理解，同时希望越南更加努力改善法律框架和政策机制，为从事制药等领域的企业创造更为便利的条件。 越南欧洲商会制药企业协会多家企业代表一致认为越南有望成为东盟的制药生产基地，同时就有关医药行业发展模式、促进患者在药品领域的可持续性获得、政府采购、知识产权保护、对越投资机会等问题进行探讨。（越通社——VNA） "
		 * ; TranslateAllFile http = new TranslateAllFile();
		 * System.out.println(test.length()); String wordToEnglish =
		 * http.callUrlAndParseResult("zh-CN", "vi", test);
		 * System.out.println(wordToEnglish);
		 */
		/*
		 * String wordEnglishToVietnamese = http.callUrlAndParseResult("en",
		 * "vi", wordToEnglish); System.out.println(wordEnglishToVietnamese);
		 */

		/*
		 * System.out.println(fileName); System.out.println(wordToEnglish);
		 * Translate.writeTranstedToText(folderName, fileName, wordToEnglish);
		 */
	}

	private static int returnIndexOfElementInArrrayList(ArrayList<Integer> arrayList, int currentNumber) {
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
		final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese");
		try {
			listFilesForFolder(folder, "Politics_Chinese_Translted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
