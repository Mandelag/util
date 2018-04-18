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
    private ExtractorMarker[] markers = new ExtractorMarker[0];
    
    public Extractor(Reader r, ExtractorMarker[] markers) {
        this.reader = r;
        if (markers != null) {
            this.markers = markers;
        }
    }
    
    public void extract() throws IOException {
        int read;
        char readChar;
        while ((read = reader.read() ) >= 0) {
            readChar = (char) read;
            for (int x=0; x < markers.length; x++ ) {
                markers[x].listen(readChar);
            }
        }
    }
}
