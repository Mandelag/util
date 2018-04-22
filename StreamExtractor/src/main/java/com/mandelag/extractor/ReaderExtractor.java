package com.mandelag.extractor;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Keenan
 */
public class ReaderExtractor {
    
    private Reader reader;
    private ExtractorMarker[] markers = new ExtractorMarker[0];
    
    public ReaderExtractor(Reader r, ExtractorMarker[] markers) {
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
