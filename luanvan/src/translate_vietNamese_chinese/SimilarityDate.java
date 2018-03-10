package translate_vietNamese_chinese;

public class SimilarityDate {
	private String dateVN;
	private String dateCN;
	private String filePathVN;
	private String filePathCN;
	private int percentSimilarity;
	
	
	public SimilarityDate(String dateVN, String dateCN, String filePathVN, String filePathCN, int percentSimilarity) {
		super();
		this.dateVN = dateVN;
		this.dateCN = dateCN;
		this.filePathVN = filePathVN;
		this.filePathCN = filePathCN;
		this.percentSimilarity = percentSimilarity;
	}
        public SimilarityDate(String dateVN, String dateCN,  int percentSimilarity) {
		super();
		this.dateVN = dateVN;
		this.dateCN = dateCN;
		this.percentSimilarity = percentSimilarity;
	}
	
	public String getDateVN() {
		return dateVN;
	}
	public void setDateVN(String dateVN) {
		this.dateVN = dateVN;
	}
	public String getDateCN() {
		return dateCN;
	}
	public void setDateCN(String dateCN) {
		this.dateCN = dateCN;
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
	public int getPercentSimilarity() {
		return percentSimilarity;
	}
	public void setPercentSimilarity(int percentSimilarity) {
		this.percentSimilarity = percentSimilarity;
	}
	
	
}
