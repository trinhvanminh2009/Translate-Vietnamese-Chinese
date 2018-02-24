package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompareTitle {

	public CompareTitle() {
	}

	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
											// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0; /* both strings are zero length */
		}
		/*
		 * // If you have StringUtils, you can use it to calculate the edit
		 * distance: return (longerLength -
		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
		 * longerLength;
		 */
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public static float printSimilarity(String s, String t) {

		return (float) (similarity(s, t) * 100);

	}

	public static boolean printSimilarity1(String s, String t) {
		if ((int) (similarity(s, t) * 100) > 92) {
			// System.out.println((int)(similarity(s, t) *100) +"% is the
			// similarity between "+ s +" and "+ t);
			return true;
		}
		return false;

	}

	public static SimilarityDate printSimilarityDate(ConvertDate convertDateCN, ConvertDate convertDateVN) {
		SimilarityDate similarityDate = null;
		if ((int) (similarity(convertDateCN.getContent(), convertDateVN.getContent()) * 100) > 92) {
			/*
			 * System.out.println((int)(similarity(convertDateCN.getContent(),
			 * convertDateVN.getContent()) *100) +
			 * "% is the similarity between "+ convertDateCN.getContent() +
			 * "\n with link "+convertDateCN.getFilePath() +"\n and "+
			 * convertDateVN.getContent() + " with link "+
			 * convertDateVN.getFilePath());
			 */
			similarityDate = new SimilarityDate(convertDateVN.getContent(), convertDateCN.getContent(),
					convertDateVN.getFilePath(), convertDateCN.getFilePath(),
					(int) (similarity(convertDateCN.getContent(), convertDateVN.getContent()) * 100));
			return similarityDate;
		}
		return similarityDate;
	}

	public static void listFilesForFolder(final File folderVN, final File folderCN) {
		ArrayList<PercentSimilarityTitle> listPercentSimilarity = new ArrayList<>();
		float percentBiggest = 0;
		String pathVN = "";
		String pathCN = "";
		String titleVN = "";
		String titleCN = "";
		File listFileVN[] = folderVN.listFiles();
		File listFileCN[] = folderCN.listFiles();
		for (int i = 0; i < listFileVN.length; i++) {
			percentBiggest = printSimilarity(readFileFromPath(listFileVN[i].getPath()),
					readFileFromPath2(listFileCN[0].getPath()));
			for (int j = 0; j < listFileCN.length; j++) {
				if ((printSimilarity(readFileFromPath(listFileVN[i].getPath()),
						readFileFromPath2(listFileCN[j].getPath())) > percentBiggest)) {
					percentBiggest = printSimilarity(readFileFromPath(listFileVN[i].getPath()),
							readFileFromPath2(listFileCN[j].getPath()));
					pathVN = listFileVN[i].getPath();
					pathCN = listFileCN[j].getPath();
					titleVN = readFileFromPath(listFileVN[i].getPath());
					titleCN = readFileFromPath2(listFileCN[j].getPath());
				}
			}
			if (pathVN.equals("") && pathCN.equals("") && titleVN.equals("") && titleCN.equals("")) {
				pathVN = listFileVN[i].getPath();
				pathCN = listFileCN[i].getPath();
				titleVN = readFileFromPath(listFileVN[0].getPath());
				titleCN = readFileFromPath(listFileCN[0].getPath());
			}
			PercentSimilarityTitle percentSimilarity = new PercentSimilarityTitle(percentBiggest, pathVN, pathCN, titleVN,
					titleCN);
			listPercentSimilarity.add(percentSimilarity);

		}

		String currentString = "";
		for (int i = 0; i < listPercentSimilarity.size(); i++) {
			currentString = listPercentSimilarity.get(i).getPathVN() + " with title "
					+ listPercentSimilarity.get(i).getTitleVN() + " similarity with "
					+ listPercentSimilarity.get(i).getPathCN() + " with title "
					+ listPercentSimilarity.get(i).getTitleCN() + " about "
					+ listPercentSimilarity.get(i).getPercentSimilarity() + "% \n";

			WriteFile.writeDateTimeSimilarity(currentString, "SimilyratyByTitle_");
		}
		System.out.println("Write file successfully!");
	}

	public static String readFileFromPath(String filePath) // Return title in
															// file
	{
		final BufferedReader bufferedReader;
		String result = "";
		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
                                System.out.println(line);
                                System.exit(0);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();

			}
			String everything = stringBuilder.toString();
			String key = "-----------------";
			result = handleFileString(filePath, everything, key);
			bufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String readFileFromPath2(String filePath) // Return everything
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

	private static String handleFileString(String filePath, String content, String key) {
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
		String titleContent = content.substring(listIndex.get(0), listIndex.get(1));
		titleContent = titleContent.replace(key, "");
		return titleContent;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final File folder1 = new File("DATA/Politics/Politics_VietNamese");
		final File folder2 = new File("G:\\IT\\luanvan\\Git\\Translate-Vietnamese-Chinese\\luanvan\\DATA\\Politics_Chinese_Translted");
                
//                final File folder1 = new File("grimmstories.com/vn");
//		final File folder2 = new File("grimmstories_translated");
                
		listFilesForFolder(folder1, folder2);

	}

}
