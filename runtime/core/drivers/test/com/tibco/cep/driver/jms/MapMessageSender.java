package com.tibco.cep.driver.jms;

import javax.jms.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * User: nprade
 * Date: Nov 18, 2009
 * Time: 5:48:15 PM
 */

public class MapMessageSender {

    private final static Pattern TYPE_NAME_VALUE_PATTERN = Pattern.compile("([^,=]+)(?:,([^=]*))?=(.*)");

    private final static String DEFAULT_CONNECTION_FACTORY_NAME = "com.tibco.tibjms.TibjmsConnectionFactory";

    private String serverUrl = null;
    private String destinationName = null;
    private List<Field> fields = new LinkedList<Field>();
    private boolean useTopic = true;
    private String connectionFactoryName;


    public MapMessageSender(String[] args) {

        this.parseArgs(args);

        try {
            final String cfn = (null != this.connectionFactoryName)
                    ? this.connectionFactoryName : DEFAULT_CONNECTION_FACTORY_NAME;

            this.log("Connection factory  : " + cfn);
            final Constructor cfc = Class.forName(cfn).getConstructor(new Class[]{String.class});

            this.log("Publishing to server: " + ((null != this.serverUrl) ? this.serverUrl : "local server"));
            final ConnectionFactory factory = (ConnectionFactory) cfc.newInstance(this.serverUrl);
            final Connection connection = factory.createConnection();

            this.log("Publishing to " + (this.useTopic ? "topic : " : "queue : ") + this.destinationName);
            try {
                final Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
                final Destination destination = this.useTopic
                        ? session.createTopic(this.destinationName)
                        : session.createQueue(this.destinationName);

                final MessageProducer msgProducer = session.createProducer(null);
                final MapMessage msg = session.createMapMessage();

                final List<Field> builtFields = new LinkedList<Field>();
                int maxNameLength = 0;
                int maxTypeLength = 0;
                for (Field f : this.fields) {
                    builtFields.add(f);
                    switch (f.type) {
                        case BOOLEAN:
                            msg.setBoolean(f.name, Boolean.valueOf(f.value));
                            break;
                        case BYTE:
                            msg.setByte(f.name, Byte.valueOf(f.value));
                            break;
                        case CHAR:
                            msg.setChar(f.name, f.value.charAt(0));
                            break;
                        case DOUBLE:
                            msg.setDouble(f.name, Double.valueOf(f.value));
                            break;
                        case FLOAT:
                            msg.setFloat(f.name, Float.valueOf(f.value));
                            break;
                        case INTEGER:
                            msg.setInt(f.name, Integer.valueOf(f.value));
                            break;
                        case LONG:
                            msg.setLong(f.name, Long.valueOf(f.value));
                            break;
                        case OBJECT:
                            msg.setObject(f.name, f.value.getBytes());
                            break;
                        default:
                            logErr("Unhandled type => field will be sent as string: "
                                    + f.name + "," + f.type.toString().toLowerCase() + "=" + f.value);
                            builtFields.remove(builtFields.size() - 1);
                            builtFields.add(new Field(f.name, Type.STRING.toString(), f.value));
                        case STRING:
                            msg.setString(f.name, f.value);
                            break;
                    }
                    maxNameLength = Math.max(maxNameLength, f.name.length());
                    maxTypeLength = Math.max(maxTypeLength, f.type.toString().length());
                }

                this.log("Message built with " + builtFields.size()
                        + (builtFields.size() == 1 ? " field." : " fields."));
                for (Field f : builtFields) {
                    final StringBuffer sb = new StringBuffer("    ").append(f.name);
                    for (int i = f.name.length(); i <= maxNameLength; i++) {
                        sb.append(" ");
                    }
                    sb.append("  type: ").append(f.type.toString().toLowerCase());
                    for (int i = f.type.toString().length(); i <= maxTypeLength; i++) {
                        sb.append(" ");
                    }
                    sb.append("  value: ").append(f.value);

                    this.log(sb.toString());
                }

                msgProducer.send(destination, msg);
                this.log("Message sent.");
            } finally {
                connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    void parseArgs(String[] args) {

        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];

            if (arg.equals("-help")) {
                this.usage();

            } else if (arg.equals("-cf")) {
                this.connectionFactoryName = args[i + 1];
                i++;

            } else if (arg.equals("-queue")) {
                if ((i + 1) >= args.length) {
                    this.usage();
                }
                this.useTopic = false;
                this.destinationName = args[i + 1];
                i++;

            } else if (arg.equals("-server")) {
                if ((i + 1) >= args.length) {
                    this.usage();
                }
                this.serverUrl = args[i + 1];
                i++;

            } else if (arg.equals("-topic")) {
                if ((i + 1) >= args.length) {
                    this.usage();
                }
                this.destinationName = args[i + 1];
                i++;

            } else {
                final Matcher m = TYPE_NAME_VALUE_PATTERN.matcher(arg);
                if (!m.matches()) {
                    this.logErr("Could not parse field: " + arg);
                    this.usage();
                }
                try {
                    this.fields.add(new Field(m.group(1), m.group(2), m.group(3)));
                } catch (Exception e) {
                    this.logErr("Could not parse field: " + arg);
                    this.usage();
                }
            }
        }//for

        if ((null == this.destinationName) || (this.fields.size() < 1)) {
            this.usage();
        }
    }


    private void logErr(String text) {
        System.err.println(text);
    }


    private void log(String text) {
        System.out.println(text);
    }


    public static void main(String[] args) {
        new MapMessageSender(args);
    }


    private void usage
            () {
        this.logErr("");
        this.logErr("Usage: " + this.getClass().getName()
                + " [-server <server URL>]"
                + " [-cf <connection factory>]"
                + " -<queue or topic> <destination name>"
                + " ( <field name>[,<field type>]=<field value> )*");
        this.logErr("");
        this.logErr("");
        this.logErr("    <server URL>         - EMS server URL, default is local server.");
        this.logErr("    <connection factory> - Connection factory class name. Default="
                + DEFAULT_CONNECTION_FACTORY_NAME);
        this.logErr("    <queue or topic>     - Either the word queue or the word topic.");
        this.logErr("    <destination name>   - Name of the queue or topic.");
        this.logErr("    <field name>         - Name of a field.");
        this.logErr("    <field type>         - Type of the field. Optional, default=string. Can be one of: ");
        this.logErr("                           boolean, byte, char, double, float, int, integer, long, object, string.");
        this.logErr("    <field value>        - Value of the field.");
        this.logErr("");
        System.exit(0);
    }


    private static class Field {
        final Type type;
        final String name;
        final String value;


        private Field(String name, String typeName, String value) {
            if ((null == typeName) || typeName.isEmpty()) {
                type = Type.STRING;
            } else {
                this.type = Type.fromName(typeName);
                if (null == this.type) {
                    throw new IllegalArgumentException("Unknown type: " + typeName);
                }
            }
            this.name = name;
            this.value = value;
        }
    }


    public static enum Type {
        BOOLEAN,
        BYTE,
        CHAR,
        DOUBLE,
        FLOAT,
        INTEGER,
        LONG,
        OBJECT,
        STRING;

        private static final Map<String, Type> NAME_TO_TYPE = new HashMap<String, Type>();

        static {
            NAME_TO_TYPE.put("boolean", BOOLEAN);
            NAME_TO_TYPE.put("byte", BYTE);
            NAME_TO_TYPE.put("char", CHAR);
            NAME_TO_TYPE.put("double", DOUBLE);
            NAME_TO_TYPE.put("f32", FLOAT);
            NAME_TO_TYPE.put("f64", DOUBLE);
            NAME_TO_TYPE.put("float", FLOAT);
            NAME_TO_TYPE.put("i8", BYTE);
            NAME_TO_TYPE.put("i16", INTEGER);
            NAME_TO_TYPE.put("i32", INTEGER);
            NAME_TO_TYPE.put("i64", LONG);
            NAME_TO_TYPE.put("int", INTEGER);
            NAME_TO_TYPE.put("integer", INTEGER);
            NAME_TO_TYPE.put("long", LONG);
            NAME_TO_TYPE.put("object", OBJECT);
            NAME_TO_TYPE.put("string", STRING);
            NAME_TO_TYPE.put("u8", BYTE);
            NAME_TO_TYPE.put("u16", INTEGER);
            NAME_TO_TYPE.put("u32", INTEGER);
            NAME_TO_TYPE.put("u64", LONG);
        }

        public static Type fromName(String s) {
            if (null == s) {
                return null;
            }
            return NAME_TO_TYPE.get(s.toLowerCase());
        }
    }

}

