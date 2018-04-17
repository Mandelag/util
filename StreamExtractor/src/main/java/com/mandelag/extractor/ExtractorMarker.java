package com.mandelag.extractor;

import java.util.function.Consumer;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class ExtractorMarker {
    
    private String startString;
    private String stopString;
    private Consumer<String> callback;
    private int cursor = 0;
    private boolean extractMode = false;
    private StringBuffer buffer = new StringBuffer();
    
    public ExtractorMarker(String start, String stop, Consumer<String> callback) {
        this.startString = start;
        this.stopString = stop;
        this.callback = callback;
    }

    void listen(char a) {
        if(!extractMode) {
            if (a == startString.codePointAt(cursor)) {
                cursor++;
                if (cursor == startString.length()) {
                    extractMode = true;
                    cursor = 0;
                }
            } else {
                cursor = 0;
            }
        } else {
            buffer.append(a);
            if (a == stopString.codePointAt(cursor)) {
                cursor++;
                if (cursor == stopString.length()) {
                    extractMode = false;
                    cursor = 0;
                    callback.accept(buffer.substring(0, buffer.length()-stopString.length()));
                    buffer.setLength(0);
                }
            }else {
                cursor = 0;
            }
        }
    }
}
