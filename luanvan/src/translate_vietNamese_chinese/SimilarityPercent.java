package translate_vietNamese_chinese;

import java.util.ArrayList;

public class SimilarityPercent {
	private String pathVN;
	private String pathCN;
	private ArrayList<String>listPercentVN;
	private ArrayList<String>listPercentCN;
	private float percentSimilarityByPercent;
	
	
	public SimilarityPercent(String pathVN, String pathCN, ArrayList<String> listPercentVN,
			ArrayList<String> listPercentCN, float percentSimilarityByPercent) {
		super();
		this.pathVN = pathVN;
		this.pathCN = pathCN;
		this.listPercentVN = listPercentVN;
		this.listPercentCN = listPercentCN;
		this.percentSimilarityByPercent = percentSimilarityByPercent;
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
	public ArrayList<String> getListPercentVN() {
		return listPercentVN;
	}
	public void setListPercentVN(ArrayList<String> listPercentVN) {
		this.listPercentVN = listPercentVN;
	}
	public ArrayList<String> getListPercentCN() {
		return listPercentCN;
	}
	public void setListPercentCN(ArrayList<String> listPercentCN) {
		this.listPercentCN = listPercentCN;
	}
	public float getPercentSimilarityByPercent() {
		return percentSimilarityByPercent;
	}
	public void setPercentSimilarityByPercent(float percentSimilarityByPercent) {
		this.percentSimilarityByPercent = percentSimilarityByPercent;
	}
	
	
	
	
	
	
	
	
	

}
