package com.tibco.be.migration.expimp.providers.bdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.tibco.be.dbutils.DbUtils;
import com.tibco.be.dbutils.PropertyTypes;
import com.tibco.be.dbutils.StateMachineHelper;
import com.tibco.be.migration.CSVWriter;
import com.tibco.be.migration.CSVWriterImpl;
import com.tibco.be.migration.expimp.Base64;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.WorkerThreadPool;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 20, 2008
 * Time: 12:32:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BDBExporter_v2_0 {

    public static final TypeConverter xsd2java_dt_conv = XSDTypeRegistry.getInstance().nativeToForeign(XsDateTime.class, GregorianCalendar.class);
    public static final TypeRenderer java2xsd_dt_conv= XSDTypeRegistry.getInstance().foreignToNative(XsDateTime.class, GregorianCalendar.class);    
    public static final String BE_VERSION_RECORD="BEVersionRecord"; // From ObjectManager
    public static final String BE_STATE_TIMEOUT_EVENT_CSV_FILE_NAME="StateTimeoutEvent"; // For StateTimeoutEvent
    private static final int NUMBER_OF_THREADS = 3;
    private static final int NUMBER_OF_JOBS = 3;

    ExpImpContext context;
    ExpImpStats stats;
    Properties prop;
    Environment dbenv;
    WorkerThreadPool exporterThreadPool;

    private HashMap propIndexToPropInfo = new HashMap();
    private HashMap classNames = new HashMap();
    private Set scoreCardSet = new HashSet();
    private HashMap csvCeptWriters = new HashMap();
    private HashMap csvEvtWriters = new HashMap();
    private String outputPath;

    public void init(ExpImpContext context, ExpImpStats stats) throws Exception {
    	this.context = context;
        this.stats = stats;

        prop = context.getEnvironmentProperties();
        File dbdir = new File(context.getInputUrl());
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(false);
        envConfig.setTransactional(true);
        dbenv = new Environment(dbdir, envConfig);
        outputPath = context.getOutputUrl();

        // alocate multiple threads for dumping the data
        this.exporterThreadPool = new WorkerThreadPool(NUMBER_OF_THREADS, NUMBER_OF_JOBS, context.getRuleServiceProvider());
      }

    public void destroy() {
    	try {
            dbenv.close();
        }
        catch (Exception e) { }
    }

   /**
    * propIndexToPropInfo hashmap should be built before exporting concepts and concept properties as they need to use propIndexToPropInfo.
    * However, it can run simultaneously with exporting events as the latter does not use it
    * And exporting concepts and run the same time as exporting concept properties.
    * @throws Exception
    */
    public void exportAll() throws Exception {
        // Version check
        String projectVersion = context.getComponentVersion();
        String dataVersion = getBdbVersion();
        this.context.getLogger().log(Level.INFO, "Project name: " + context.getProjectName() + ", Project version: BE " + context.getComponentVersion()
                                        + ", data version: BE " + dataVersion);

        if (!projectVersion.substring(0, 2).equals(dataVersion.substring(0, 2))) {
            this.context.getLogger().log(Level.ERROR, "Can not use BE " + projectVersion + " project to export data generated by BE "
                                        + dataVersion + " project! exit.");
            return;
        }

       // Create CSV files for all concetps and concept properties
        createConceptfiles();

        try {
            exporterThreadPool.execute(new ExportPropIndexToPropInfo());
            exporterThreadPool.execute(new ExportEvents());
            exporterThreadPool.execute(new ExportConcepts());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        // Wait for building propIndexToPropInfo and className HashMap are done as they are needed by exportConceptProperties
        exporterThreadPool.join();

       // Now exporting concept properties
        exportConceptProperties();

        // Clean up CSVCept-Writer
        cleanupCSVCeptWriter();

        writeDummyScorecardFile();
        writeControlFile(context, dataVersion);
       
        int numErr = stats.getErrorCount();
        int numWarn = stats.getWarningCount();
        if(numErr == 0 && numWarn == 0)
            this.context.getLogger().log(Level.INFO, "Exported all CSV files successfully.");
        else {
            this.context.getLogger().log(Level.INFO, "Exported all CSV files with " + numErr + " errors and " + numWarn
                                        + " warnings. Please check following lines for details.");
            Iterator errors = stats.getErrors();
            while(errors.hasNext())
            	this.context.getLogger().log(Level.ERROR, (String)errors.next());
            
            Iterator warnings = stats.getWarnings();
            while(warnings.hasNext())
            	this.context.getLogger().log(Level.ERROR, (String)warnings.next());
        }
    }

    protected void exportPropIndexToPropInfo() throws Exception {
        this.context.getLogger().log(Level.INFO, "Exporting property index file...");
    	DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database propertyindex = dbenv.openDatabase(null, prop.getProperty("be.engine.om.berkeleydb.propertyindextable"), null);
        Cursor cursor = propertyindex.openCursor(null, null);

        CSVWriter piWriter = new CSVWriterImpl(outputPath, "propertiesIndex");
        piWriter.writeCommentln("conceptName,propertyName,propertyIndex");

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            writePropIndexRow(keyis, datais, piWriter);
        }
        this.context.getLogger().log(Level.INFO, "Exported property index file.");
        piWriter.close();
        cursor.close();
        propertyindex.close();
    }

    private void writePropIndexRow(DataInputStream keyis, DataInputStream datais, CSVWriter writer) throws Exception {
    	String subjectName, propName;
        String propClassName=keyis.readUTF();
        int index=datais.readInt();
        PropertyDefinition pd = null;
        int historySize = 0;
        byte propTypeIndex = 0;

        if(propClassName.equals("$MarkerRecord$")) { // What is "$MarkerRecord$" for and what is the use here?
            subjectName = propName = "$MarkerRecord$";
        } else {
            String[] contents = DbUtils.EntityPathfromClass(propClassName).split("\\$\\$");
            subjectName = contents[0];
            propName = contents[1].substring(2); // Get rid of the 1z also.

            boolean isSMConcept = false;
            Concept c = context.getProject().getOntology().getConcept(subjectName);
            if(c == null) {
            	//TODO: ??? What the StateMachineHelper.isStateMachineConcept() does is to return ontology.getPropertyDefinition(subjectName, false) == null
            	//          which is the same as context.getProject().getOntology().getConcept(subjectName) == null
            	// Need to find another way to confirm c is indeed a StateMachineConcept
            	if(StateMachineHelper.isStateMachineConcept(subjectName, context.getProject().getOntology())){ // it'll always evaluates to be true
                    isSMConcept = true;
                    //context.getLogger().logDebug("StateMachine: " + subjectName);
                } else { // should never get here even when c==null
	                stats.addErrorString("Unknown Concept: " + subjectName);
	                return;
                }
            }
            if(!isSMConcept) { // c != null, is a concept
                pd = c.getPropertyDefinition(propName, false);
                if(pd == null) {
                	//TODO: Same concern as above. Need to find another way to confirm pd is indeed a StateMachineConceptProperty
                	if(StateMachineHelper.isStateMachineConceptProperty(c, propName)) {
                        //context.getLogger().logDebug("pd is null. propertyTypes_atomContainedConcept: " + propName + " of " + subjectName);
                        propTypeIndex = PropertyTypes.propertyTypes_atomContainedConcept;
                    } else { // will never get here even when pd==null
                		stats.addErrorString("Unknown Property: " + propName + " of Concept: " + subjectName);
                    	return;
                    }
                }
                else {
                	historySize = pd.getHistorySize();
                	propTypeIndex = PropertyTypes.RDFToPropType(pd, context.getLogger());
                    //context.getLogger().logDebug("pd is not null. propertyTypes: property of concept: " + propName + " of " + subjectName);
                }
            } else { //the concept is a SM concept, so the property must be a state property
                propTypeIndex = PropertyTypes.propertyTypes_atomInt;
                //context.getLogger().logDebug("pd is null. propertyTypes: property of stateMachine: " + propName + " of " + subjectName);
            }
        }
        writer.write(subjectName).write(propName).write(index);
        writer.writeln();
        
        PropertyInfo pi = new PropertyInfo();
        pi.propRdfIndex = index;
        pi.subjectName = subjectName;
        pi.propName = propName;
        pi.pd = pd;
        pi.historySize = historySize;
        pi.propTypeIndex = propTypeIndex;
        propIndexToPropInfo.put(new Integer(index), pi);
    }

    protected void createConceptfiles() throws Exception {

        // Creates files for concept and its property for each concept type in the project, regardless the existence of its data in BDB
        CSVWriter writers[] = null;
        String cptName =  null;
        String smName = null;
        String smPath = null;
        String encoding = prop.getProperty("be.encoding");//TODO - Find the right encoding key
        TypeManager typeManager = context.getRuleServiceProvider().getTypeManager();
        Collection concepts = context.getProject().getOntology().getConcepts();
        int count = 0;

            this.context.getLogger().log(Level.INFO, "Creating concept/statemachine and properties files...");

        for(Iterator it = concepts.iterator(); it.hasNext();) {
            Concept c = (Concept) it.next();
            //context.getLogger().logDebug("concept fullPath is: " + c.getFullPath());
            //cptName = ModelNameUtil.modelPathToGeneratedClassName(c.getFullPath());
            cptName = typeManager.getTypeDescriptor(c.getFullPath()).getImplClass().getName();
            this.context.getLogger().log(Level.DEBUG, "Creating concept and concept-properties files for: " + cptName);

            writers = new CSVWriter[2];
            writers[0] = new CSVWriterImpl(outputPath, cptName);
            writers[0].writeCommentln("id,extId,status,timestamp,retractedFlag");

            writers[1] = new CSVWriterImpl(outputPath , cptName + "-properties");
            writers[1].writeCommentln("conceptId,propertyName,type,isSet,arrayIndex,value,historysize,currentIndex,[{HistoryTS,HistoryValue}...]");
            csvCeptWriters.put(c.getFullPath(), writers);
            count = count + 2;

            List allMachines = c.getStateMachines();
            for (int i = 0; null != allMachines && i < allMachines.size(); i++) {
                StateMachine sm = (StateMachine) allMachines.get(i);
                //smName = ModelNameUtil.modelNametoStateMachineClassName(c,sm);
                ExpandedName uri = ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + sm.getOwnerConcept().getFullPath() + "/" + sm.getName(), sm.getName());
                smName = typeManager.getTypeDescriptor(uri).getImplClass().getName();
                this.context.getLogger().log(Level.DEBUG, "Creating statemachine and statemachine-properties files for: " + smName);
                smPath = smName.substring((context.getRuleServiceProvider().getProperties().getProperty("be.codegen.rootPackage", "be.gen")).length(), smName.length());
                smPath = smPath.replace('.', '/');
                //this.context.getLogger().log(Level.DEBUG, "smPath = " + smPath);
                
                writers = new CSVWriter[2];
                writers[0] = new CSVWriterImpl(outputPath, smName);
                writers[0].writeCommentln("id,extId,status,timestamp,retractedFlag");

                writers[1] = new CSVWriterImpl(outputPath , smName + "-properties");
                // make the statemachine property the same 8 columns as required by the importer
                writers[1].writeCommentln("conceptId,propertyName,type,isSet,arrayIndex,value,historysize,currentIndex");
                csvCeptWriters.put(smPath, writers);
                count = count + 2;
            }
        }
        this.context.getLogger().log(Level.INFO, "Created " + count + " concept/statemachine and properties files.");
    }

    protected void exportConcepts() throws Exception {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database conceptDb = dbenv.openDatabase(null, prop.getProperty("be.engine.om.berkeleydb.conceptable"), null);
        Cursor cursor = conceptDb.openCursor(null, null);
        CSVWriter writers[] = null;
        String cPath = null;

        this.context.getLogger().log(Level.INFO, "Exporting " + conceptDb.count() + " concepts...");

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            EntityHeader header = new EntityHeader(datais);

            this.context.getLogger().log(Level.DEBUG, "Exporting concept: " + header.className + ", id: " + header.id);
            cPath = ModelNameUtil.generatedClassNameToModelPath(header.className);
            //this.context.getLogger().log(Level.INFO, "cPath = " + cPath);
            writers = (CSVWriter[]) csvCeptWriters.get(cPath);
            if (writers == null) {// this should not happen unless there are some changes in the project that runs with migration.
                	stats.addErrorString("Concept: " + header.className + " is not in the project, ignored.");
                	continue;
            }

            classNames.put(new Long(header.id), cPath);
            header.writeHeaderOnly((CSVWriter)writers[0]);
            stats.incrementInstances(); //TODO: do we need to separate the counters for concept and statemachines?
        }
        //writeConceptProperties();

        cursor.close();
        conceptDb.close();
        
        this.context.getLogger().log(Level.INFO, "Exported " + stats.getInstanceCount() + " concepts.");
    }

    protected void exportConceptProperties() throws Exception{

    	DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database propertiesDb = dbenv.openDatabase(null, prop.getProperty("be.engine.om.berkeleydb.propertiestable"), null);
        Cursor cursor = propertiesDb.openCursor(null, null);

        this.context.getLogger().log(Level.INFO, "Exporting " + propertiesDb.count() + " concept properties...");

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            writePropertyRow(keyis, datais);
        }
        this.context.getLogger().log(Level.INFO, "Finished exporting concept properties.");
    }

    private void writePropertyRow(DataInputStream keyis, DataInputStream datais) throws Exception{

        long subjectId = keyis.readLong();   //subject id is the concept id
        int propIndex = keyis.readInt();   //propIndex
        PropertyInfo pi = (PropertyInfo)propIndexToPropInfo.get(new Integer(propIndex));
        if(pi == null) {
        	stats.addErrorString("Can not find PropertyInfo for propIndex: " + propIndex + " of concept: " + pi.subjectName + ", internalId: " + subjectId);
        	return;
        }
        this.context.getLogger().log(Level.DEBUG, pi.propName + " of " + classNames.get(new Long(subjectId)));

        CSVWriter writers[] = null;
    	writers = (CSVWriter[]) csvCeptWriters.get(classNames.get(new Long(subjectId)));
    	if(writers == null || (writers != null && writers[1] == null)) {
            //this.context.getLogger().log(Level.DEBUG, "classPath is:" + classNames.get(new Long(subjectId)));
            stats.addErrorString("Can not find concept property: " + pi.propName+ " of concept: " + pi.subjectName + ", internalId: " + subjectId);
    		return;
    	}

        if (pi.pd != null && pi.pd.isArray()) {
            handlePropertyArray(subjectId, pi, datais, writers[1]);
        }
        else {
            handlePropertyAtom(subjectId, pi, datais, -1, writers[1]);
        }
    }

    private void handlePropertyArray(long subjectId, PropertyInfo pi, DataInputStream is, CSVWriter cw) throws Exception  {

        int arraySize = is.readInt();

        if(arraySize == 0) { // Special case for any 0 sized arrays that might exist in the db.
            // Write a single row with -1 as array index indicating this was a 0 sized array
            cw.write(subjectId).write(pi.propName).write(pi.propTypeIndex);
            if(pi.historySize == 0) // Handle 0 history case differently
                cw.write(false).write(-1).write(null).write(0).write(0); // isSet = false
            else {
                cw.write(false).write(-1).write(null).write(pi.historySize).write(0); //
                for(int i=0; i < pi.historySize; i++)
                    cw.write(0).write(null); //HistoryTS = 0, HistoryValue = null
            }
        	cw.writeln();
            return;
        }

        for(int i=0; i < arraySize; i++) {
            handlePropertyAtom(subjectId, pi, is, i, cw);
        }
    }

    private void handlePropertyAtom(long subjectId, PropertyInfo pi, DataInputStream is, int arrayIndex, CSVWriter cw) throws Exception {

        int historySize = 0;
        short currIndex = 0;
        long[] timeArray = null;
        String[] valueArray = null;
        String value = null;

        cw.write(subjectId).write(pi.propName).write(pi.propTypeIndex).write(is.readBoolean()); //isSet = is.readBoolean()
        if(pi.historySize != 0) { // Handle 0 history case differently
            historySize = is.readInt(); //history Size, should be equal to model size, but anyway it is written
            currIndex = is.readShort(); // History Index
            timeArray = new long[historySize];
            for (int i = 0; i < historySize; i++) {
                timeArray[i] = is.readLong(); // read all the time values;
            }

            valueArray = new String[historySize];
            for (int i = 0; i < historySize; i++) {
                valueArray[i] = readValue(is, pi.propTypeIndex);
            }
            value = valueArray[currIndex];
            
            cw.write(arrayIndex); //arrayIndex
            cw.write(value); //currentValue
            cw.write(historySize); //historySize
            cw.write(currIndex); //currIndex
            for (int i=0; i < historySize; i++) {
                cw.write(timeArray[i]); //tiemStamp
                cw.write(valueArray[i]); //historyValue
            }
            cw.writeln();
        }
        else { // pi.historySize == 0
            value = readValue(is, pi.propTypeIndex);

            cw.write(arrayIndex); //arrayIndex
            cw.write(value); //currentValue
            cw.write(0);  //historySize is 0
            cw.write(0); // current index is 0
            cw.writeln();
        }
    }

    private String readValue(DataInputStream is, int propTypeIndex) throws Exception{

        switch(propTypeIndex) {
            case PropertyTypes.propertyTypes_atomBoolean:
            case PropertyTypes.propertyTypes_arrayBoolean:
                boolean value =  is.readBoolean();
                return String.valueOf(value);

            case PropertyTypes.propertyTypes_atomConceptReference:
            case PropertyTypes.propertyTypes_atomContainedConcept:
            case PropertyTypes.propertyTypes_arrayConceptReference:
            case PropertyTypes.propertyTypes_arrayContainedConcept:
                if(is.readBoolean()) {
                    long valuel =  is.readLong();
                    return String.valueOf(valuel);
                }
                else {
                    return null; //for reference or contained object it is null
                }

            case PropertyTypes.propertyTypes_atomLong:
            case PropertyTypes.propertyTypes_arrayLong:
                long valuel =  is.readLong();
                return String.valueOf(valuel);

            case PropertyTypes.propertyTypes_atomDateTime:
            case PropertyTypes.propertyTypes_arrayDateTime:
            	long time = is.readLong();
                String timezone = null;
                if(is.readBoolean()) {
                    timezone = is.readUTF();
                }
                Calendar datetime = new GregorianCalendar();
                if(timezone != null) {
                    datetime.setTimeZone(TimeZone.getTimeZone(timezone));
                }
                datetime.setTimeInMillis(time);
                String output = null;
                try {
                    output = ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(datetime)).castAsString();
                } catch (ConversionException ce) {
                    this.context.getLogger().log(Level.ERROR, ce.getMessage(), ce);
                }
                return output;
                
            case PropertyTypes.propertyTypes_atomString:
            case PropertyTypes.propertyTypes_arrayString:
                if(is.readBoolean()) {
                    String value2 =  is.readUTF();
                    return value2;
                }
                else {
                    return null;
                }

            case PropertyTypes.propertyTypes_atomDouble:
            case PropertyTypes.propertyTypes_arrayDouble:
                double value3=  is.readDouble();
                return String.valueOf(value3);

            case PropertyTypes.propertyTypes_atomInt:
            case PropertyTypes.propertyTypes_arrayInt:
                int value4=  is.readInt();
                return String.valueOf(value4);

            default:
                stats.addErrorString(propTypeIndex + ", Unknown property Index seen");
        }

        return null;
    }

    protected void exportEvents() throws Exception {

    	DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database eventstable = dbenv.openDatabase(null, prop.getProperty("be.engine.om.berkeleydb.eventslog"), null);
        Cursor cursor = eventstable.openCursor(null, null);
        String encoding = prop.getProperty("be.encoding");//TODO - Find the right encoding key
        
        this.context.getLogger().log(Level.INFO, "Exporting " + eventstable.count() + " events...");

        // Create event and payload files for each event type in the project, regardless the existence of the event and payload data
        Iterator propdefs = null;
        CSVWriter writers[] = null;
        StringBuffer hdRow = new StringBuffer("id,extId,status,timestamp,retractedFlag");
        
        // Create file for StateTimeoutEvent, no playload
    	String stEvthdRow = new String("id,extId,status,timestamp,retractedFlag,scheduledTime,closure,TTL,SMId,PropertyName");
    	writers = new CSVWriter[2];
        writers[0] = new CSVWriterImpl(outputPath, BE_STATE_TIMEOUT_EVENT_CSV_FILE_NAME);
        writers[0].writeCommentln(stEvthdRow);
        writers[1] = null;
        csvEvtWriters.put(BE_STATE_TIMEOUT_EVENT_CSV_FILE_NAME,writers);
        
        Collection events = context.getProject().getOntology().getEvents();
        for(Iterator it = events.iterator(); it.hasNext();) {
            Event e = (Event) it.next();
            String className = ModelNameUtil.modelPathToGeneratedClassName(e.getFullPath());
            
            this.context.getLogger().log(Level.DEBUG, "Creating event file for: " + className);

            if(e.getType() == Event.SIMPLE_EVENT) { 
            	propdefs = e.getUserProperties();
                while(propdefs.hasNext()) {
                    EventPropertyDefinition pd = (EventPropertyDefinition)propdefs.next();
                    hdRow.append(",").append(pd.getPropertyName());
                }
                writers = new CSVWriter[2];
                writers[0] = new CSVWriterImpl(outputPath, className);
                writers[0].writeCommentln(hdRow.toString());
                writers[1] = new CSVWriterImpl(outputPath, className + "-payload");
                writers[1].writeCommentln("eventId,payloadType,payloadData");
                csvEvtWriters.put(className,writers);
            } else if(e.getType() == Event.TIME_EVENT) {
            	writers = new CSVWriter[2];
                writers[0] = new CSVWriterImpl(outputPath, className);
                writers[0].writeCommentln("id,extId,status,timestamp,retractedFlag,scheduledTime,closure,TTL");
                writers[1] = null;
                csvEvtWriters.put(className,writers);
            }
        }
        
        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            EntityHeader header = new EntityHeader (datais);
            
            String fileName = header.className;
            Event e = null;

            this.context.getLogger().log(Level.DEBUG, "Exporting event: " + header.className + ", id: " + header.id);

            if(header.className.equals(DbUtils.STATE_TIMEOUT_EVENT_CLASS_NAME)) { //StateTimeoutEvent
                writers = (CSVWriter[]) csvEvtWriters.get(BE_STATE_TIMEOUT_EVENT_CSV_FILE_NAME);
                if(writers == null) {// this should not happen unless there are some changes in the project that runs with migration.
                	stats.addErrorString("StateTimeoutEvent: " + header.className + " is not in the project, ignored.");
                	continue;
                }
                header.writeHeader((CSVWriter)writers[0]);
                writeTimeEventProperties(datais, writers[0]);
                //sm_id - StateTimeoutEvent specific
                writers[0].write(datais.readLong());
                //property_name - StateTimeoutEvent specific
                writers[0].write(datais.readUTF());
            } else {
            	String eventPath=DbUtils.EntityPathfromClass(header.className);
                e = context.getProject().getOntology().getEvent(eventPath);
                
            	if(e.getType() == Event.SIMPLE_EVENT) {
                    writers = (CSVWriter[])csvEvtWriters.get(fileName);
                    if(writers == null) { // This should not happen unless there are some changes in the project that runs with migration.
                    	stats.addErrorString("Event: " + header.className + " is not in the project, ignored.");
                    	continue;
                    }
                    header.writeHeader((CSVWriter)writers[0]);
                    
                    propdefs = e.getUserProperties();
                    while(propdefs.hasNext()) {
                        EventPropertyDefinition pd = (EventPropertyDefinition) propdefs.next();
                        writeEventProperty(writers[0], RDFTypes.getIndex(pd.getType()), datais);
                    }
                    
	                if(datais.readBoolean()) // This event has payload
	                    writePayloadRow(writers[1], header.id,  datais);
	                
	            } else if(e.getType() == Event.TIME_EVENT) {
	                writers = (CSVWriter[])csvEvtWriters.get(fileName);
	                if(writers == null) {// This should not happen unless there are some changes in the project that runs with migration.
	                	stats.addErrorString("Time Event: " + header.className + " is not in the project, ignored.");
                    	continue;
	                }
	                header.writeHeader((CSVWriter)writers[0]);
	                writeTimeEventProperties(datais, writers[0]);
	            }
            }
            writers[0].writeln();
            //if (writers[1] != null) writers[1].writeln();
            stats.incrementsEvents();
        }

        for(Iterator it=csvEvtWriters.values().iterator();it.hasNext();) {
            writers = (CSVWriter[]) it.next();
            writers[0].close();
            if(writers[1] != null) writers[1].close();
        }
        csvEvtWriters.clear();
        cursor.close();
        eventstable.close();

        this.context.getLogger().log(Level.INFO, "Exported " + stats.getEventCount() + " events...");
    }

    private void writeEventProperty(CSVWriter writer, int rdftype, DataInputStream is) throws Exception {
        switch(rdftype) {

        case RDFTypes.STRING_TYPEID :
        case RDFTypes.DATETIME_TYPEID :
            String strval = null;
            if(is.readBoolean())
                strval=is.readUTF();
            writer.write(strval);
            break;
        case RDFTypes.INTEGER_TYPEID :
        	is.readBoolean();
            int intval=is.readInt();
            writer.write(intval);
            break;
        case RDFTypes.LONG_TYPEID :
        case RDFTypes.CONCEPT_TYPEID :
        case RDFTypes.CONCEPT_REFERENCE_TYPEID :
        	is.readBoolean();
            long longval = is.readLong();
            writer.write(longval);
            break;
        case RDFTypes.DOUBLE_TYPEID :
        	is.readBoolean();
            double dblval=is.readDouble();
            writer.write(dblval);
            break;
        case RDFTypes.BOOLEAN_TYPEID :
        	is.readBoolean();
            boolean boolval=is.readBoolean();
            writer.write(boolval ? 1 : 0);
            break;
        default:
            stats.addErrorString(RDFTypes.driverTypeStrings[rdftype] + ", Unknown property type seen");
        }
    }
    
    private void writePayloadRow(CSVWriter writer, long id, DataInputStream datais) throws Exception {
    	int payloadtype = datais.readInt();
        int payloadlength = datais.readInt();
        byte[] payloadbytes = new byte[payloadlength];
        datais.readFully(payloadbytes);
        
        //this.context.getLogger().log(Level.DEBUG, id + ", " + payloadtype + ", " + new String(payloadbytes));

        writer.write(id).write(payloadtype);
        writer.write(Base64.encodeBytes(payloadbytes));
        //writer.write(new String(payloadbytes));
        writer.writeln();
    }
    
    private void writeTimeEventProperties(DataInputStream datais, CSVWriter writer) throws Exception {
    	//scheduledTime
        writer.write(datais.readLong());
        //closure
        if(datais.readBoolean())
            writer.write(datais.readUTF());
        else
        	writer.write(null);
        //TTL
        writer.write(datais.readLong());
    }
    
    protected void writeControlFile(ExpImpContext context, String bdbVersion) throws Exception {
        CSVWriter controlWriter = new CSVWriterImpl(outputPath, "export-control");
        controlWriter.writeCommentln("project-name,project-config-version,data-version,lastInternalId,#instances,#events,#error,#warnings");
        controlWriter.write(context.getProjectName());
        controlWriter.write(context.getComponentVersion());
        controlWriter.write(bdbVersion);
        controlWriter.write(0); // for the lastInternalId column in BE 1.4
        controlWriter.write(stats.getInstanceCount());
        controlWriter.write(stats.getEventCount());
        controlWriter.write(stats.getErrorCount());
        controlWriter.write(stats.getWarningCount());
        controlWriter.writeln();
        controlWriter.close();
    }
    private String getBdbVersion() throws Exception {

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream dataos = new DataOutputStream(bytes);
        Database versiontable = null;
        
        try {
        	versiontable = dbenv.openDatabase(null, prop.getProperty("be.engine.om.berkeleydb.beversiontable"), null);
	    } catch (Exception e) {
	        this.context.getLogger().log(Level.FATAL, e, e.getMessage());
	        System.exit(1);
	    }
        dataos.writeUTF( BE_VERSION_RECORD);
        keyEntry.setData(bytes.toByteArray());
        versiontable.get(null, keyEntry, dataEntry, null);
        DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));

        String bdbVersion = datais.readUTF();
        return bdbVersion;
    }

    protected void cleanupCSVCeptWriter() {
        CSVWriter writers[] = null;

        try {
            for(Iterator it=csvCeptWriters.values().iterator();it.hasNext();) {
                writers = (CSVWriter[])it.next();
                writers[0].close();
                if(writers[1] != null)
                    writers[1].close();
            }
            csvCeptWriters.clear();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
   /**
    * BE 2.x BDB does not have scorecardIds table that was in BE 1.x
    * This dummy file is just to satisfy the Importer
    */
    private void writeDummyScorecardFile() throws Exception {
        CSVWriter scWriter = new CSVWriterImpl(outputPath, "scorecardIds");
        scWriter.writeCommentln("scorecardName,scorecardId");
    }

    protected class ExportPropIndexToPropInfo implements Runnable {
        public void run() {
            Thread.currentThread().setName("PropIndexToPropInfo.ExportThread");

            try {
                exportPropIndexToPropInfo();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            exporterThreadPool.afterExecute();
        }
    }

    protected class ExportEvents implements Runnable {
        public void run() {
            Thread.currentThread().setName("Events.ExportThread");

            try {
                exportEvents();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            exporterThreadPool.afterExecute();
        }
    }

    protected class ExportConcepts implements Runnable {
        public void run() {
            Thread.currentThread().setName("Concepts.ExportThread");

            try {
                exportConcepts();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            exporterThreadPool.afterExecute();
        }
    }

    protected static class EntityHeader {

        boolean retractedFlag; // Concept/Event marked deleted? Part of status too.
        String className; // Concept/Event class name
        long timeStamp; // Time of add/delete -- introduced in 1.1
        long id; // Internal Id
        String extId; //External Id

        EntityHeader(DataInputStream is) throws IOException {
            id = is.readLong();
            if(is.readBoolean())
                extId = is.readUTF();
            else
                extId = null;
            retractedFlag = is.readBoolean();
            className = is.readUTF();
            timeStamp = is.readLong();
        }

        /**
         * Writes the entity header and marks the end of the line.
         */
        public void writeHeaderOnly(CSVWriter cw) throws IOException {

            cw.write(id).write(extId).write(null).write(timeStamp).write(retractedFlag);
            cw.writeln();
        }

        /**
         * Writes the entity header without marking the end of the line.
         */
        public void writeHeader(CSVWriter cw) throws IOException {

            cw.write(id).write(extId).write(null).write(timeStamp).write(retractedFlag);
        }
    }

    protected static class PropertyInfo {
        public String propName;
        public int propRdfIndex;
        public int historySize;
        public String subjectName;
        public PropertyDefinition pd;
        public byte propTypeIndex; // 2.x no longer persists PropTypeIndex in BDB
    }

}
