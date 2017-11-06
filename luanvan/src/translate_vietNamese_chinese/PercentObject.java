package translate_vietNamese_chinese;

import java.util.ArrayList;

public class PercentObject {
	private String filePath;
	private ArrayList<String>listPercent;
	
	
	public PercentObject(String filePath, ArrayList<String> listPercent) {
		super();
		this.filePath = filePath;
		this.listPercent = listPercent;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public ArrayList<String> getListPercent() {
		return listPercent;
	}
	public void setListPercent(ArrayList<String> listPercent) {
		this.listPercent = listPercent;
	}
	public PercentObject() {
		super();
	}
	
	
}
