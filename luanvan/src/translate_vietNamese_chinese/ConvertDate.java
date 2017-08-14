package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


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
		 ArrayList<String>resultVN = new ArrayList<>();
		 ArrayList<String>resultCN = new ArrayList<>();
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
		
		handleArrayListDate(resultVN, resultCN);
	}
	
	public static ArrayList<String> readFileFromPath(String filePath) throws Exception 
	{
		ArrayList<String> result = new ArrayList<>();
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
	
	private static ArrayList<String> handleFileString(String content, String key, String filePath) throws Exception
	{
		//Check file and remove key to get main contain
		int lastIndex = 0;
		ArrayList<String>resultList = new ArrayList<>();
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
	
	
	private static ArrayList<String> getMonthYearVN(String content, String filePath)
	{
		ArrayList<String>listMonthYear = new ArrayList<>();
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
					listMonthYear.add(tempString.trim());
				}
			}
			tempString = "";
		}
		return listMonthYear;
	}
	
	private static ArrayList<String> getDayMonthYearVN(String content, String filePath)
	{
		ArrayList<String>listDateMonthYear = new ArrayList<>();
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
					listDateMonthYear.add(tempString.trim());
				}
			}
			tempString = "";
		}
		
		return listDateMonthYear;
		
	}
	

	
	public static ArrayList<String> getDateMonthYearCN(String content, String filePath) throws Exception
	{
		int lastIndex = 0;
		String subTempString;
		String tempString;
		ArrayList<String>listDateMonth = new ArrayList<>();
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
							listDateMonth.add(tempString);
						}
						else
						{
							subTempString = tempString.substring(0, tempString.indexOf("日")+1);
							listDateMonth.add(subTempString);
						}
					}
				}
				else
				{
					break;
				}
			}
		}
		/*
		for(int i = 0; i < listDateMonth.size(); i++)
		{
			Translate translate = new Translate();
			 convertFormatAfterTranslated(translate.translateDateMonth(listDateMonth.get(i)), filePath);
		}*/
		return listDateMonth;
	}
	
	
	public static ArrayList<String> getMonthYearCN(String content, String filePath) throws Exception
	{
		int lastIndex = 0;
		String subTempString;
		String tempString;
		
		ArrayList<String>listDateMonth = new ArrayList<>();
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
							listDateMonth.add(tempString);
						}
						else
						{
							subTempString = tempString.substring(0, tempString.indexOf("月")+1);
							listDateMonth.add(subTempString);
						}
					}
				}
				else
				{
					break;
				}
			}
		}
		/*for(int i = 0; i < listDateMonth.size(); i++)
		{
			//System.out.println(listDateMonth.get(i));
			Translate translate = new Translate();
			translate.translateDateMonth(listDateMonth.get(i));
		}*/
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
	
	public static void handleArrayListDate(ArrayList<String>listVN, ArrayList<String>listCN) throws Exception
	{
		ArrayList<String>listCNTranslated = new ArrayList<>();
		Translate translate = new Translate();
		for(int i = 0 ; i < listCN.size(); i++)
		{
			listCNTranslated.add(convertFormatAfterTranslated(translate.translateDateMonth(listCN.get(i))));
		}
	
		//CompareTitle.printSimilarity("222", "222");
		for(int i = 0 ; i < listCNTranslated.size(); i++)
		{
			
			for(int j = 0 ; j < listVN.size(); j++)
			{
				
				//System.out.println(listVN.get(j));
				CompareTitle.printSimilarity(listCNTranslated.get(i), listVN.get(j));
				
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
