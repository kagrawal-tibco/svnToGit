package tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
* Author: Ashwin Jayaprakash / Date: May 3, 2010 / Time: 3:27:45 PM
*/
public class NullCharReader {
    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();

            InputStream inputStream = connectionSocket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] bytes = new byte[1024];

            while (true) {
                int c = bufferedInputStream.read(bytes);

                System.out.println("Read bytes: " + c + " -- " + new String(bytes, 0, c));
            }
        }
    }
}
