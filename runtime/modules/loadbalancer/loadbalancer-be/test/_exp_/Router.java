package _exp_;

import com.tibco.cep.loadbalancer.util.Helper;

import javax.jms.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/*
* Author: Ashwin Jayaprakash / Date: Apr 12, 2010 / Time: 4:22:48 PM
*/
public class Router implements ExceptionListener {
    /*-----------------------------------------------------------------------
     * Parameters
     *----------------------------------------------------------------------*/

    String serverUrl = null;

    String userName = null;

    String password = null;

    String name = "topic.sample";

    File directory = null;

    boolean useTopic = true;

    /*-----------------------------------------------------------------------
    * Variables
    *----------------------------------------------------------------------*/
    Connection connection = null;

    Session session = null;

    MessageConsumer msgConsumer = null;

    Destination destination = null;

    public Router(String[] args) {
        parseArgs(args);

        /* print parameters */
        System.err.println("\n------------------------------------------------------------------------");
        System.err.println("tibjmsMsgConsumer SAMPLE");
        System.err.println("------------------------------------------------------------------------");
        System.err.println("Server....................... " + ((serverUrl != null) ? serverUrl : "localhost"));
        System.err.println("User......................... " + ((userName != null) ? userName : "(null)"));
        System.err.println("Destination.................. " + name);
        System.err.println("------------------------------------------------------------------------\n");

        try {
            run();
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /*-----------------------------------------------------------------------
     * usage
     *----------------------------------------------------------------------*/
    void usage() {
        System.err.println("\nUsage: tibjmsMsgConsumer [options] [ssl options]");
        System.err.println("");
        System.err.println("   where options are:");
        System.err.println("");
        System.err.println(" -server   <server URL> - EMS server URL, default is local server");
        System.err.println(" -user     <user name>  - user name, default is null");
        System.err.println(" -password <password>   - password, default is null");
        System.err.println(" -topic    <topic-name> - topic name, default is \"topic.sample\"");
        System.err.println(" -queue    <queue-name> - queue name, no default");
        System.err.println(" -directory    <directory>");
        System.err.println(" -help-ssl              - help on ssl parameters\n");
        System.exit(0);
    }

    /*-----------------------------------------------------------------------
     * parseArgs
     *----------------------------------------------------------------------*/
    void parseArgs(String[] args) {
        int i = 0;

        while (i < args.length) {
            if (args[i].compareTo("-server") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                serverUrl = args[i + 1];
                i += 2;
            }
            else if (args[i].compareTo("-topic") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                name = args[i + 1];
                i += 2;
            }
            else if (args[i].compareTo("-queue") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                name = args[i + 1];
                i += 2;
                useTopic = false;
            }
            else if (args[i].compareTo("-user") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                userName = args[i + 1];
                i += 2;
            }
            else if (args[i].compareTo("-password") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                password = args[i + 1];
                i += 2;
            }
            else if (args[i].compareTo("-help") == 0) {
                usage();
            }
            else if (args[i].startsWith("-ssl")) {
                i += 2;
            }
            else if (args[i].compareTo("-directory") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                directory = new File(args[i + 1]);
                i += 2;
            }
            else {
                System.err.println("Unrecognized parameter: " + args[i]);
                usage();
            }
        }
    }


    /*---------------------------------------------------------------------
     * onException
     *---------------------------------------------------------------------*/
    public void onException(
            JMSException e) {
        /* print the connection exception status */
        System.err.println("CONNECTION EXCEPTION: " + e.getMessage());
    }

    /*-----------------------------------------------------------------------
     * run
     *----------------------------------------------------------------------*/
    void run() throws JMSException {
        Message msg = null;
        String msgType = "UNKNOWN";

        System.err.println("Subscribing to destination: " + name + "\n");

        ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);

        /* create the connection */
        connection = factory.createConnection(userName, password);

        /* create the session */
        session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

        /* set the exception listener */
        connection.setExceptionListener(this);

        /* create the destination */
        if (useTopic) {
            destination = session.createTopic(name);
        }
        else {
            destination = session.createQueue(name);
        }

        /* create the consumer */
        msgConsumer = session.createConsumer(destination);

        /* start the connection */
        connection.start();

        /* read messages */
        while (true) {
            /* receive the message */
            msg = msgConsumer.receive();
            if (msg == null) {
                break;
            }

            handleMsg(msg);
        }

        /* close the connection */
        connection.close();
    }

    protected void handleMsg(Message message) {
        try {
            System.err.println("Received message: " + message);

            String s = Helper.$hashMD5(message.getJMSMessageID());
            File file = new File(directory, s);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(message);
            oos.close();

            System.err.println("Wrote message to : " + file.getAbsolutePath());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-----------------------------------------------------------------------
    * main
    *----------------------------------------------------------------------*/
    public static void main(String[] args) {
        new Router(args);
    }
}
