/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbzsogr.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;

/**
 *
 * @author PlayWolfYT
 */
public class CSVWriter {
        
    public static void writeCSV(Object[][] data, File file) throws Exception {
        if(!file.exists()) file.createNewFile();
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, Charset.forName("windows-1252")));
        
        // Write header
        writer.write("Benutzername;Name;Vorname;Klasse1;Klasse2");
        writer.newLine();
        
        // Write data
        for(Object[] row : data) {
            String writeOut = "";
            for(Object column : row) {
                String toWrite = column != null ? column.toString() : "";
                
                writeOut += toWrite + ";";
            }
            
            // Remove last semicolon
            writeOut = writeOut.substring(0, writeOut.length()-1);
            
            writer.write(writeOut);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
    
}
