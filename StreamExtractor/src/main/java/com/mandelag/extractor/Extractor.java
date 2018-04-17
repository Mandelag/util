package com.mandelag.extractor;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 *
 * @author Keenan
 */
public class Extractor {
    
    private Reader reader;
    private ArrayList<ExtractorMarker> markers;
    
    public Extractor(Reader r) {
        this.reader = r;
        this.markers = new ArrayList<>();
    }
    
    public void extract() throws IOException {
        int read;
        char readChar;
        while ((read = reader.read() ) >= 0) {
            readChar = (char) read;
            for ( ExtractorMarker marker : markers) {
                marker.listen(readChar);
            }
        }
    }
    
    public Extractor addMarker(ExtractorMarker marker) {
        this.markers.add(marker);
        return this;
    }
    
}
