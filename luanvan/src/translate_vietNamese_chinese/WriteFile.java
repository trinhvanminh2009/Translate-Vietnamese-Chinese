package translate_vietNamese_chinese;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteFile {

	public static void writeDateTimeSimilarity(String content)
	{
		try{
			File file = new File(System.getProperty("user.dir")+"/SimilarityByDate.txt");
			if(file.exists())
			{
				Files.write(Paths.get(System.getProperty("user.dir")+"/SimilarityByDate.txt") ,
						content.getBytes(), StandardOpenOption.APPEND);
			}
			else{
				Writer writer = new BufferedWriter(new OutputStreamWriter
						(new FileOutputStream(System.getProperty("user.dir")+"/SimilarityByDate.txt"),"utf-8"));
				writer.write(content);
				writer.close();
			}
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
