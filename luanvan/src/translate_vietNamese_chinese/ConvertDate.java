package translate_vietNamese_chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ConvertDate {

	public static void listFilesForFolder(final File folder)
	{
		for(final File file : folder.listFiles())
		{
			if(file.isDirectory())
			{
				listFilesForFolder(file);
			}
			else{	
				readFileFromPath(file.getPath());
		
			}
		}
	}
	
	private static void handleFileString(String content, String key)
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
		String mainContent = content.substring(listIndex.get(1), listIndex.get(2));
		getDayMonthYearVN(mainContent);
		getMonthYearVN(mainContent);
	}
	
	
	
	private static void getMonthYearVN(String content)
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
		
		for(int i = 0; i < listMonthYear.size(); i++)
		{
			System.out.println(listMonthYear.get(i));
		}
		
	}
	
	private static void getDayMonthYearVN(String content)
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
		
		for(int i = 0; i < listDateMonthYear.size(); i++)
		{
			System.out.println(listDateMonthYear.get(i));
		}
		
	}
	
	public static void readFileFromPath(String filePath) 
	{
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
			handleFileString(everything, key);
			bufferedReader.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final File folder = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese");
		listFilesForFolder(folder);
		//getDayMonthYearVN("ngày 22/12/1021 trước trước qua anh gặp em đi chơi");

		
		
	}

}
