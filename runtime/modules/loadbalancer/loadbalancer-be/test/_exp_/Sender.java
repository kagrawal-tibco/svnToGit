package _exp_;

import javax.jms.*;
import java.util.Vector;

/*
* Author: Ashwin Jayaprakash / Date: Apr 12, 2010 / Time: 4:16:03 PM
*/
public class Sender {
    /*-----------------------------------------------------------------------
     * Parameters
     *----------------------------------------------------------------------*/

    String serverUrl = null;

    String userName = null;

    String password = null;

    String name = null;

    Vector<Integer> data = new Vector<Integer>();

    /*-----------------------------------------------------------------------
     * Variables
     *----------------------------------------------------------------------*/
    Connection connection = null;

    Session session = null;

    MessageProducer msgProducer = null;

    Destination destination = null;

    public Sender(String[] args) {
        parseArgs(args);


        /* print parameters */
        System.err.println("\n------------------------------------------------------------------------");
        System.err.println("Sender SAMPLE");
        System.err.println("------------------------------------------------------------------------");
        System.err.println("Server....................... " + ((serverUrl != null) ? serverUrl : "localhost"));
        System.err.println("User......................... " + ((userName != null) ? userName : "(null)"));
        System.err.println("Destination.................. " + name);
        System.err.println("Message Text................. ");
        for (int i = 0; i < data.size(); i++) {
            System.err.println(data.elementAt(i));
        }
        System.err.println("------------------------------------------------------------------------\n");

        try {
            TextMessage msg;
            int i;

            if (data.size() == 0) {
                System.err.println("***Error: must specify at least one message text\n");
                usage();
            }

            System.err.println("Publishing to destination '" + name + "'\n");

            ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);

            connection = factory.createConnection(userName, password);

            /* create the session */
            session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            /* create the destination */
            if (false) {
                destination = session.createTopic(name);
            }
            else {
                destination = session.createQueue(name);
            }

            /* create the producer */
            msgProducer = session.createProducer(null);

            /* publish messages */
            doPublish();

            session.close();

            /* close the connection */
            connection.close();
        }
        catch (JMSException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    protected void doPublish() throws JMSException {

        for (int i = 0; i < data.size(); i++) {
            //todo Change this for diff tests. 
            //Message jmsMsg = session.createMessage();
            //Message jmsMsg = session.createTextMessage();
            //Message jmsMsg = session.createBytesMessage();
            //Message jmsMsg = session.createMapMessage();
            Message jmsMsg = session.createObjectMessage();

            jmsMsg.setStringProperty("_ns_", "www.tibco.com/be/ontology/Events/TestEventA");

            jmsMsg.setStringProperty("_nm_", "TestEventA");

            Integer msg = data.elementAt(i);

            String x = "." + System.currentTimeMillis() + "." + System.nanoTime();

            jmsMsg.setStringProperty("_extId_", "/hello/" + msg + x);

            jmsMsg.setStringProperty("name", "Hello-" + (msg % 10));

            /* publish message */
            msgProducer.send(destination, jmsMsg);

            System.err.println("Published message: " + jmsMsg);
        }
    }

    /*-----------------------------------------------------------------------
    * usage
    *----------------------------------------------------------------------*/
    private void usage() {
        System.err.println("\nUsage: java Sender [options] [ssl options]");
        System.err.println("                                <message-text-1>");
        System.err.println("                                [<message-text-2>] ...");
        System.err.println("\n");
        System.err.println("   where options are:");
        System.err.println("");
        System.err.println("   -server   <server URL>  - EMS server URL, default is local server");
        System.err.println("   -user     <user name>   - user name, default is null");
        System.err.println("   -password <password>    - password, default is null");
        System.err.println("   -topic    <topic-name>  - topic name, default is \"topic.sample\"");
        System.err.println("   -queue    <queue-name>  - queue name, no default");
        System.err.println("   -help-ssl                 - help on ssl parameters");
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
            else if (args[i].compareTo("-queue") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                name = args[i + 1];
                i += 2;
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
            else if (args[i].startsWith("-repeat")) {
                String times = args[i + 1];

                int repeat = Integer.parseInt(times);
                for (int j = 1; j <= repeat; j++) {
                    data.add(j);
                }

                i += 2;
            }
            else {
                data.addElement(Integer.parseInt(args[i]));
                i++;
            }
        }
    }

    /*-----------------------------------------------------------------------
     * main
     *----------------------------------------------------------------------*/
    public static void main(String[] args) {
        Sender t = new Sender(args);
    }
}
