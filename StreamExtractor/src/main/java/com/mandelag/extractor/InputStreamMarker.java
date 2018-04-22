package com.mandelag.extractor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

/**
 *
 * @author Keenan Gebze (@mandelag)
 */
public class InputStreamMarker {
    
    private int[] startBytes;
    private int[] stopBytes;
    private Consumer<byte[]> callback;
    private int cursor = 0;
    private boolean extractMode = false;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream(1024);
    
    public InputStreamMarker(int[] start, int[] stop, Consumer<byte[]> callback) {
        this.startBytes = start;
        this.stopBytes = stop;
        this.callback = callback;
    }

    public void listen(int a) {
        if(!extractMode) {
            if (a == startBytes[cursor]) {
                cursor++;
                if (cursor == startBytes.length) {
                    extractMode = true;
                    cursor = 0;
                }
            } else {
                cursor = 0;
            }
        } else {
            buffer.write(a);
            if (a == stopBytes[cursor]) {
                cursor++;
                if (cursor == stopBytes.length) {
                    extractMode = false;
                    cursor = 0;
                    //callback.accept(buffer.substring(0, buffer.length()-stopBytes.length));
                    try {
                        buffer.flush();
                        callback.accept(buffer.toByteArray());
                    }catch(IOException e){
                        System.err.println(e);
                    }
                    buffer.reset();
                }
            }else {
                cursor = 0;
            }
        }
    }
}
