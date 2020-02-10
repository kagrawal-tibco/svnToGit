package com.tibco.be.dbmigration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.tibco.be.dbutils.DBException;
import com.tibco.be.util.BEProperties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Apr 4, 2005
 * Time: 5:10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DbMigration {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    static private final String DIRNAME_DATE_FORMAT = "yyyy-MM-dd.HH-mm-ssZ";
    static private final String PROPERTYINDEX_MARKER = "$MarkerRecord$"; // From RdfHierarchyIndexer.java
    private static final String BE_VERSION_RECORD="BEVersionRecord"; // From ObjectManager.java
    private static final String BE_VERSION="1.1.0";
    private Environment env;
    private Database propertyIndexTable;
    private Database conceptTable;
    private Database eventLog;
    private Database propertiesTable;
    private Database oldPropertiesTable;
    private HashMap conceptNameOldIndexToNewIndexMap, propNameToNewIndexMap;
    private BEProperties beprops = BEProperties.loadDefault();

    public DbMigration(String dbpath) throws DatabaseException, IOException {
        File db = new File(dbpath);
        makeBackup(db);

        conceptNameOldIndexToNewIndexMap = new HashMap();
        propNameToNewIndexMap = new HashMap();

        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(false);
        envConfig.setTransactional(true);
        env = new Environment(db, envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(false);
        dbConfig.setTransactional(true);

        propertyIndexTable = env.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.propertyindextable"), dbConfig);
        conceptTable = env.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.conceptable"), dbConfig);
        eventLog = env.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.eventslog"), dbConfig);
        propertiesTable = null; // Will be opened and renamed in a lower method
        oldPropertiesTable = null; // Will be opened later

        try {
            doMigration();
        } finally {
            if (propertiesTable != null)
                propertiesTable.close();
            if (oldPropertiesTable != null)
                oldPropertiesTable.close();
            conceptTable.close();
            propertyIndexTable.close();
            eventLog.close();
            env.close();
        }
    }

    private void doMigration() throws DatabaseException, IOException {
        if(checkDbVersion())
            System.out.println("Looks like this is a Version 1.0 database. Upgrade starting...");
        else {
            System.out.println("Looks like this is a Version 1.1 database. Will not be upgraded");
            return;
        }

        readPropertyIndexTable();

        Transaction txn = env.beginTransaction(null, null);
        try {
            updatePropertiesIndexTable(txn);
            updatePropertiesTable(txn);
            updateConceptTable(txn);
            updateEventLog(txn);
            createVersionTable(txn);
        } catch(DatabaseException e) {
            txn.abort();
            throw(e);
        } catch(IOException e) {
            txn.abort();
            throw(e);
        }

        txn.commit();
        System.out.println("Database Upgrade completed successfully.");
    }

    private void makeBackup(File db) {
        SimpleDateFormat df = new SimpleDateFormat(DIRNAME_DATE_FORMAT);
        String dateSuffix = df.format(new Date(System.currentTimeMillis()));

        try {
            File backupDir = new File(db.getAbsolutePath() + ".backup." + dateSuffix);
            System.out.println("Creating a backup of the current database in " + backupDir.getAbsolutePath());
            if (!db.renameTo(backupDir)) {
                System.out.println("Unable to create backup directory " + backupDir.getAbsolutePath());
                System.exit(1);
            }

            if(!db.mkdirs()) {
                System.out.println("Unable to recreate the db directory " + db.getAbsolutePath());
                System.exit(1);
            }
            copyFiles(backupDir, db);
            System.out.println("The old database has been backed up in " + backupDir.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void copyFiles(File oldDir, File newDir) throws IOException {
        File[] files = oldDir.listFiles();
        int len;
        for (int i = 0; i < files.length; i++) {
            File oldFile = files[i];
            if (oldFile.isFile()) {
                File newFile = new File(newDir.getAbsolutePath() + "/" + oldFile.getName());
                copyOneFile(oldFile, newFile);
            } else if (oldFile.isDirectory()) {
                File newSubDir = new File(newDir.getAbsolutePath() + "/" + oldFile.getName());
                newSubDir.mkdirs();
                copyFiles(oldFile, newSubDir);
            }
        }
    }

    private void copyOneFile(File oldFile, File newFile) throws IOException {
        int len;
        newFile.createNewFile();
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(oldFile));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(newFile));
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) > 0) {
            os.write(buffer, 0, len);
        }
        is.close();
        os.close();
    }

    private boolean checkDbVersion() throws DatabaseException {
        Database versionTable = null;
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(false);
        dbConfig.setTransactional(false);
        try {
            versionTable = env.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.beversiontable"), dbConfig);
        } catch (DatabaseNotFoundException e ) {
            return true; // 1.0 did not have this table.
        }

        if(versionTable != null) {
            versionTable.close(); // table found. cannot be a 1.0 database.
            return false;
        } else
            return true;

    }

    private void createVersionTable(Transaction txn) throws DatabaseException, IOException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setExclusiveCreate(true);
        dbConfig.setTransactional(true);
        Database versionTable = env.openDatabase(txn, beprops.getString("be.engine.om.berkeleydb.beversiontable"), dbConfig);

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream keybytes = new ByteArrayOutputStream();
        ByteArrayOutputStream databytes = new ByteArrayOutputStream();
        DataOutputStream keyos = new DataOutputStream(keybytes);
        DataOutputStream dataos = new DataOutputStream(databytes);

        keyos.writeUTF(BE_VERSION_RECORD);
        dataos.writeUTF(BE_VERSION);
        keyEntry.setData(keybytes.toByteArray());
        dataEntry.setData(databytes.toByteArray());
        versionTable.put(txn, keyEntry, dataEntry);
        versionTable.close();
    }

    private void readPropertyIndexTable() throws DatabaseException, IOException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        int assigningId = 1;
        Cursor cursor = propertyIndexTable.openCursor(null, null);

        try {
            while (cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
                DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));
                DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));

                String propName = keyis.readUTF();
                int oldPropIndex = datais.readInt();

                if (propName.equals(PROPERTYINDEX_MARKER)) {
                    continue; // Skip over the Marker for now.. update it later.
                }

                String[] contents = propName.split("\\$\\$");
                String conceptName = contents[0];
                String propertyName = contents[1];
                int newPropIndex = assigningId++; // Just an increasing number now in 1.1

                // add to all required maps.
                String conceptNameOldPropIndex = conceptName + "@@" + oldPropIndex;
                Integer newPropIndexObj = new Integer(newPropIndex);
                conceptNameOldIndexToNewIndexMap.put(conceptNameOldPropIndex, newPropIndexObj);
                propNameToNewIndexMap.put(propName, newPropIndexObj);
            }

            // For Marker record
            Integer markerIndexObj = new Integer(assigningId);
            propNameToNewIndexMap.put(PROPERTYINDEX_MARKER, markerIndexObj);
        } finally {
            cursor.close();
        }
    }

    private void updatePropertiesIndexTable(Transaction txn) throws IOException, DatabaseException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream databytes = new ByteArrayOutputStream();
        ByteArrayOutputStream keybytes = new ByteArrayOutputStream();
        DataOutputStream dataos = new DataOutputStream(databytes);
        DataOutputStream keyos = new DataOutputStream(keybytes);

        Collection newIndices = propNameToNewIndexMap.entrySet();
        for(Iterator iter = newIndices.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            keyEntry.setData(null);
            dataEntry.setData(null);
            keybytes.reset();
            databytes.reset();

            String propName = (String) entry.getKey();
            Integer newIndexObj = (Integer) entry.getValue();
            // Update the values in the table

            keyos.writeUTF(propName);
            dataos.writeInt(newIndexObj.intValue());
            keyEntry.setData(keybytes.toByteArray());
            dataEntry.setData(databytes.toByteArray());

            propertyIndexTable.put(txn, keyEntry, dataEntry);
        }
    }

    private void updatePropertiesTable(Transaction txn) throws DatabaseException, IOException {
        // Move the old table. Create a fresh one. This is because we cannot update keys in place.
        String bepropstable = beprops.getString("be.engine.om.berkeleydb.propertiestable");
        String bepropstableOld = bepropstable + ".old";

        env.renameDatabase(txn, bepropstable, bepropstableOld);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(false);
        dbConfig.setTransactional(true);
        oldPropertiesTable = env.openDatabase(txn, bepropstableOld, dbConfig);

        dbConfig.setAllowCreate(true);
        dbConfig.setExclusiveCreate(true);
        propertiesTable = env.openDatabase(txn, bepropstable, dbConfig);

        Cursor cursor = oldPropertiesTable.openCursor(txn, null);
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream keybytes = new ByteArrayOutputStream();
        DataOutputStream keyos = new DataOutputStream(keybytes);
        long oldSubjectId = -1;
        String conceptName = null;

        // Iterate over all records and assign the new property indices.
        try {
            while (cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
                DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));

                long subjectId = keyis.readLong();
                int oldPropIndex = keyis.readInt();

                // Lookup the new Index.
                if (subjectId != oldSubjectId) {
                    // Need to get the next subject's concept name.
                    conceptName = getConceptName(subjectId);
                    oldSubjectId = subjectId;
                }
                String conceptNameOldPropIndex = conceptName + "@@" + oldPropIndex;
                Integer newIndexObj = (Integer) conceptNameOldIndexToNewIndexMap.get(conceptNameOldPropIndex);
                int newIndex = newIndexObj.intValue();

                // Update the database Entry.
                keybytes.reset();
                keyEntry.setData(null);
                keyos.writeLong(subjectId);
                keyos.writeInt(newIndex);
                keyEntry.setData(keybytes.toByteArray());
                propertiesTable.put(txn, keyEntry, dataEntry);
            }
        } finally {
            cursor.close();
            oldPropertiesTable.close();
            propertiesTable.close();
            oldPropertiesTable = null;
            propertiesTable = null;
            env.removeDatabase(txn, bepropstableOld);
        }
    }

    private String getConceptName(long subjectId) throws IOException, DatabaseException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream keybytes = new ByteArrayOutputStream();
        DataOutputStream keyos = new DataOutputStream(keybytes);
        keyos.writeLong(subjectId);
        keyEntry.setData(keybytes.toByteArray());

        if(conceptTable.get(null, keyEntry, dataEntry, null) != OperationStatus.SUCCESS) {
            throw new DBException("Concept name not found for Instance id = " + subjectId);
        }

        DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
        datais.readBoolean(); // retracted flag - skip over
        String conceptName = datais.readUTF();
        return conceptName;
    }

    public static void main(String args[]) throws DatabaseException, IOException {
        new DbMigration(args[0]);
    }

    private void updateEventLog(Transaction txn) throws DatabaseException {
        updateEntityRecords(txn, eventLog);
    }

    private void updateConceptTable(Transaction txn) throws DatabaseException {
        updateEntityRecords(txn, conceptTable);
    }

    private void updateEntityRecords(Transaction txn, Database db) throws DatabaseException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntityBinding binding = new EntityBinding();

        Cursor cursor = db.openCursor(txn, null);

        try{
            while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {

                EntityHeaderandData entity = (EntityHeaderandData) binding.entryToObject(dataEntry);
                binding.objectToEntry(entity, dataEntry); // Will add the time value
                cursor.putCurrent(dataEntry);
            }
        } finally {
            cursor.close();
        }
    }

    private static class EntityHeaderandData {
        private boolean isRetracted;
        private String entityClassname;
        private static final long timeStamp = System.currentTimeMillis(); // Set to the same value for all records.
        private byte[] dataportion;

        public boolean isRetracted() {
            return isRetracted;
        }

        public void setRetracted(boolean retracted) {
            isRetracted = retracted;
        }

        public String getEntityClassname() {
            return entityClassname;
        }

        public void setEntityClassname(String entityClassname) {
            this.entityClassname = entityClassname;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public byte[] getDataportion() {
            return dataportion;
        }

        public void setDataportion(byte[] dataportion) {
            this.dataportion = dataportion;
        }
    }

    private static class EntityBinding extends TupleBinding {

        private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private static final DataOutputStream dataos = new DataOutputStream(baos);

        /**
         * Constructs a key or data object from a {@link com.sleepycat.bind.tuple.TupleInput} entry.
         *
         * @param input is the tuple key or data entry.
         * @return the key or data object constructed from the entry.
         */
        public Object entryToObject(TupleInput input) {

            EntityHeaderandData entity = new EntityHeaderandData();
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(input.getBufferBytes()));
            try {
                entity.setRetracted(datais.readBoolean());
                entity.setEntityClassname(datais.readUTF());
                int len = datais.available();
                byte[] buf = new byte[len];
                datais.read(buf);
                entity.setDataportion(buf);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            /*
            entity.setRetracted(input.readBoolean());
            short len = input.readShort();
            entity.setEntityClassname(input.readBytes(len));
            // Old entries do not have the checkpointed time.
            // Rest is data.
            entity.setDataportion(input.readBytes(input.available()));
            */
            return entity;
        }

        /**
         * Converts a key or data object to a tuple entry.
         *
         * @param object is the key or data object.
         * @param output is the tuple entry to which the key or data should be
         *               written.
         */
        public void objectToEntry(Object object, TupleOutput output) {

            baos.reset();
            EntityHeaderandData entity = (EntityHeaderandData) object;

            try {
                dataos.writeBoolean(entity.isRetracted());
                dataos.writeUTF(entity.getEntityClassname());
                dataos.writeLong(entity.getTimeStamp());
                dataos.write(entity.getDataportion());
                output.write(baos.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            /*
            output.writeBoolean(entity.isRetracted());
            //output.writeString(entity.getEntityClassname());
            output.writeShort(entity.getEntityClassname().length());
            output.writeBytes(entity.getEntityClassname());
            // New entries have the checkpoint time.
            output.writeLong(entity.getLastCheckpointedAt());
            output.writeBytes(entity.getDataportion());
            */
        }
    }
}
