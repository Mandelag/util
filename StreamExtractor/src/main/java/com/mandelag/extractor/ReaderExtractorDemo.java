package com.mandelag.extractor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class ReaderExtractorDemo {
    public static void main(String[] args) {
        testInputStream();
    }
    
    public static void testReader() {
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
    
    public static void testInputStream() {
        
        Consumer<byte[]> byteConsumer = (b) -> {
            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\keenan\\extract.jpeg"))){
                bos.write(new byte[]{(byte)255,(byte)216,(byte)255,(byte)224});
                bos.write(b);
                bos.flush();
            }catch(IOException e){
            }
        };
        
        InputStreamMarker imageExtract = new InputStreamMarker(new int[]{255,216,255,224}, new int[]{45,45,109,121}, byteConsumer);
        
        long t = System.currentTimeMillis();
        try (InputStream is = new BufferedInputStream(new FileInputStream("C:\\Users\\keenan\\image0.jpeg"))) {
            InputStreamExtractor extractor = new InputStreamExtractor(is, new InputStreamMarker[]{imageExtract});            
            extractor.extract();
        } catch (IOException ex) {
            Logger.getLogger(ReaderExtractorDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println(System.currentTimeMillis()-t);
    }
    
}
