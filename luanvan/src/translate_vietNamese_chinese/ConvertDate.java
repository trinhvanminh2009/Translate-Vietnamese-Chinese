package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.sun.prism.paint.Color;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class ConvertDate {

	private String content;
	private String filePath;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

	public ConvertDate(String content, String filePath) {
		super();
		this.content = content;
		this.filePath = filePath;
	}

	public static void listFilesForFolder(final File folderVN,final File folderCN ) throws Exception
	{
		 ArrayList<ConvertDate>resultVN = new ArrayList<>();
		 ArrayList<ConvertDate>resultCN = new ArrayList<>();
		for(final File file : folderVN.listFiles())
		{
			if(file.isDirectory())
			{
				listFilesForFolder(file,file);
			}
			else{	
				resultVN.addAll(readFileFromPath(file.getPath())) ;
			}
		}
		for(final File file : folderCN.listFiles())
		{
			if(file.isDirectory())
			{
				listFilesForFolder(file,file);
			}
			else{	
				resultCN.addAll(readFileFromPath(file.getPath())) ;
			}
		}
		
		//handleArrayListDate(resultVN, resultCN);
		similarityFile(resultVN, resultCN);
	}
	
	public static ArrayList<ConvertDate> readFileFromPath(String filePath) throws Exception 
	{
		ArrayList<ConvertDate> result = new ArrayList<>();
		final BufferedReader bufferedReader;
		try {
			File file = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(file));
			bufferedReader.ready();
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while(line != null)
			{
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
				
			}
			String everything = stringBuilder.toString();
			String key = "-----------------";
			result  = handleFileString(everything, key, filePath);
			bufferedReader.close();
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private static ArrayList<ConvertDate> handleFileString(String content, String key, String filePath) throws Exception
	{
		//Check file and remove key to get main contain
		int lastIndex = 0;
		ArrayList<ConvertDate>resultList = new ArrayList<>();
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
		String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
		if(filePath.equals("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese"));
		{
			resultList.addAll(getDayMonthYearVN(mainContent,filePath));
			resultList.addAll(getMonthYearVN(mainContent,filePath));
			
		}
		if(filePath.equals("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese"));
		{
			resultList.addAll(getDateMonthYearCN(mainContent,filePath));
			resultList.addAll(getMonthYearCN(mainContent,filePath));
			
		}
		return resultList;
	}
	
	
	private static ArrayList<ConvertDate> getMonthYearVN(String content, String filePath)
	{
		ArrayList<ConvertDate>listMonthYear = new ArrayList<>();
		int lastIndex = 0;
		String tempString;
		ArrayList<String>listSpecicalChar = new ArrayList<>();
		listSpecicalChar.add("\\.");
		listSpecicalChar.add(",");
		//the character ( and ) and { and } are special character in Regex
		listSpecicalChar.add("\\)");
		listSpecicalChar.add(";");
		listSpecicalChar.add("---");
		String []listTempString;
		String []monthYear ;
		//Check index not null
		while(lastIndex != -1)
		{
			lastIndex = content.indexOf("tháng ",lastIndex);
			if(lastIndex != -1)
			{
				//Plus five because ngày have length is 6 include space
				lastIndex += 7;
				//Get substring include date month year inside
				tempString = content.substring(lastIndex-7, lastIndex+11);
				//Check this string contains "/" inside to make sure it's date/ month/year of Vietnamese
				if(tempString.contains("/"))
				{
					//Check substring contains " " to know this not wrong
					if(tempString.contains(" "))
					{
						listTempString = tempString.split(" ");
						for(int i = 0; i < listSpecicalChar.size(); i++)
						{
							monthYear = listTempString[1].split(listSpecicalChar.get(i));
							if(monthYear.length >0)
							{
								listTempString[1] = monthYear[0];
								
							}
							else
							{
								
							}
						}
						if(Character.isDigit(listTempString[1].charAt(0)))
						{
							tempString = listTempString[0] + " "+ listTempString[1];
						}
						else
						{
							break;
						}
					}
					ConvertDate convertDate = new ConvertDate(tempString.trim(), filePath);
					listMonthYear.add(convertDate);
				}
			}
			tempString = "";
		}
		return listMonthYear;
	}
	
	private static ArrayList<ConvertDate> getDayMonthYearVN(String content, String filePath)
	{
		ArrayList<ConvertDate>listDateMonthYear = new ArrayList<>();
		int lastIndex = 0;
		String tempString;
		ArrayList<String>listSpecicalChar = new ArrayList<>();
		listSpecicalChar.add("\\.");
		listSpecicalChar.add(",");
		//the character ( and ) and { and } are special character in Regex
		listSpecicalChar.add("\\)");
		listSpecicalChar.add(";");
		listSpecicalChar.add("---");
		String []listTempString;
		String []dateMonthYear ;
		//Check index not null
		while(lastIndex != -1)
		{
			lastIndex = content.indexOf("ngày ",lastIndex);
			if(lastIndex != -1)
			{
				//Plus five because ngày have length is 6 include space
				lastIndex += 6;
				//Get substring include date month year inside
				tempString = content.substring(lastIndex-6, lastIndex+18);
				//Check this string contains "/" inside to make sure it's date/ month/year of Vietnamese
				if(tempString.contains("/"))
				{
					//Check substring contains " " to know this not wrong
					if(tempString.contains(" "))
					{
						listTempString = tempString.split(" ");
						for(int i = 0; i < listSpecicalChar.size(); i++)
						{
							dateMonthYear = listTempString[1].split(listSpecicalChar.get(i));
							if(dateMonthYear.length >0)
							{
								listTempString[1] = dateMonthYear[0];
								
							}
							else
							{
								
							}
						}
						if(Character.isDigit(listTempString[1].charAt(0)))
						{
							tempString = listTempString[0] + " "+ listTempString[1];
						}
						else
						{
							break;
						}
					}
					ConvertDate convertDate = new ConvertDate(tempString.trim(),filePath);
					listDateMonthYear.add(convertDate);
				}
			}
			tempString = "";
		}
		
		return listDateMonthYear;
		
	}
	

	
	public static ArrayList<ConvertDate> getDateMonthYearCN(String content, String filePath) throws Exception
	{
		int lastIndex = 0;
		String subTempString;
		String tempString;
		ArrayList<ConvertDate>listDateMonth = new ArrayList<>();
		while(lastIndex != -1)
		{
			//Check in substring include year
			lastIndex = content.indexOf("年 ", lastIndex);
			if(lastIndex != -1)
			{
				//Length of year and space
				lastIndex += 2;
				//Get substring is date month year and space
				tempString = content.substring(lastIndex-7, lastIndex + 8 ).trim();
				//Check first is digit to make sure it's year in Chinese
				if(Character.isDigit(tempString.charAt(0)))
				{
					//Make sure substring include date
					if(tempString.contains("月") && tempString.contains(" ") && tempString.contains("日"))
					{
						//Check if last character is date in Chinese add it into list. Else remove and add it later
						if(tempString.substring(tempString.length()-1, tempString.length()).trim().equals("日".trim()))
						{
							ConvertDate convertDate = new ConvertDate(tempString, filePath);
							listDateMonth.add(convertDate);
						}
						else
						{
							subTempString = tempString.substring(0, tempString.indexOf("日")+1);
							ConvertDate convertDate = new ConvertDate(subTempString, filePath);
							listDateMonth.add(convertDate);
						}
					}
				}
				else
				{
					break;
				}
			}
		}
		return listDateMonth;
	}
	
	
	public static ArrayList<ConvertDate> getMonthYearCN(String content, String filePath) throws Exception
	{
		int lastIndex = 0;
		String subTempString;
		String tempString;
		
		ArrayList<ConvertDate>listDateMonth = new ArrayList<>();
		
		while(lastIndex != -1)
		{
			lastIndex = content.indexOf("年 ", lastIndex);
			if(lastIndex != -1)
			{
				lastIndex += 2;
				tempString = content.substring(lastIndex-7, lastIndex + 8 ).trim();
				
				if(Character.isDigit(tempString.charAt(0)))
				{
					if(tempString.contains("月") && tempString.contains(" ") && !tempString.contains("日"))
					{
						if(tempString.substring(tempString.length()-1, tempString.length()).trim().equals("月".trim()))
						{
							ConvertDate convertDate = new ConvertDate(tempString, filePath);
							listDateMonth.add(convertDate);
						}
						else
						{
							subTempString = tempString.substring(0, tempString.indexOf("月")+1);
							ConvertDate convertDate = new ConvertDate(subTempString, filePath);
							listDateMonth.add(convertDate);
						}
					}
				}
				else
				{
					break;
				}
			}
		}
	
		return listDateMonth;
	}
	
	public static String convertFormatAfterTranslated(String input)
	{
		input = input.toLowerCase();
		String subString  = "";
		if(input.contains("ngày"))
		{
			input = input.toLowerCase();
			input = input.replace("tháng", "/");
			input = input.replace("năm", "/");
			input = input.replace("đến", "-");
			input = input.replace(" ", "");
			input = input.replace("ngày", "ngày ");
		}
		else if(!input.contains("ngày"))
		{
			
			input = input.replace("năm", "/");
			input = input.replace("đến", "-");
			input = input.replace(" ", "");
			input = input.replace("tháng", "tháng ");
		}
		
		return input;
	}
	
	public static void handleArrayListDate(ArrayList<ConvertDate>listVN, ArrayList<ConvertDate>listCN) throws Exception
	{
		ArrayList<ConvertDate>listCNTranslated = new ArrayList<>();
		Translate translate = new Translate();
		//Translate date time and then convert format to easy to compare with Vietnamese
		for(int i = 0 ; i < listCN.size(); i++)
		{
			ConvertDate convertDate = new ConvertDate(convertFormatAfterTranslated
					(translate.translateDateMonth(listCN.get(i).getContent())), listCN.get(i).getFilePath());
			listCNTranslated.add(convertDate);
		}
	
		for(int i = 0 ; i < listCNTranslated.size(); i++)
		{
			
			for(int j = 0 ; j < listVN.size(); j++)
			{
				//Check similarity of content and show located of each content
				CompareTitle.printSimilarityDate(listCNTranslated.get(i), listVN.get(j));
				
			}
		}
		
		
	}
	
	public static void similarityFile(ArrayList<ConvertDate>listVN, ArrayList<ConvertDate>listCN) throws Exception
	{
		String tempPath = "";
		String tempPathVN = "";
		int count = 0;
		int countVN = 0;
		ArrayList<ConvertDate>listCNTranslated = new ArrayList<>();
		Translate translate = new Translate();
		//Translate date time and then convert format to easy to compare with Vietnamese
		for(int i = 0 ; i < listCN.size(); i++)
		{
			ConvertDate convertDate = new ConvertDate(convertFormatAfterTranslated
					(translate.translateDateMonth(listCN.get(i).getContent())), listCN.get(i).getFilePath());
			listCNTranslated.add(convertDate);
		}
		
		for(int i = 0; i < listCNTranslated.size(); i++)//This loop to get fist element in array list
		{
			tempPath = listCN.get(i).getFilePath();
			for(int j = 0; j < listCNTranslated.size(); j++)//This loop to compare all elements with first selected element.
			{
				if(listCNTranslated.get(j).getFilePath().equals(tempPath))
				{
					for(int k = 0 ; k < listVN.size(); k++)
					{
						if(k > 0)
						{
							tempPathVN = listVN.get(k-1).getFilePath();
						}
						
						//Check similarity of content and show located of each content
						if(CompareTitle.printSimilarityDate(listCNTranslated.get(j), listVN.get(k)) == true)
						{
							count++;
							if(listVN.get(k).getFilePath().equals(tempPathVN))
							{
								countVN ++;
							}
							
							
						}
					}
					if(count >0)
					{
						//System.out.println("Found " + count + " similarity with path "+ listCNTranslated.get(j).getFilePath());
					}
					if(countVN > 0)
					{
						System.out.println("Found " + countVN + " file similarity");
					}
					count = 0;
					countVN = 0;
				}
			}
		
		}
		
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese");
		final File folder2 = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese");
		listFilesForFolder(folder,folder2);
		
	}

}
