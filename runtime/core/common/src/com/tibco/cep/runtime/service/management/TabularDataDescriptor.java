package com.tibco.cep.runtime.service.management;

import java.util.HashMap;

import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jan 12, 2010
 * Time: 6:03:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TabularDataDescriptor {

    //Column and item have the same meaning in this class and are used interchangeably. Each item is a column in the table

    private static TabularDataDescriptor instance = null;
    private static HashMap<String, ItemsDescriptor> methodToItemsDescriptor = null;

    //The items descriptor for every exposed method returning tabular data are created in this constructor.
    //The descriptors are put in a hashmap. The key is the invoked method name.
    public TabularDataDescriptor() {
        methodToItemsDescriptor = new HashMap<String, ItemsDescriptor>();
            //cols structure for method getDestinations
        methodToItemsDescriptor.put( "getDestinations", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.LONG,
                     SimpleType.DOUBLE, SimpleType.LONG, SimpleType.DOUBLE, SimpleType.BOOLEAN},
            new String[]{"Row","Channel URI","Destination URI", "Num Events Received", "Received Events Rate",
                                "Num Events Sent", "Received Events Rate in last stats interval","Suspended"},
            new String[]{"Row","channelURI","destinationURI", "numEventsReceived", "receivedEventsRate",
                                "numEventsSent", "lastStatsIntervalReceivedEventsRate","suspended"},
            new String[]{"Row"},
            "DestinationRowType",
            "Type of each row of the table with the Destinations info",
            "DestinationTabularType",
            "Type to represent Destinations info in tabular form") );

            //cols structure for method getChannels
        methodToItemsDescriptor.put("getChannels", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","channel URI", "state"},
            new String[]{"Row","ChannelURI", "Current state of the Channel"},
            new String[]{"Row"},
            "ChannelRowType",
            "Type of each row of the table with the Channel info",
            "ChannelTabularType",
            "Type to represent Channels information in tabular form") );

            //cols structure for method getSessionInputDestinations
        methodToItemsDescriptor.put("getSessionInputDestinations", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Destination URI", "PreprocessorURI"},
            new String[]{"Row","DestinationURI", "Destination preprocessor URI"},
            new String[]{"Row"},
            "SessionInputDestRowType",
            "Type of each row of the table with the Session Input Channel info",
            "SessionInputDestTabularType",
            "Type to represent the Session Input Channels information in tabular form") );

            //cols structure for method getSessions
        methodToItemsDescriptor.put("getSessions", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING},
            new String[]{"Row","Session"},
            new String[]{"Row","Name of the Session"},
            new String[]{"Row"},
            "SessionRowType",
            "Type of each row of the table with the session info",
            "SessionTabularType",
            "Type to represent the session information in tabular form") );

        //cols structure for method getTotalNumberRulesFired
        methodToItemsDescriptor.put("getTotalNumberRulesFired", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.LONG},
            new String[]{"Row","Session","Number of Rules Fired"},
            new String[]{"Row","Name of the Session", "Total number of rules fired since the last reset"},
            new String[]{"Row"},
            "TotalNumRulesFiredRowType",
            "Type of each row of the table with the total number of rules fired info",
            "TotalNumRulesFiredTabularType",
            "Type to represent the total number of rules fired info in tabular form") );
        
        methodToItemsDescriptor.put("getEntityStatistic", new ItemsDescriptor(
                new OpenType[]{SimpleType.STRING, SimpleType.LONG},//SimpleType.LONG
                new String[]{"URI", "Asserted count"},//"Expired count"
                new String[]{"Entity URI", "The asserted count of entity."},//"Number of Events Expired"
                new String[]{"URI"},
                "EntityStatisticsRowType",
                "Type of each row of the table with entity statistics",
                "EntityStatisticsTabularType",
                "Type to represent entity statistics"));//Currently shows just the asserted counts of events/concepts.
        
        //cols structure for method getRules
        methodToItemsDescriptor.put("getRules", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.BOOLEAN, SimpleType.LONG},
            new String[]{"Row","Session","URI", "Activated", "Rule Fired Count"},
            new String[]{"Row","Name of the Session", "URI of the rule", "Is the Rule activated", "Number of times the rule has executed since the last reset"},
            new String[]{"Row"},
            "RulesInfoRowType",
            "Type of each row of the table with the rules info",
            "RulesInfoTabularType",
            "Type to represent the rules info in tabular form") );

        //cols structure for method getRule
        methodToItemsDescriptor.put("getRule", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.INTEGER, SimpleType.INTEGER},
            new String[]{"Row","Session","URI", "Priority", "Internal ID"},
            new String[]{"Row","Name of the Session", "URI of the rule", "Priority of the rule", "Internal ID"},
            new String[]{"Row"},
            "RuleInfoRowType",
            "Type of each row of the table with the specified rule info",
            "RuleInfoTabularType",
            "Type to represent the specified rule info in tabular form") );

       //cols structure for method activateRule
        methodToItemsDescriptor.put("activateRule", new ItemsDescriptor(
            new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.BOOLEAN},
            new String[]{"Session","URI", "Activated"},
            new String[]{"Name of the Session", "URI of the rule", "Is the Rule activated"},
            new String[]{"Session"},
            "ActivateRuleResultRowType",
            "Type of each row of the table with the result of the activate rule action",
            "ActivateRuleResultTabularType",
            "Type to represent the result of the activate rule action in tabular form") );

        //cols structure for method deactivateRule
        methodToItemsDescriptor.put("deactivateRule", new ItemsDescriptor(
            new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.BOOLEAN},
            new String[]{"Session","URI", "Deactivated"},
            new String[]{"Name of the Session", "URI of the rule", "Is the Rule deactivated"},
            new String[]{"Session"},
            "DeActivateRuleResultRowType",
            "Type of each row of the table with the result of the deactivate rule action",
            "DeActivateRuleResultTabularType",
            "Type to represent the result of the deactivate rule action in tabular form") );

        //cols structure for method getEvent
        methodToItemsDescriptor.put("getEvent", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Session","Type","Name","Value"},
            new String[]{"Row","Name of the Session", "Attribute or Property", "Name of the Attribute or Property", "Value of the Attribute or Property"},
            new String[]{"Row"},
            "EventInfoRowType",
            "Type of each row of the table with the Event info",
            "EventInfoTabularType",
            "Type to represent the Event info in tabular form") );

        //cols structure for method getInstance
        methodToItemsDescriptor.put("getInstance", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Session","Type","Name","Value"},
            new String[]{"Row","Name of the Session", "Attribute or Property", "Name of the Attribute or Property", "Value of the Attribute or Property"},
            new String[]{"Row"},
            "InstanceInfoRowType",
            "Type of each row of the table with the Instance info",
            "InstanceInfoTabularType",
            "Type to represent the Instance info in tabular form") );
        
      //cols structure for method getInstanceByUri
        methodToItemsDescriptor.put("getInstanceByUri", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Session","Type","Name","Value"},
            new String[]{"Row","Name of the Session", "Attribute or Property", "Name of the Attribute or Property", "Value of the Attribute or Property"},
            new String[]{"Row"},
            "InstanceInfoRowType",
            "Type of each row of the table with the Instance info",
            "InstanceInfoTabularType",
            "Type to represent the Instance info in tabular form") );

         //cols structure for method getScorecard
        methodToItemsDescriptor.put("getScorecard", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Session","Type","Name","Value"},
            new String[]{"Row","Name of the Session", "Attribute or Property", "Name of the Attribute or Property", "Value of the Attribute or Property"},
            new String[]{"Row"},
            "ScorecardInfoRowType",
            "Type of each row of the table with the Scorecard info",
            "ScorecardInfoTabularType",
            "Type to represent the Scorecard info in tabular form") );

        //cols structure for method getScorecard(s)
        methodToItemsDescriptor.put("getScorecards", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.LONG, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row","Session","Id","External Id","Type"},
            new String[]{"Row","Name of the Session", "Id of the Scorecard", "External Id of the Scorecard", "Class of the Scorecard"},
            new String[]{"Row"},
            "ScorecardsInfoRowType",
            "Type of each row of the table containing the Scorecards of every session",
            "ScorecardsInfoTabularType",
            "Type to represent the Scorecards of every session in tabular form") );

        //cols structure for method getHostInformation
        methodToItemsDescriptor.put("getHostInformation", new ItemsDescriptor(
            new OpenType[]{SimpleType.STRING, SimpleType.STRING},
            new String[]{"Name","Value"},
            new String[]{"Property Name","Property Value"},
            new String[]{"Name"},
            "HostInfoRowType",
            "Type of each row of the table with the Host info",
            "HostInfoTabularType",
            "Type to represent the Host info in tabular form") );

        //cols structure for method getNumberOfEvents
        methodToItemsDescriptor.put("getNumberOfEvents", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.INTEGER},
            new String[]{"Row", "Session","Number of Events"},
            new String[]{"Row", "Name of the Session", "Total Number of Events"},
            new String[]{"Row"},
            "NumberOfEventsRowType",
            "Type of each row of the table with the number of events info",
            "NumberOfEventsTabularType",
            "Type to represent the number of events info in tabular form") );

        //cols structure for method getNumberOfInstances
        methodToItemsDescriptor.put("getNumberOfInstances", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.INTEGER},
            new String[]{"Row", "Session","Number"},
            new String[]{"Row", "Name of the Session", "Total Number of Instances"},
            new String[]{"Row"},
            "NumberOfInstancesRowType",
            "Type of each row of the table with the number of Instances info",
            "NumberOfInstancesTabularType",
            "Type to represent the number of Instances info in tabular form") );

        //cols structure for method geTMemoryUsage
        methodToItemsDescriptor.put("getMemoryUsage", new ItemsDescriptor(
            new OpenType[]{SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG},
            new String[]{"Max", "Free","Used","Percentage Used"},
            new String[]{"Maximum memory size of the JVM, in bytes", "Estimate of the free memory available to the JVM, in bytes",
                    "Estimate of the memory used in the JVM, in bytes","Estimate of the percentage of max memory used"},
            new String[]{"Max"},
            "MemoryUsageInfoRowType",
            "Type of each row of the table with the JVM Memory usage info",
            "MemoryUsageInfoTabularType",
            "Type to represent JVM Memory usage info in tabular form") );
        
        methodToItemsDescriptor.put("getVersionInfo", new ItemsDescriptor(
                new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
                new String[]{"Engine Version", "Application Version", "Application Name"},
                new String[]{"Engine version", "The running application version", "The running application name"},
                new String[]{"Engine Version"},
                "ExecInfoRowType",
                "Type of each row of the table with the engine execution info",
                "ExecInfoTabularType",
                "Type to represent engine execution info in tabular form"));//BE-22598

        //cols structure for method startFileBasedRecorder
        methodToItemsDescriptor.put("startFileBasedRecorder", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row", "Session", "Mode", "Output Directory", "Status"},
            new String[]{"Row", "Name of the Session", "Recording Mode", "Output Directory", "Recording Status"},
            new String[]{"Row"},
            "StartFileBasedRecorderInfoRowType",
            "Type of each row of the table with the File Based Recorder info",
            "StartFileBasedRecorderInfoTabularType",
            "Type to represent the File Based Recorder info in tabular form") );

        //cols structure for method stopFileBasedRecorder
        methodToItemsDescriptor.put("stopFileBasedRecorder", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row", "Session", "Mode", "Output Directory", "Status"},
            new String[]{"Row", "Name of the Session", "Recording Mode", "Output Directory", "Recording Status"},
            new String[]{"Row"},
            "StopFileBasedRecorderInfoRowType",
            "Type of each row of the table with the File Based Recorder info",
            "StopFileBasedRecorderInfoTabularType",
            "Type to represent the File Based Recorder info in tabular form") );

        //cols structure for method getWorkingMemoryDump
        methodToItemsDescriptor.put("getWorkingMemoryDump", new ItemsDescriptor(
            new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING},
            new String[]{"Row", "Session", "Memory Dump"},
            new String[]{"Row", "Name of the Session", "Working Memory Dump"},
            new String[]{"Row"},
            "WorkingMemoryDumpRowType",
            "Type of each row of the table with Working Memory Dump",
            "WorkingMemoryDumpTabularType",
            "Type to represent the Working Memory Dump in tabular form") );

        methodToItemsDescriptor.put("getLoggerNamesWithLevels", new ItemsDescriptor(
        		new OpenType[]{SimpleType.STRING, SimpleType.STRING},
        		new String[]{"Logger Name", "Log Level"},
        		new String[]{"Logger Name", "Current Log Level"},
        		new String[]{"Logger Name"},
        		"LoggerInfoRowType",
        		"Type of each row of the table with logger information",
        		"LoggerInfoTabularType",
        		"Type to represent the Logger information in tabular form") );

    } //constructor

    public static TabularDataDescriptor getInstance(){
        if(instance == null)
            instance = new TabularDataDescriptor();
        return instance;
    }

    public ItemsDescriptor getItemsDescriptor(String invokedMethod) {
       return methodToItemsDescriptor.get(invokedMethod);
    }

    //sacrifice encapsulation for simplicity. Goal is to simulate properties equivalent to C# and other languages
    //Class fields were made final for safety
    public class ItemsDescriptor {
            //map.get(METHOD_NAME).getItemTypes .getItemNames
        final public OpenType[] itemTypes;
        final public String[] itemNames;
        final public String[] itemDescriptions;
        final public String[] indexNames;
        final public String rowTypeName;
        final public String rowTypeDescription;
        final public String tabularTypeName;
        final public String tabularTypeDescription;

        public ItemsDescriptor(OpenType[] itemTypes, String[] itemNames, String[] itemDescriptions, String[] indexNames,
                                 String rowTypeName, String rowTypeDescription, String tabularTypeName, String tabularTypeDescription) {
            this.itemTypes = itemTypes;
            this.itemNames = itemNames;
            this.itemDescriptions = itemDescriptions;
            this.indexNames = indexNames;
            this.rowTypeName = rowTypeName;
            this.rowTypeDescription = rowTypeDescription;
            this.tabularTypeName = tabularTypeName;
            this.tabularTypeDescription = tabularTypeDescription;
        }
    } //ItemsDescriptor

//    //test
//    public static void main (String[] args) {
//         ItemsDescriptor items = getInstance().getItemsDescriptor("getDestinations");
//         System.out.println(items.tabularTypeDescription);
//         items = getInstance().getItemsDescriptor("getChannels");
//         System.out.println(items.tabularTypeDescription);
//    }

} //TabularDataDescriptor
