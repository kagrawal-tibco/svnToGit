package com.tibco.be.oracle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.RuleServiceProvider;

// The following class is used to log unserviced backing store entity deletes so that they may
// be replayed manually, since Tangosol does not currently requeue deletes.
public class RedoLogger {

    //TODO: sgonik 10/26/07 : This method is deprecated; it will become createConnectionRecoveryMgr
    public synchronized static void createRedoLogger( String masterCacheName, RuleServiceProvider rsp ) {
        String filePath = rsp.getProperties().getProperty(masterCacheName + ".be.bstore.redoLog.filePath");
        if( (filePath == null) || (filePath.trim().length() <= 0)) {
            if (!m_warned){
                rsp.getLogger(RedoLogger.class).log(Level.WARN,
                        "############# WARNING:: No redo log is being set for [at least] %s" +
                                        ". Due to a known Tangosol issue DELETE operations will not be" +
                                        " requeued during backing store connection failures and will be lost!" +
                                        " To log DELETE operations during failures please set the" +
                                        " <backing-store-key>.be.bstore.redoLog.filePath property in each VM's" +
                                        " .tra file (use a different file name per VM).",
                                        masterCacheName);
                m_warned = true;
            }
            return;
        }
        if(!s_loggers.containsKey(masterCacheName)) {
            s_loggers.put(masterCacheName, new RedoLogger(masterCacheName, rsp));
        }
    }

    //TODO: sgonik 10/26/07 : This method is deprecated; it will become getConnectionRecoveryMgr
    public static RedoLogger getRedoLogger(String masterCacheName) {
        return (RedoLogger)s_loggers.get(masterCacheName);
    }

    public static boolean inUse(String masterCacheName) {
        return (getRedoLogger(masterCacheName) != null);
    }

    public static void closeIfInUse(String masterCacheName) {
        RedoLogger logger = getRedoLogger(masterCacheName);
        if (logger != null){
            logger.close();
        }
    }

    private RedoLogger(String masterCacheName, RuleServiceProvider rsp) {
        assert(rsp != null);
        assert(masterCacheName != null);
        m_rsp = rsp;
        m_masterCacheName = masterCacheName;
    }


    public synchronized void log(StringBuffer query) throws IOException {
        String q = query.toString();
        if ((q == null) || (q.trim().length() <= 0)) {
            return;
        }
        if (!q.trim().endsWith(";")) {
            query.append(";");
        }
        getPrintWriter().println(query);
    }

    public void log(StringBuffer buf, long id) throws IOException {
        log(resolveQuery(buf, id));
    }

    public void log(StringBuffer buf, String id) throws IOException {
        log(resolveQuery(buf, id));
    }

    public void log(StringBuffer buf, long id, long id2) throws IOException {
        buf = resolveQuery(buf, id);
        log(resolveQuery(buf, id2));
    }

    public synchronized void close(){
        if (m_pwriter != null) {
            m_pwriter.close();
            m_pwriter = null;
        }
    }

    private StringBuffer resolveQuery(StringBuffer query, long id) throws IOException {
        return resolveQuery(query, Long.toString(id));
    }

    private StringBuffer resolveQuery(StringBuffer query, String id) throws IOException {
        int i = query.indexOf("?");
        if (i < 0) {
            throw new
              IOException("RedoLogger:resolveQuery: " + query +
                            ": expected to find \'?\' to replace with value=" + id);
        }
        return query.delete(i, i+1).insert(i, id);
    }

    // create print writer lazily
    private PrintWriter getPrintWriter() throws IOException {
        if (m_pwriter == null) {
            m_pwriter = createWriter(false);
        }
        return m_pwriter;
    }

    private PrintWriter createWriter(boolean appendDateTime) throws IOException {
        File f = null;

        String filePath =
            m_rsp.getProperties().getProperty(m_masterCacheName + ".be.bstore.redoLog.filePath");
        assert(filePath != null);
        filePath = filePath.trim();

        String dirPath = extractDirPath(filePath);
        if (dirPath == null) {
            dirPath = DEFAULT_DIR_PATH;
            m_curFilePath = dirPath + File.separator + filePath;
        }
        else {
            m_curFilePath = filePath;
        }

        new File(dirPath).mkdirs();


        String dateTime = null;
        if (appendDateTime) {
            Date d = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            dateTime = formatter.format(d);
            f = new File(m_curFilePath + "-" + dateTime);
        }
        else {
            f = new File(m_curFilePath);
            // if this file already exists then replay this method with timestamp on
            if( f.exists()) {
                return createWriter(true);
            }
        }

        f.createNewFile();

        // create new PrinWriter with autoflush set to true
        return new PrintWriter(new FileOutputStream(f), true);
    }

    private String extractDirPath(String fullPath) {

        // try getting a path using Unix format, which is always accepted
        int i = fullPath.lastIndexOf('/');
        if (i < 0) {
            // try OS specific path separator
            i = fullPath.lastIndexOf(File.separator);
        }

        if (i < 0) {
            // filename with no dir path
            return null;
        }

        return fullPath.substring(0, i);
    }


    private static Hashtable s_loggers = new Hashtable(5);

    private RuleServiceProvider m_rsp = null;
    private PrintWriter m_pwriter = null;
    private String m_masterCacheName = null;

    private static boolean m_warned = false;

    protected static final String DEFAULT_DIR_PATH = "logs";
    // Used for testing
    protected String m_curFilePath = null;
}
