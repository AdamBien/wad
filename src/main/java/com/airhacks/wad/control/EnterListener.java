
package com.airhacks.wad.control;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author airhacks.com
 */
public interface EnterListener {

    static void registerEnterListener(Runnable listener) {
        InputStream in = System.in;
        Runnable task = () -> {
            int c;
            try {
                while ((c = in.read()) != -1) {
                    listener.run();
                }
            } catch (IOException ex) {
            }
        };
        new Thread(task).start();
    }

}
