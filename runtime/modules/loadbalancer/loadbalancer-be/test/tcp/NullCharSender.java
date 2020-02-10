package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
* Author: Ashwin Jayaprakash / Date: May 3, 2010 / Time: 3:27:36 PM
*/
public class NullCharSender {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        for (; ;) {
            outToServer.writeBytes("abcdefghijklmnopqrst" + '\0');
            outToServer.flush();

            Thread.sleep(2500);
        }
    }
}
