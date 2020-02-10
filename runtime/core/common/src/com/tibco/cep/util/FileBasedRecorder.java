package com.tibco.cep.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2007
 * Time: 4:52:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class FileBasedRecorder implements ChangeListener {
    public static final String BRK = System.getProperty("line.separator", "\n");

    public static int RECORD_ASSERTED           = 0x01;
    public static int RECORD_MODIFIED           = 0x02;
    public static int RECORD_RETRACTED          = 0x04;
    public static int RECORD_SCHEDULED_TIMEVENT = 0x08;
    public static int RECORD_RULE_FIRED         = 0x10;
    public static int RECORD_ACTION_EXECUTED    = 0x20;
    public static int RECORD_EVENT_EXPIRY       = 0x40;
    public static int RECORD_FUNCTION_EXECUTED  = 0x80;
    public static int RECORD_EVENT_EXPIRED      = 0x100;

    int options;
    Filter[] filters;
    String dirPath;
    HashMap writerMap;
    HashMap fileNameMap;
    String globalFileName;

    final static Long globalFileId = new Long(0);

    long rotateFileLen  = Long.parseLong(System.getProperty("com.tibco.cep.fileBasedRecorder.log.maxsize", "10000000"));
    short rotateFileNum = Short.parseShort(System.getProperty("com.tibco.cep.fileBasedRecorder.log.maxnum", "9"));

    public FileBasedRecorder(String dirPath_, String globalFile, int options_) {
        dirPath        = dirPath_;
        writerMap      = new HashMap();
        fileNameMap    = new HashMap();
        filters        = null;
        options        = options_;
        globalFileName = globalFile;
    }

    public void close() {
        try {
            options = 0;
            filters = null;
            Iterator ite = writerMap.values().iterator();
            while(ite.hasNext()) {
                FileWriter fw = (FileWriter) ite.next();
                fw.flush();
                fw.close();
            }
            writerMap.clear();
            fileNameMap.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetOptions(int options_) {
        options = options_;
    }

    public void addFilter(Filter filter) {
        if(filters == null)
            filters = new Filter[] {filter};
        else {
            Filter[] tmp = new Filter[filters.length + 1];
            for(int i = 0; i < filters.length; i++)
                tmp[i] = filters[i];
            filters = tmp;
        }
    }

    public Object[] getFilters() {
        return filters;
    }

    public void resetFilter() {
        filters = null;
    }

    public void asserted(Object obj, ExecutionContext context) {
        if((options & RECORD_ASSERTED) == 0) return;
        try {
            if(this.applyFilters(obj)) {
                FileWriter fw = this.getFileWriter(obj);
                String txt = " - Object Asserted" + BRK + this.objectDetailsToString(obj) + BRK;
                recordToFile(fw, txt, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modified(Object obj, ExecutionContext context) {
        if((options & RECORD_MODIFIED) == 0) return;
        try {
            if(this.applyFilters(obj)) {
                FileWriter fw = this.getFileWriter(obj);
                String txt = " - Object Modified" + BRK + this.objectDetailsToString(obj) + BRK;
                recordToFile(fw, txt, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retracted(Object obj, ExecutionContext context) {
        try {
            if((options & RECORD_RETRACTED) == 0) {
                this.closeFileWriter(obj);
            }
            else {
                if(applyFilters(obj)) {
                    FileWriter fw = this.getFileWriter(obj);
                    recordToFile(fw, " - Object Retracted", context);
                    this.closeFileWriter(obj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void ruleFired(AgendaItem context) {
        if((options & RECORD_RULE_FIRED) == 0) return;
        Object[] objs = (Object[]) context.getParams();
        boolean writeGlobal = filters == null;
        for(int i = 0; i < objs.length; i++) {
            if(applyFilters(objs[i])) {
                recordToFile(getFileWriter(objs[i]), "- Rule Fired", context);
                writeGlobal = true;
            }
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Rule Fired", context);
    }

    public void scheduledTimeEvent(Event evt, ExecutionContext context) {
        if((options & RECORD_SCHEDULED_TIMEVENT) == 0) return;
        boolean writeGlobal = filters == null;
        if(applyFilters(evt)) {
            recordToFile(getFileWriter(evt), "- Scheduled TimeEvent", context);
            writeGlobal = true;
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Scheduled TimeEvent", context);
    }

    public void actionExecuted(ActionExecutionContext context) {
        if((options & RECORD_ACTION_EXECUTED) == 0) return;
        boolean writeGlobal = filters == null;
        Object[] objs = (Object[]) context.getParams();
        if(objs !=null && objs.length > 0) {
            for(int i = 0; i < objs.length; i++) {
                if(applyFilters(objs[i])) {
                    recordToFile(getFileWriter(objs[i]), "- Action Executed", context);
                    writeGlobal = true;
                }
            }
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Action Executed", context);
    }

    public void eventExpiryExecuted(EventExpiryExecutionContext context) {
        if((options & RECORD_EVENT_EXPIRY) == 0) return;
        boolean writeGlobal = filters == null;
        if(applyFilters(context.getCause())) {
            recordToFile(getFileWriter(context.getCause()), "- Event Expiry Executed", context);
            writeGlobal = true;
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Event Expiry Executed", context);
    }

    public void eventExpired(Event evt) {
        if((options & RECORD_EVENT_EXPIRED) == 0) return;
        boolean writeGlobal = filters == null;
        if(applyFilters(evt)) {
            recordToFile(getFileWriter(evt), "- Event Expired", null);
            writeGlobal = true;
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Event Expired", null);

    }


    public void functionExecuted(FunctionExecutionContext context) {
        if((options & RECORD_FUNCTION_EXECUTED) == 0) return;
        Object[] objs = (Object[]) context.getParams();
        boolean writeGlobal = filters == null;
        if(objs !=null && objs.length > 0) {
            for(int i = 0; i < objs.length; i++) {
                if(applyFilters(objs[i])) {
                    recordToFile(getFileWriter(objs[i]), "- Function Executed", context);
                    writeGlobal = true;
                }
            }
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Function Executed", context);
    }

    public void functionExecuted(FunctionMapArgsExecutionContext context) {
        if((options & RECORD_FUNCTION_EXECUTED) == 0) return;
        boolean writeGlobal = filters == null;
        Map args = (Map) context.getParams();
        if(args !=null && args.size() > 0) {
            Iterator ite = args.values().iterator();
            while(ite.hasNext()) {
                Object obj = ite.next();
                if(obj != null && applyFilters(obj)) {
                    recordToFile(getFileWriter(obj), "- Function Executed", context);
                    writeGlobal = true;
                }
            }
        }
        if(writeGlobal)
            recordToFile(getFileWriter(null), " - Function Executed", context);
    }

    protected String objectDetailsToString(Object obj) {
        try {
            String ret;
            if(obj instanceof Concept) {
                ExpandedName rootNm=ExpandedName.makeName("", "Instance");
                XiNode node = XiSupport.getXiFactory().createElement(rootNm);
                ((Concept)obj).toXiNode(node, false);
                ret = XiSerializer.serialize(node);
            }
            else if(obj instanceof SimpleEvent) {
                ExpandedName rootNm=ExpandedName.makeName("", "Event");
                XiNode node = XiSupport.getXiFactory().createElement(rootNm);
                ((SimpleEvent)obj).toXiNode(node);
                ret = XiSerializer.serialize(node);
            }
            else if(obj instanceof TimeEvent) {
                ExpandedName rootNm=ExpandedName.makeName("", "Event");
                XiNode node = XiSupport.getXiFactory().createElement(rootNm);
                ((TimeEventImpl)obj).toXiNode(node);
                ret = XiSerializer.serialize(node);
            }
            else if(obj instanceof AdvisoryEventImpl) {
                ExpandedName rootNm=ExpandedName.makeName("", "Event");
                XiNode node = XiSupport.getXiFactory().createElement(rootNm);
                ((AdvisoryEventImpl)obj).toXiNode(node);
                ret = XiSerializer.serialize(node);
            }
            else {
                throw new Exception("Type " + obj.getClass().getName() + " is not supported");
            }
            return "\t" + ret.replaceAll("\n", "\n\t");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void recordToFile(FileWriter fw, String txt, ExecutionContext context) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(new Date()).append(txt).append(BRK);
            if(context != null) {
                String[] contextInfo = context.info();
                for(int i = 0; i < contextInfo.length; i++) {
                    sb.append("\t").append(contextInfo[i]).append(BRK);
                }
            }
            sb.append(BRK);
            fw.write(sb.toString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected boolean applyFilters(Object obj) {
        if(filters == null) return true;
        for(int i = 0; i < filters.length; i++) {
            if(filters[i].eval(obj)) return true;
        }
        return false;
    }

    protected String getFileName(Object obj) {
        Long   fileId;
        if(obj == null) {
            fileId   = globalFileId;
        }
        else {
            fileId   = new Long(((Entity)obj).getId());
        }

        String fileName = (String) fileNameMap.get(fileId);

        if(fileName != null)
            return fileName;
        if(obj != null) {
            fileName = obj.toString();
            int end =  fileName.indexOf("&v=");
            if(end != -1)
                fileName = fileName.substring(0, end);
        }
        else
            fileName = this.globalFileName;

        fileName = fileName.replace('\\', '-');
        fileName = fileName.replace('/', '-');
        fileName = fileName.replace(':', '-');
        fileName = fileName.replace('*', '-');
        fileName = fileName.replace('?', '-');
        fileName = fileName.replace('\"', '-');
        fileName = fileName.replace('<', '-');
        fileName = fileName.replace('>', '-');
        fileName = fileName.replace('|', '-');
        fileName += ".log";
        fileNameMap.put(fileId, fileName);

        return fileName;
    }

    protected FileWriter getFileWriter(Object obj) {
        try {
            Long   fileId;
            if(obj == null)
                fileId   = globalFileId;
            else
                fileId   = new Long(((Entity)obj).getId());
            rotateFiles(obj);
            FileWriter fw = (FileWriter) writerMap.get(fileId);       //todo - use RandomAccessFile
            if(fw == null) {
                String filePath = dirPath + "/" + this.getFileName(obj);
                fw = new FileWriter(filePath, true);    // todo - use RandomAccessFile
                writerMap.put(fileId, fw);
                fw.write("******************************************************************************************************************");
                fw.write(BRK);
                fw.flush();
            }
            return fw;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    protected void closeFileWriter(Object obj) throws IOException {
        try {
            Long   fileId;
            if(obj == null)
                fileId   = globalFileId;
            else
                fileId   = new Long(((Entity)obj).getId());
            FileWriter fw = (FileWriter) writerMap.remove(fileId);
            fileNameMap.remove(fileId);
            if(fw != null) {
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    protected boolean rotateFiles(Object obj) throws IOException {
        String filePath = dirPath + "/" + this.getFileName(obj);
        File file = new File(filePath);
        if(!file.exists()) return true;
        if(file.length() > rotateFileLen) {
            if(rotateFileNum == 0) {
                File filedelete = new File(filePath);
                filedelete.delete();
            }
            else {
                int i = rotateFileNum;
                for(; i > 0; i--) {
                    File fileBackup = new File(filePath + "." + i);
                    if(fileBackup.exists()) {
                        if(i == rotateFileNum) {
                            fileBackup.delete();
                            i--;
                        }
                        break;
                    }
                }
                for(int j = i; j > 0; j--) {
                    File fileToRename = new File(filePath + "." + j);
                    File destFile = new File(filePath + "." + (j+1));
//                    if(!destFile.exists()) destFile.createNewFile();
                    if(!fileToRename.renameTo(destFile)) {
                        System.err.println("Can't rename file from " + filePath + " to " + filePath + "." + 1);
                        return false;
                    }
                }
                File fileToRename = new File(filePath);
                File destFile =  new File(filePath + "." + 1);
                closeFileWriter(obj);
//                if(!destFile.exists()) destFile.createNewFile();
                if(!fileToRename.renameTo(destFile)) {
                    System.err.println("Can't rename file from " + filePath + " to " + filePath + "." + 1);
                    return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    static public class Filter {
        RuleFunction function;
        String uri;

        public Filter(String functionURI) {
            uri = functionURI;
            function = ((BEClassLoader) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(functionURI);
//            function.ParameterDescriptor      -todo check for return type and parameter type
        }

        boolean eval(Object obj) {
            return ((Boolean)function.invoke(new Object[] {obj})).booleanValue();
        }

        public String getFilterURI() {
            return uri;
        }
    }


    static public void start(RuleSessionImpl session, String dirPath, String mode) {
        int options = 0;

        if(mode != null && mode.length() > 0) {
            if(mode.indexOf("c") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_ASSERTED;

            if(mode.indexOf("u") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_MODIFIED;

            if(mode.indexOf("d") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_RETRACTED;

            if(mode.indexOf("s") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_SCHEDULED_TIMEVENT;

            if(mode.indexOf("r") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_RULE_FIRED;

            if(mode.indexOf("a") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_ACTION_EXECUTED;

            if(mode.indexOf("x") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_EVENT_EXPIRY;

            if(mode.indexOf("f") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_FUNCTION_EXECUTED;

            if(mode.indexOf("e") != -1)
                options |= com.tibco.cep.util.FileBasedRecorder.RECORD_EVENT_EXPIRED;
        }
        else {
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_ASSERTED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_MODIFIED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_RETRACTED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_SCHEDULED_TIMEVENT;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_RULE_FIRED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_ACTION_EXECUTED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_EVENT_EXPIRY;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_FUNCTION_EXECUTED;
            options |= com.tibco.cep.util.FileBasedRecorder.RECORD_EVENT_EXPIRED;
        }

        String globalFileName = session.getRuleServiceProvider().getName() + "-" + session.getName();
        FileBasedRecorder fr = new FileBasedRecorder(dirPath, globalFileName, options);
        ((WorkingMemoryImpl)session.getWorkingMemory()).addChangeListener(fr);
    }

    static public boolean stop(RuleSessionImpl s) {
        Collection<ChangeListener> changeListeners = ((WorkingMemoryImpl) s.getWorkingMemory()).getChangeListeners();
        FileBasedRecorder fr = null;
        for (ChangeListener changeListener : changeListeners) {
            if (changeListener instanceof FileBasedRecorder) {
                fr = (FileBasedRecorder)changeListener;
                break;
            }
        }
        if (fr != null) {
            fr.close();
            ((WorkingMemoryImpl) s.getWorkingMemory()).removeChangeListener(fr);
            return true;
        }
        else
            return false;
    }    

}

