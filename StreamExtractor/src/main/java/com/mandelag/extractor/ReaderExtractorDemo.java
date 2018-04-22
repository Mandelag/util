package com.mandelag.extractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class ReaderExtractorDemo {
    public static void main(String[] args) {
        ReaderMarker namaExtract = new ReaderMarker("Nama : ", "</font>", System.out::println);
        ReaderMarker alamatExtract = new ReaderMarker("Alamat : ", "</font>", System.out::println);
        ReaderMarker desaKelExtract = new ReaderMarker("Desa/Kelurahan : ", "</font>", System.out::println);
        ReaderMarker urlFotoExtract = new ReaderMarker("Foto : <img src=\"", "\" width=\"50px\"", System.out::println);
        ReaderMarker latlonExtract = new ReaderMarker("\t\tvar marker = L.marker(L.latLng(", "),{icon:", (System.out::println));
        long t = System.currentTimeMillis();
        try (Reader r = new BufferedReader(new InputStreamReader( new FileInputStream("C:\\050000.SD"), "UTF8"))) {
            ReaderExtractor extractor = new ReaderExtractor(r, new ReaderMarker[]{namaExtract, alamatExtract, desaKelExtract, urlFotoExtract, latlonExtract});            
            extractor.extract();
        } catch (IOException ex) {
            Logger.getLogger(ReaderExtractorDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println(System.currentTimeMillis()-t);
    }
}
