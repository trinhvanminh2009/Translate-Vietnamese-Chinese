/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


/**
 *
 * @author Quang
 */
public class WriteSentences {

    public static final String FOLDER = System.getProperty("user.dir") + "/SentenceMatchingNew/";

    public WriteSentences() {
        
    }

    public static int getFileIndex() {
        File file = new File(FOLDER);
        if (file.exists()) {

        } else {
            new File(FOLDER).mkdir();
        }
        return new File(FOLDER).listFiles().length;
    }

    public static void writeAppend(String vn, String cn, String percent, int index) {

        //// ghi file
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FOLDER + "/" + index + ".txt"), "utf-8"))) {
            writer.write(vn + "\n");
            writer.write("-----------------\n");
            writer.write(cn + "\n");
            writer.write("-----------------\n");
            writer.write("similarity about " + percent + "%\n");
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
