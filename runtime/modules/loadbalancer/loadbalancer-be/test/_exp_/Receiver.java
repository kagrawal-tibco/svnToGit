package _exp_;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/*
* Author: Ashwin Jayaprakash / Date: Apr 12, 2010 / Time: 4:30:16 PM
*/
public class Receiver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, JMSException {
        File directory = new File(args[0]);

        for (; ;) {
            System.out.println("Scanning..." + directory.getAbsolutePath());

            File[] files = directory.listFiles();

            for (File file : files) {
                System.out.println("Reading..." + file.getAbsolutePath());

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                Message message = (Message) ois.readObject();

                ois.close();

                System.out.println("File contents: " + message);

                message.acknowledge();

                file.delete();
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
    }
}
