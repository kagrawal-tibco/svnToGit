package com.tibco.rta.model.command;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Stack;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ID_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PARENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_MEASUREMENT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/10/12
 * Time: 4:51 PM
 * Create instance of this class when the basic admin console is started using command like
 * <p>
 *     <b>java -jar spm-admin.jar</b>
 * </p>
 * <p>
 *     Per connect, create instance of {@link CommandProcessor} which in turn will
 *     create an {@link com.tibco.rta.RtaSession}
 * </p>
 */
public class CommandParser extends DefaultHandler2 {

    /**
     * Stack per command
     */
    private Stack<CommandObject> commandObjectStack = new Stack<CommandObject>();

    /**
     * Process individual command using this
     */
    private CommandProcessor commandProcessor = null;

    /**
     * This may change as and when a command is issued.
     */
    private CommandActions commandAction;

    public void parseCommandsFile(File file) throws Exception {
        //Set up SAX parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //factory.setValidating(true);
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();
        parser.parse(file, this);
    }


    /**
     * Receive notification of the end of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each element (such as finalising a tree node or writing
     * output to a file).</p>
     *
     * @param uri       The Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed.
     * @param localName The local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed.
     * @param qName     The qualified name (with prefix), or the
     *                  empty string if qualified names are not available.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#endElement
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("command".equals(localName)) {
            //Process command queue here
            if (commandProcessor != null) {
                try {
                    commandProcessor.processCommandStack(commandAction, commandObjectStack);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                //Connection not made yet throw exception
                //TODO create a checked exception
                throw new RuntimeException("No session created. Please make a connection first");
            }
            commandAction = null;
        }
    }

    /**
     * Receive notification of the start of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each element (such as allocating a new tree node or writing
     * output to a file).</p>
     *
     * @param uri        The Namespace URI, or the empty string if the
     *                   element has no Namespace URI or if Namespace
     *                   processing is not being performed.
     * @param localName  The local name (without prefix), or the
     *                   empty string if Namespace processing is not being
     *                   performed.
     * @param qName      The qualified name (with prefix), or the
     *                   empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *                   there are no attributes, it shall be an empty
     *                   Attributes object.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#startElement
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("command".equals(localName)) {
            //Get its type
            String commandActionString = attributes.getValue("action");
            commandAction = CommandActions.getByLiteral(commandActionString);
            if (commandAction == CommandActions.CONNECT) {
                //Create a new processor here per connect.
                commandProcessor = new CommandProcessor();
            }
        } else {
            CommandType commandType = CommandType.getByLiteral(localName);
            String name = attributes.getValue("", ATTR_NAME_NAME);
            CommandObject commandObject = null;
            if (ELEM_SCHEMA_NAME.equals(localName) || ELEM_CUBE_NAME.equals(localName)) {
                //Add to processing queue
//                commandObject = new CommandObject(commandType);
            } else if (ELEM_HIERARCHY_NAME.equals(localName)) {
                String aggregator = null;//attributes.getValue("", ATTR_AGGREGATOR_NAME);
                //Add to processing queue
//                commandObject = new CommandObject(commandType);
                if (aggregator != null && !aggregator.isEmpty()) {
//                    commandObject.addAttribute(ATTR_AGGREGATOR_NAME, aggregator);
                }
            } else if (ELEM_DIMENSION_NAME.equals(localName)) {
                String id = attributes.getValue("", ATTR_ID_NAME);
                String parent = attributes.getValue("", ATTR_PARENT_NAME);
                //Add to processing queue
//                commandObject = new CommandObject(commandType);
                commandObject.addAttribute(ATTR_ID_NAME, id);
                if (parent != null && !parent.isEmpty()) {
                    commandObject.addAttribute(ATTR_PARENT_NAME, parent);
                }
            } else if (ELEM_MEASUREMENT_NAME.equals(localName)) {
                String id = attributes.getValue("", ATTR_ID_NAME);
                //Add to processing queue
//                commandObject = new CommandObject(commandType);
                commandObject.addAttribute(ATTR_ID_NAME, id);
            }
            if (commandObject != null) {
                commandObject.addAttribute(ATTR_NAME_NAME, name);
                commandObjectStack.push(commandObject);
            }
        }
    }

    public static void main(String[] r) throws Exception {
        File file = new File("/media/Windows7/dev/tmp/Commands.xml");
        CommandParser commandParser = new CommandParser();
        commandParser.parseCommandsFile(file);
        commandParser.commandAction = null;
    }
}
