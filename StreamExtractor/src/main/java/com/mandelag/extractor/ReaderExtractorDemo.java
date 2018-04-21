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
        ExtractorMarker namaExtract = new ExtractorMarker("Nama : ", "</font>", System.out::println);
        ExtractorMarker alamatExtract = new ExtractorMarker("Alamat : ", "</font>", System.out::println);
        ExtractorMarker desaKelExtract = new ExtractorMarker("Desa/Kelurahan : ", "</font>", System.out::println);
        ExtractorMarker urlFotoExtract = new ExtractorMarker("Foto : <img src=\"", "\" width=\"50px\"", System.out::println);
        ExtractorMarker latlonExtract = new ExtractorMarker("\t\tvar marker = L.marker(L.latLng(", "),{icon:", (System.out::println));
        long t = System.currentTimeMillis();
        try (Reader r = new BufferedReader(new InputStreamReader( new FileInputStream("C:\\050000.SD"), "UTF8"))) {
        //try(Reader r = new FileReader("C:\\050000.SD")){
            Extractor extractor = new Extractor(r, new ExtractorMarker[]{namaExtract, alamatExtract, desaKelExtract, urlFotoExtract, latlonExtract});            
            extractor.extract();
        } catch (IOException ex) {
            Logger.getLogger(ReaderExtractorDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println(System.currentTimeMillis()-t);
    }
}
