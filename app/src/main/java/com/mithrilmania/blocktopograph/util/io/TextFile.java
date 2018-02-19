package com.mithrilmania.blocktopograph.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFile {

    /** Read text file in sync, only use this when appropriate... not in UI **/
    public static String readTextFile(File txtFile){
        StringBuilder text = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txtFile));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        return text.toString();
    }

    public static String readTextFileFirstLine(File txtFile){
        String text = null;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txtFile));
            text = br.readLine();
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}