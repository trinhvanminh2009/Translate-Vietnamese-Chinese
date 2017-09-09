package translate_vietNamese_chinese.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ConnectToExcel {

	public static void writeToExcel()
	{
		
		
		 File file = new File("D:\\Dowloads\\luanvan\\luanvan\\sheetData.xls");
		 try {
			WritableWorkbook myExcel = Workbook.createWorkbook(file);
			WritableSheet mySheet = myExcel.createSheet("File Size", 0);
			Label label = new Label(0,0,"Data1");
			Label label2 = new Label(1,1,"Data2");
			Label label3 = new Label(1,2,"Data3");
			mySheet.addCell(label);
			mySheet.addCell(label2);
			mySheet.addCell(label3);
		
			myExcel.write();
			myExcel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		writeToExcel();

	}

}
