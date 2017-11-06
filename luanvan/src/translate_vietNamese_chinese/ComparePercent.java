package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ComparePercent {

	public static void listFilesForFolder(final File folderVN, final File folderCN) {
		ArrayList<PercentObject> percentObjectsVN = new ArrayList<>();
		ArrayList<PercentObject> percentObjectsCN = new ArrayList<>();
		for (final File file : folderVN.listFiles()) {
			if (file.isDirectory()) {
				listFilesForFolder(file, file);
			} else {
				if (readFileFromPath(file.getPath()) != null) {
					percentObjectsVN.add(readFileFromPath(file.getPath()));
				}
			}
		}
		for (final File file : folderCN.listFiles()) {
			if (file.isDirectory()) {
				listFilesForFolder(file, file);
			} else {
				if (readFileFromPath(file.getPath()) != null) {
					percentObjectsCN.add(readFileFromPath(file.getPath()));
				}

			}
		}

		for (int i = 0; i < percentObjectsCN.size(); i++) {
			System.out.println(percentObjectsCN.get(i).getFilePath() + "\n");
			for (int j = 0; j < percentObjectsCN.get(i).getListPercent().size(); j++) {
				System.out.println(percentObjectsCN.get(i).getListPercent().get(j));
			}
		}
		
		System.out.println(percentObjectsCN.size());
		System.out.println(percentObjectsVN.size());
	
	}

	public static PercentObject readFileFromPath(String filePath) {
		PercentObject result = new PercentObject();
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
			result = handleFileString(everything, key, filePath);
			bufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static PercentObject handleFileString(String content, String key, String filePath) {
		// Check file and remove key to get main contain
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
		String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
		PercentObject result = getPercentObject(mainContent, filePath);
		return result;
	}

	private static PercentObject getPercentObject(String mainContent, String filePath) {

		int lastIndex = 0;
		ArrayList<String> listPercent = new ArrayList<>();
		String tempString;
		String percent;
		while (lastIndex != -1) {
			lastIndex = mainContent.indexOf("%", lastIndex);
			if (lastIndex != -1) {

				tempString = mainContent.substring(lastIndex - 6, lastIndex + 1);
				tempString = tempString.replace(".", ",");// Some string contain
															// character . in
															// number percent
				tempString = tempString.replace(" ", "");
				// Regex only get number with character percent
				percent = String.valueOf(tempString.replaceAll("[^\\d,%]+|\\.(?!\\d%)", ""));
				if (percent.startsWith("%")) {
					percent = percent.substring(1, percent.length());
				}
				listPercent.add(percent);

			}
			tempString = "";
			lastIndex++;// Not in loop

		}
		if (listPercent.size() > 0) {
			PercentObject result = new PercentObject(filePath, listPercent);
			return result;
		}

		return null;
	}

	public static void main(String[] args) throws IOException {
		File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese");
		File folder2 = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese");
		listFilesForFolder(folder, folder2);
	}
}
