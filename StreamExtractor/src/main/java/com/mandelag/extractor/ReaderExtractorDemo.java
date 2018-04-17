package com.mandelag.extractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class ReaderExtractorDemo {
    public static void main(String[] args) {
        ReaderExtractor namaExtract = new ReaderExtractor("Nama : ", "</font>", System.out::println);
        ReaderExtractor alamatExtract = new ReaderExtractor("Alamat : ", "</font>", System.out::println);
        ReaderExtractor desaKelExtract = new ReaderExtractor("Desa/Kelurahan : ", "</font>", System.out::println);
        ReaderExtractor urlFotoExtract = new ReaderExtractor("Foto : <img src=\"", "\" width=\"50px\"", System.out::println);
        ReaderExtractor latlonExtract = new ReaderExtractor("\t\tvar marker = L.marker(L.latLng(", "),{icon:", System.out::println);
        
        try (Reader r = new InputStreamReader( new FileInputStream("C:\\050000.SD"), "UTF8")) {
            int read;
            char readChar;
            while ((read = r.read() ) >= 0) {
                readChar = (char) read;
                namaExtract.listen(readChar);
                alamatExtract.listen(readChar);
                desaKelExtract.listen(readChar);
                urlFotoExtract.listen(readChar);
                latlonExtract.listen(readChar);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReaderExtractorDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
