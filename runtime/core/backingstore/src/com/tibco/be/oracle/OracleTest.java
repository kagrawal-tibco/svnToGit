package com.tibco.be.oracle;

import java.io.FileOutputStream;
import java.sql.DriverManager;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class OracleTest {
    RuleServiceProvider provider;
    TypeManager typeManager;
    BEProject beProject;
    Ontology ontology;
    OracleConnection oracle;
    OracleSchemaFile schemaFile;
    FileOutputStream file_output;

    public static void main (String [] args) {
        try {
            new OracleTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect2Oracle() {
        try {
            OracleDriver driver = new OracleDriver();
            DriverManager.registerDriver(driver);
            //DriverManager.getConnection()
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();

            ds.setURL("jdbc:oracle:thin:@localhost:1521:ORCL");
            oracle = (OracleConnection) ds.getConnection("be", "be");

            System.out.println("Got Connection...");
            testOracleObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testOracleObjects () throws Exception{
        //DatabaseMetaData dmd= oracle.getMetaData();
        StructDescriptor sd1 = StructDescriptor.createDescriptor("T_NEWCONCEPT", oracle);
        StructDescriptor sd2 = StructDescriptor.createDescriptor("T_CONCEPT", oracle);
        StructDescriptor sd3 = StructDescriptor.createDescriptor("T_ENTITY", oracle);
        System.out.println("StructDescriptor#1:" + sd1);
        System.out.println("StructDescriptor#2:" + sd2);
        System.out.println("StructDescriptor#3:" + sd3);
        int numAttrs= sd1.getOracleTypeADT().getNumAttrs();
        for (int j=0; j< numAttrs; j++) {
        	System.out.println("  Attribute-Name: " + sd1.getOracleTypeADT().getAttributeName(j+1));
        	System.out.println("  Attribute-Type: " + sd1.getOracleTypeADT().getAttributeType(j+1));
        }
        STRUCT struct = new STRUCT(sd1,oracle,new Object[]{
                new Integer(10), "1234", new Character('A'),null,null,
                "1234", new Integer(1)});

        OraclePreparedStatement insertStatement = (OraclePreparedStatement) oracle.prepareStatement("INSERT INTO ALL_ENTITIES VALUES(T_NEWCONCEPT(?,?,?,?,?,?,?))");
        //insertStatement.setStructDescriptor(1,sd);
        Datum[] attrs= struct.getOracleAttributes();
        for (int i=0; i < attrs.length;i++) {
            insertStatement.setOracleObject(i+1,attrs[i]);
        }
        insertStatement.executeUpdate();

        OracleStatement st= (OracleStatement) oracle.createStatement();
        OracleResultSet rs= (OracleResultSet) st.executeQuery("SELECT * FROM ALL_ENTITIES");

        while (rs.next()) {
            ORAData oraData= rs.getORAData(1, OracleEntity.getORADataFactory());
            //oracle.sql.STRUCT o= (oracle.sql.STRUCT) rs.getObject(1);
            //String [] names= o.getDescriptor().getAttributeJavaNames();
            System.out.println("ORAData:" + oraData);
        }
        rs.close();
        st.close();

        /**
        OracleStatement st= (OracleStatement) oracle.createStatement();
        OracleResultSet rs= (OracleResultSet) st.executeQuery("SELECT * FROM a");

        while (rs.next()) {
            //ORAData oraData= rs.getORAData(1, OracleEntity.getORADataFactory());
            Object a1= rs.getObject(1);
            oracle.sql.TIMESTAMPTZ tz= rs.getTIMESTAMPTZ(2);
            //oracle.sql.DATE dt= TIMESTAMPTZ.toDATE();

            //oracle.sql.TIMESTAMPTZ tz= rs.getTIMESTAMPTZ(2);
            //oracle.sql.TIMEZONETAB.
            //java.sql.Timestamp tt;
            //oracle.sql.STRUCT o= (oracle.sql.STRUCT) rs.getObject(1);
            //String [] names= o.getDescriptor().getAttributeJavaNames();
            //System.out.println(oraData);
            //System.out.println("Timezone = " + tz.stringValue(oracle));
            //DateFormat df = DateFormat.getDateTimeInstance()
            java.util.Date dt;
        }
        rs.close();
        st.close();
        **/
    }

    /**
     *
     *
     * @throws Exception
     */
    private OracleTest() throws Exception {
        connect2Oracle();
    }
}
