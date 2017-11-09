package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CombinationCompare {
	private static void readFileFromPath(String fileConditionA, String fileConditionB) {
		ArrayList<String> listCompareByPercent = new ArrayList<>();
		ArrayList<String> listCompareByDate = new ArrayList<>();
		BufferedReader bufferedReader;
		File fileResult;
		// This block code for listCompareByPecent
		try {
			fileResult = new File(fileConditionA);
			bufferedReader = new BufferedReader(new FileReader(fileResult));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
				if (line != null) {
					listCompareByPercent.add(line);
				
				}
			}

		} catch (Exception e) {

		}
		// This block code for listCompareByDate
		try {
			fileResult = new File(fileConditionB);
			bufferedReader = new BufferedReader(new FileReader(fileResult));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
				if (line != null) {
					listCompareByDate.add(line);
				}
			}
		//	combinationCompare(listCompareByPercent, listCompareByDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		combinationCompare(listCompareByPercent, listCompareByDate);

	}
	
	private static void combinationCompare(ArrayList<String> listCompareByPercent, ArrayList<String> listCompareByDate){
		String currentLinePercent = "";
		String currentLineDate = "";
		int indexOfAboutPercent = 0;
		int indexOfAboutDate = 0;
		for(int i = 0; i < listCompareByPercent.size(); i++){
			currentLinePercent = listCompareByPercent.get(i);
			indexOfAboutPercent = currentLinePercent.indexOf("about");
			//Only get string contains path of 2 files
			currentLinePercent = currentLinePercent.substring(0, indexOfAboutPercent);
			for(int j  = 0; j < listCompareByDate.size(); j++){
				currentLineDate = listCompareByDate.get(j);
				indexOfAboutDate = currentLineDate.indexOf("about");
				currentLineDate = currentLineDate.substring(0, indexOfAboutDate);
				if(currentLineDate.replace(" ", "").equals(currentLinePercent.replace(" ", ""))){
					System.out.println(currentLineDate + "__" + currentLinePercent);
				}
				//System.out.println(currentLineDate);
				
			}
		}
		
	}

	public static void main(String[] args) throws Exception {
		readFileFromPath("D:/Dowloads/luanvan/SimilarityByDate.txt", "D:/Dowloads/luanvan/SimilarityByPercent.txt");
	}
}
