package translate_vietNamese_chinese;

public class CompareResultObject {
	private String filePathVN;
	private String filePathCN;
	private int percent;
	
	public CompareResultObject(String filePathVN, String filePathCN, int percent) {
		super();
		this.filePathVN = filePathVN;
		this.filePathCN = filePathCN;
		this.percent = percent;
	}
	public String getFilePathVN() {
		return filePathVN;
	}
	public void setFilePathVN(String filePathVN) {
		this.filePathVN = filePathVN;
	}
	public String getFilePathCN() {
		return filePathCN;
	}
	public void setFilePathCN(String filePathCN) {
		this.filePathCN = filePathCN;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	
}
