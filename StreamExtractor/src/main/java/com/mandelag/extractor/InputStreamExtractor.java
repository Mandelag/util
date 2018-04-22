package com.mandelag.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 * @author Keenan
 */
public class InputStreamExtractor {
    
    private InputStream inputStream;
    private InputStreamMarker[] markers = new InputStreamMarker[0];
    
    public InputStreamExtractor(InputStream inputStream, InputStreamMarker[] markers) {
        this.inputStream = inputStream;
        if (markers != null) {
            this.markers = markers;
        }
    }
    
    public void extract() throws IOException {
        int read;
        while ((read = inputStream.read() ) >= 0) {
            for (int x=0; x < markers.length; x++ ) {
                markers[x].listen(read);
            }
        }
    }
}
