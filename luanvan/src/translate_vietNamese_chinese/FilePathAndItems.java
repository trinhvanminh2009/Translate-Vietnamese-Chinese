package translate_vietNamese_chinese;

import java.util.ArrayList;

public class FilePathAndItems {
	
	private String filePath;
	private ArrayList<String>listDate;
	
	
	public FilePathAndItems(String filePath, ArrayList<String> listDate) {
		super();
		this.filePath = filePath;
		this.listDate = listDate;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ArrayList<String> getListDate() {
		return listDate;
	}
	public void setListDate(ArrayList<String> listDate) {
		this.listDate = listDate;
	}
	
	
}
