package translate_vietNamese_chinese;

public class PercentSimilarityTitle {
	private float percentSimilarity;
	private String pathVN;
	private String pathCN;
	private String titleVN;
	private String titleCN;

	public PercentSimilarityTitle(float percentSimilarity, String pathVN, String pathCN) {
		super();
		this.percentSimilarity = percentSimilarity;
		this.pathVN = pathVN;
		this.pathCN = pathCN;
	}
	
	

	public PercentSimilarityTitle(float percentSimilarity, String pathVN, String pathCN, String titleVN, String titleCN) {
		super();
		this.percentSimilarity = percentSimilarity;
		this.pathVN = pathVN;
		this.pathCN = pathCN;
		this.titleVN = titleVN;
		this.titleCN = titleCN;
	}



	public String getTitleVN() {
		return titleVN;
	}

	public void setTitleVN(String titleVN) {
		this.titleVN = titleVN;
	}

	public String getTitleCN() {
		return titleCN;
	}

	public void setTitleCN(String titleCN) {
		this.titleCN = titleCN;
	}

	public float getPercentSimilarity() {
		return percentSimilarity;
	}

	public void setPercentSimilarity(float percentSimilarity) {
		this.percentSimilarity = percentSimilarity;
	}

	public String getPathVN() {
		return pathVN;
	}

	public void setPathVN(String pathVN) {
		this.pathVN = pathVN;
	}

	public String getPathCN() {
		return pathCN;
	}

	public void setPathCN(String pathCN) {
		this.pathCN = pathCN;
	}

}
