/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mandelag.extractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keenan
 */
public class ReaderExtractorDemo {
    public static void main(String[] args) {
        ReaderExtractor extractor = new ReaderExtractor("\t\tvar marker = L.marker(L.latLng(", "),{icon:", System.out::println);
        try (Reader r = new InputStreamReader( new FileInputStream("C:\\050000.SD"), "UTF8")) {
            int read = 0;
            while ((read = r.read() ) >= 0) {
                extractor.listen((char)read);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReaderExtractorDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
