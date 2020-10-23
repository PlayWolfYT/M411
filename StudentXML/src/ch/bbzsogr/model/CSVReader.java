/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbzsogr.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *
 * @author PlayWolfYT
 */
public class CSVReader {
    
    private static File file = new File("C:/temp/01-schueler.csv");
    
    public static enum StudentFields {
        USERNAME,
        SURNAME,
        NAME,
        CLASS_1,
        CLASS_2;
    }
    
    public static Object[][] getData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file, Charset.forName("windows-1252") /* Specify charset to have valid umlaute */));
            
            String[][] data = new String[1300][5];
            
            String row;
            int index = 0;
            String delimiter = ";";
            
            // Skip first line
            reader.readLine();
            
            while((row = reader.readLine()) != null && row.contains(delimiter)) {    
                System.out.println(row);
                String[] rowData = row.split(delimiter);
                if(rowData.length < 4 || rowData[0] == null) break;
                
                
                
                data[index][StudentFields.USERNAME.ordinal()] = rowData[StudentFields.USERNAME.ordinal()];
                data[index][StudentFields.SURNAME.ordinal()] = rowData[StudentFields.SURNAME.ordinal()];
                data[index][StudentFields.NAME.ordinal()] = rowData[StudentFields.NAME.ordinal()];
                data[index][StudentFields.CLASS_1.ordinal()] = rowData[StudentFields.CLASS_1.ordinal()];
                if(rowData.length == 5) {
                    data[index][StudentFields.CLASS_2.ordinal()] = rowData[StudentFields.CLASS_2.ordinal()];
                }
                
                
                index++;
            }
            
            /** Not needed. Removes <code>null</code> entries to reduce size of the data. */
            data = stripData(data);
            
            return data;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setFile(File file) {
        CSVReader.file = file;
    }
    
    private static String[][] stripData(String[][] data) {
        return Arrays.stream(data).filter(arr -> arr.length > 0 && arr[0] != null).toArray(s -> new String[s][5]);
    }
    
}
