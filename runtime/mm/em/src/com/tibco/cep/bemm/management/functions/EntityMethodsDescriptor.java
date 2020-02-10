package com.tibco.cep.bemm.management.functions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.functions.java.util.MapHelper;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 27, 2009
 * Time: 9:13:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityMethodsDescriptor {

    private static enum AGENT_TYPE {INFERENCE, CACHE, QUERY, PROCESS};
    private static String[] xmlFileLocation = {"/IA_MethodsDescriptor.xml",
                                               "/CS_MethodsDescriptor.xml",
                                               "/QA_MethodsDescriptor.xml",
                                               "/Process_MethodsDescriptor.xml"};

    private static EntityMethodsDescriptor instance = new EntityMethodsDescriptor();
    private static final String TOKEN = "#";

    //This class uses a Map of Maps. The key of the first map is the entity type; one of inference, cache, query, or process
    //The key of the second map is the Method_Qualified_Name, which is of the form GROUP#METHOD_NAME.
    //These keys are used to account for the possibility that in the future different groups might have methods with the
    // same name, or the functionality of a given method might be different for different agent types (process, inference, query, ...)

    public static synchronized EntityMethodsDescriptor getInstance() {      //todo: check this
        if (instance == null){
            instance = new EntityMethodsDescriptor();
        }
        return instance;
    }

    //exposed as catalog function via MethodsHelper
    public static String getMethodDescriptor(String entityType, String methodGroup, String methodName) {
        //put everything in lowercase for consistency
        entityType = entityType.toLowerCase();
        methodGroup = methodGroup.toLowerCase();
        methodName = methodName.toLowerCase();
                                                                         //todo: put logging info
        try {
            String methodQualifiedName = getMethodQualifiedName(methodGroup, methodName);

            //create a Map of Maps. The key of the first Map is the entityType. The key of the second Map is the
            //Method_Qualified_Name, which is of the form GROUP#METHOD_NAME. The first time a request
            //for the method descriptor of a method belonging to a certain entityType is made, all of the methods of that
            //entity type are put in the Map
            //If the map already exists, no action is taken (feature of the MapHelper class)
            MapHelper.createMap(entityType);

            //if this entityType entry is not existent in the Map, put in the Map all the method descriptors for this entityType
            if (MapHelper.getObject(entityType,methodQualifiedName) == null)
                getInstance().putMethodQualNameToDescriptor(entityType);      //todo handle exceptions and remove getInstance

            //return desired entry;
            MethodDescriptor methodDescriptor = ((MethodDescriptor)(MapHelper.getObject(entityType,methodQualifiedName)));
            return methodDescriptor.getXmlMethodDescriptor();
        }
        catch (Exception e) {
//            logger.logError("For agent type: " + entityType + " no method descriptor found for method: " + methodGroup + "." + methodName, e);
            e.printStackTrace();
//            throw new RuntimeException(e);    //todo: Handle Exceptions 
            return null;
        }
    } //getMethodDescriptor

    private static String getMethodQualifiedName(String methodGroup, String methodName) {
        return methodGroup.toLowerCase() + TOKEN +  methodName.toLowerCase();
    }

    //this method is called just once per entityType. It is used to create the HashMap with the Methods description
    //extracted from the XML file and parsed into the particular XML string format defined in the method
    private void putMethodQualNameToDescriptor(String entityType) {
        AGENT_TYPE agent_type = null;
        InputStream fileStream =null;
        byte i = -1;     //XML file index

        try {
            agent_type = AGENT_TYPE.valueOf(entityType.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("No methods descriptor available for agent: " + agent_type ,e);
        }

        switch (agent_type) {
            case INFERENCE :
                fileStream = instance.getClass().getResourceAsStream(xmlFileLocation[0]);
                i = 0;
                break;
            case CACHE:
                fileStream = instance.getClass().getResourceAsStream(xmlFileLocation[1]);
                i = 1;
                break;
            case QUERY:
                fileStream = instance.getClass().getResourceAsStream(xmlFileLocation[2]);
                i = 2;
                break;
            case PROCESS:
                fileStream = instance.getClass().getResourceAsStream(xmlFileLocation[3]);
                i = 3;
                break;
        }

        if (fileStream == null) {
//            logger.logError("Could not find method description file for method XXXXX"); //todo
            throw new RuntimeException("Could not find method description file for entity of type: " + entityType);
        }

        parseXMLandPutMap(fileStream, i);

    }//putMethodQualNameToDescriptor
                                                            //TODO: Add ObjectName to the MAP
    private void parseXMLandPutMap(InputStream is, byte fileIndex) {
        String entityType, methodGroup, methodName, methodAnnotation=null;
        ArrayList<String> argNames = new ArrayList<String>();
        ArrayList<String> argTypes = new ArrayList<String>();
        ArrayList<String> argDescriptions = new ArrayList<String>();
        ArrayList<String> argDefaultValues = new ArrayList<String>();
        ArrayList<String> argIsRequired = new ArrayList<String>();

        try {
            //create an instance of the parser
            XiParser parser = XiParserFactory.newInstance();
            // open the xml file
            final XiNode doc = parser.parse(new InputSource(is));
            XiNode root = doc.getFirstChild();             //root represents the 'root' node
            entityType = root.getAttributeStringValue(ExpandedName.makeName("type"));
            entityType = entityType.toLowerCase();

            //methods node(s)
            Iterator methodsIter = XiChild.getIterator(root);
            while (methodsIter.hasNext()) {
                XiNode methodsNode = (XiNode)methodsIter.next();
                //methodgroup nodes
                Iterator methodgroupIter = XiChild.getIterator(methodsNode);
                while (methodgroupIter.hasNext()) {
                    XiNode methodgroupNode = (XiNode) methodgroupIter.next();
                    methodGroup = methodgroupNode.getAttributeStringValue(ExpandedName.makeName("group"));
    //                Iterator methodIterator = XiChild.getIterator(methodGroupNode, ExpandedName.makeName("method"));

                    //method nodes
                    Iterator methodIterator = XiChild.getIterator(methodgroupNode);
                    while (methodIterator.hasNext()) {
                        XiNode methodNode = (XiNode) methodIterator.next();
                        methodName = methodNode.getAttributeStringValue(ExpandedName.makeName("name"));

                        //retrive annotation and args information
                        //In the first iteration the element retrieved is Annotation. In the subsequent iterations the args are retrieved
                        //Need the isAnnotation boolean flag to differentiate the cases when the arg element is empty
                        Iterator methodDetailsIterator = XiChild.getIterator(methodNode);
                        boolean isAnnotation = true;
                        while (methodDetailsIterator.hasNext()) {
                            XiNode methodDetailsNode = (XiNode) methodDetailsIterator.next();
                                //it is annotation node
                            if (! methodDetailsNode.hasAttributes() && isAnnotation) {
                                methodAnnotation = methodDetailsNode.getItem(0).getTypedValue().toString();
                                isAnnotation = false;
                            }
                            else { //it is arg node
                                if (methodDetailsNode.hasAttributes()) {
                                    argNames.add(methodDetailsNode.getAttributeStringValue(ExpandedName.makeName("name")));
                                    argTypes.add(getJavaType(methodDetailsNode.getAttributeStringValue(ExpandedName.makeName("type"))));
                                    argDescriptions.add(methodDetailsNode.getAttributeStringValue(ExpandedName.makeName("desc")));
                                    argDefaultValues.add(methodDetailsNode.getAttributeStringValue(ExpandedName.makeName("defaultvalue")));
                                    argIsRequired.add(methodDetailsNode.getAttributeStringValue(ExpandedName.makeName("required")));
                                }
                                else { //arg node is empty
                                    /*argNames.add("");
                                    argTypes.add("");
                                    argDescriptions.add("");
                                    argDefaultValues.add("");*/
                                }
                            }
                        }//while

                        MethodDescriptor methodDescriptor = new MethodDescriptor(entityType, methodGroup,
                                                            methodName,methodAnnotation );

                        //one needs to add the entries of the ArrayList one by one in the wrapper object MethodsDescription.
                        for (int i=0; i<argNames.size(); i++) {
                            methodDescriptor.setArgName(argNames.get(i));
                            methodDescriptor.setArgType(argTypes.get(i));
                            methodDescriptor.setArgDescription(argDescriptions.get(i));
                            methodDescriptor.setArgDefaultValue(argDefaultValues.get(i));
                            methodDescriptor.setArgIsRequired(argIsRequired.get(i));
                        }

                        //build XML string and update this object's XmlMethodDescriptor field
                        methodDescriptor.setXmlMethodDescriptor(buildXMLString(methodDescriptor));

                        //put methodDescriptor object in the Map
                        MapHelper.putObject(entityType.toLowerCase(),
                                    getMethodQualifiedName(methodGroup, methodName), methodDescriptor);

                        //reset variables
                        methodAnnotation="";
                        //clear ArrayLists before processing next method
                        argNames.clear();
                        argTypes.clear();
                        argDescriptions.clear();
                        argDefaultValues.clear();
                        argIsRequired.clear();
                    }//while
                }//while
            }//while

        }//try
        catch (FileNotFoundException ignore) {
            ignore.printStackTrace();
            throw new RuntimeException(ignore);
        }
        catch (SAXException e) {
            throw new RuntimeException("could not parse "+xmlFileLocation[fileIndex],e);
        }
        catch (IOException e) {
            throw new RuntimeException("could not load "+xmlFileLocation[fileIndex],e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    } //parseXMLandPutMap

    //convert the String with the type description parsed from the XML file, to a String with the corresponding Java type
    private String getJavaType(String type) {
        type = type.toLowerCase();

        if(type.equals("string"))
            type = String.class.getName();
        else if (type.equals("boolean"))
            type = Boolean.TYPE.getName();
        else if (type.equals("integer") || type.equals("int"))
            type = Integer.TYPE.getName();
        else if (type.equals("char"))
            type = Character.TYPE.getName();
        else if (type.equals("long"))
            type = Long.TYPE.getName();
        else if (type.equals("short"))
            type = Short.TYPE.getName();
        else if (type.equals("byte"))
            type = Byte.TYPE.getName();
        else if (type.equals("double"))
            type = Double.TYPE.getName();
        else if (type.equals("float"))
            type = Float.TYPE.getName();
        else if (type.equals("void"))
            type = Void.TYPE.getName();
//        else //non primitive type or String so leave it unchanged
//            logger.logWarn("Non primitive Java type or String found defined as an argument type");  todo uncomment this

        return type;
    } //getJavaType


    /** Builds the XML string with the method description and arguments information. The format of this XML string
     *  is gathered from the information in the file XX_methodDescriptor.xml but is presented in a slightly different
     *  way to make it more meaningful to the UI (BEMM) */
    private static String buildXMLString(MethodDescriptor methodDescriptor) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<panel>").
                          append("<page name=\"Function Invocation\">").
                          append("<methodpanelconfig>");

            String methodGroup = "<group>{0}</group>";
            String methodName = "<name>{0}</name>";
            String methodAnnotation = "<description>{0}</description>";
            String methodArg = "<arg name=\"{0}\" type=\"{1}\" desc=\"{2}\" defaultvalue=\"{3}\" required=\"{4}\"/>";

            stringBuilder.append(MessageFormat.format(methodGroup,methodDescriptor.methodGroup)).
                    append(MessageFormat.format(methodName,methodDescriptor.methodName)).
                    append(MessageFormat.format(methodAnnotation,methodDescriptor.methodAnnotation)).
                    append("<arguments>");

            for(int i=0; i< methodDescriptor.argNames.size(); i++) {
                stringBuilder.append( MessageFormat.format(methodArg, methodDescriptor.argNames.get(i), methodDescriptor.argTypes.get(i),
                        methodDescriptor.argDescriptions.get(i), methodDescriptor.argDefaultValues.get(i),
                        methodDescriptor.argIsRequired.get(i)) );
            }
            stringBuilder.append("</arguments>").
                          append("</methodpanelconfig>").
                          append("</page>").
                          append("</panel>");

            return stringBuilder.toString();
        } //createXMLString

    //Class wrapping all the descriptive information of the methods
    public class MethodDescriptor {
        private String agentType;
        private String methodGroup;
        private String methodName;
        private String methodAnnotation;
        //arguments
        private ArrayList<String> argNames;
        private ArrayList<String> argTypes;
        private ArrayList<String> argDescriptions;
        private ArrayList<String> argDefaultValues;
        private ArrayList<String> argIsRequired;

        private String xmlMethodDescriptor;
        private String objectName;                  //todo

        //setter methods
        //entries in the ArrayLists need to be added one by one.
        public void setArgName(String argName) { this.argNames.add(argName); }
        public void setArgType(String argType) { this.argTypes.add(argType); }
        public void setArgDescription(String argDescription) { this.argDescriptions.add(argDescription); }
        public void setArgDefaultValue(String argDefaultValue) { this.argDefaultValues.add(argDefaultValue); }
        public void setArgIsRequired(String argIsRequired) { this.argIsRequired.add(argIsRequired); }
        public void setXmlMethodDescriptor(String xmlMD) { this.xmlMethodDescriptor = xmlMD; }

        //getter methods
        public String getAgentType() { return agentType; }
        public String getMethodGroup() { return methodGroup; }
        public String getMethodName() { return methodName; }
        public String getMethodAnnotation() { return methodAnnotation; }
        public ArrayList<String> getArgNames() { return argNames; }
        public ArrayList<String> getArgTypes() { return argTypes; }
        public ArrayList<String> getArgDescriptions() { return argDescriptions; }
        public ArrayList<String> getArgDefaultValues() { return argDefaultValues; }
        public ArrayList<String> getArgIsRequired() { return argIsRequired; }
        public String getXmlMethodDescriptor() { return xmlMethodDescriptor; }

        public MethodDescriptor( String agentType, String methodGroup, String methodName, String methodAnnotation ) {
            this.agentType = agentType;
            this.methodGroup = methodGroup;
            this.methodName = methodName;
            this.methodAnnotation = methodAnnotation;
            this.argNames = new ArrayList<String>();
            this.argTypes = new ArrayList<String>();
            this.argDescriptions = new ArrayList<String>();
            this.argDefaultValues = new ArrayList<String>();
            this.argIsRequired = new ArrayList<String>();
            this.xmlMethodDescriptor = null;
        }

    } //class MethodDescriptor

//    // ***************** test method ******************
//    public static void main (String[] args) {
//        System.err.println("Invoking method...");
//        System.err.println( EntityMethodsDescriptor.getMethodDescriptor("inference", "Agent", "getNumberOfEvents") );
//        System.err.println( EntityMethodsDescriptor.getMethodDescriptor("process", "proFilER", "STARTFileBasedProfileR") );
//        
//        System.err.println( EntityMethodsDescriptor.getMethodDescriptor("CacHE", "proFilER", "StarTFileBasedProfileR") );
//        System.err.println( EntityMethodsDescriptor.getMethodDescriptor("inference", "agENt", "RESUME") );
//        System.err.println("Method Invoked...");
//    }

} //class
