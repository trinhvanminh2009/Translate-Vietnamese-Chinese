package translate_vietNamese_chinese.file_size_filters;

import java.io.File;
import java.util.ArrayList;

import translate_vietNamese_chinese.database.ConnectionToSQL;

public class CheckFileSize {

	private File fileChinese;
	private ArrayList<File> listFileVietnameseToCompare;

	public File getFileChinese() {
		return fileChinese;
	}

	public void setFileChinese(File fileChinese) {
		this.fileChinese = fileChinese;
	}

	public ArrayList<File> getListFileVietnameseToCompare() {
		return listFileVietnameseToCompare;
	}

	public void setListFileVietnameseToCompare(ArrayList<File> listFileVietnameseToCompare) {
		this.listFileVietnameseToCompare = listFileVietnameseToCompare;
	}

	public CheckFileSize(File fileChinese, ArrayList<File> listFileVietnameseToCompare) {
		super();
		this.fileChinese = fileChinese;
		this.listFileVietnameseToCompare = listFileVietnameseToCompare;
	}

	private static ArrayList<CheckFileSize> getListFileSizeShouldBeCheck(File filePathChinese,
			File filePathVietnamese) {
		ArrayList<CheckFileSize> checkFileSizes = new ArrayList<>();
		ArrayList<File> listFileVietnameseToCompare;
		int i, j;

		for (i = 0; i < filePathChinese.listFiles().length; i++) {
			listFileVietnameseToCompare = new ArrayList<>();
			for (j = 0; j < filePathVietnamese.listFiles().length; j++) {
				// Make sure 2 file just bigger or lesser than other files 2kb,
				// no more

				if (((filePathChinese.listFiles()[i].length() - filePathVietnamese.listFiles()[j].length()) < 2000)
						&& ((filePathChinese.listFiles()[i].length()
								- filePathVietnamese.listFiles()[j].length()) > -2000)) {
					listFileVietnameseToCompare.add(filePathVietnamese.listFiles()[j]);
				}
			}
			checkFileSizes.add(new CheckFileSize(filePathChinese.listFiles()[i], listFileVietnameseToCompare));

		}
		for (i = 0; i < checkFileSizes.size(); i++) {
			// Insert into SQL server
			ConnectionToSQL.insertFileChinese(checkFileSizes.get(i).getFileChinese().getPath(),
					checkFileSizes.get(i).getFileChinese().length());
			for (j = 0; j < checkFileSizes.get(i).getListFileVietnameseToCompare().size(); j++) {
				ConnectionToSQL.insertFileVietnamese(
						checkFileSizes.get(i).getListFileVietnameseToCompare().get(j).getPath(),
						checkFileSizes.get(i).getListFileVietnameseToCompare().get(j).length(),
						checkFileSizes.get(i).getFileChinese().getPath());
			}
			System.out.println("-----------------------Done " + i + "------------------------------------");

		}
		System.out.println(checkFileSizes.size());
		return checkFileSizes;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final File folderVietnamese = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politics_VietNamese");
		final File folderChinese = new File("D:/Dowloads/luanvan/luanvan/DATA/Politics/Politis_Chinese");
		getListFileSizeShouldBeCheck(folderChinese, folderVietnamese);

	}

}
