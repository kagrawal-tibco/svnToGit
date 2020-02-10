package com.tibco.be.dbutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Feb 11, 2005
 * Time: 2:27:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class SQLConnection {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private Connection cnx;
    private DatabaseMetaData metadata;
    static private BEProperties beprops = BEProperties.getInstance();
    private Logger logger = null;

    public SQLConnection(Logger _logger) {
        logger = _logger;
        //String dburl = "jdbc:postgresql:BEdb", user = "be", password = "be";
        //String dburl = "jdbc:oracle:oci8:@BEdb", user = "be", password = "be";
        //get the whole branch so that other connection properties can be passed from the tra file
        BEProperties jdbcProps = beprops.getBranch("be.dbutils.jdbc");
        String dburl = jdbcProps.getString("url");
        if(dburl == null) {
            this.logger.log(Level.FATAL, "No DB URL specified");
            System.exit(1);
        }
        String user = jdbcProps.getString("user");
        if(user == null) {
            this.logger.log(Level.FATAL, "No DB User specified");
            System.exit(1);
        }
        String password = jdbcProps.getString("password");
        if(password == null) {
            this.logger.log(Level.FATAL, "No DB Password specified");
            System.exit(1);
        }

        try {
            cnx = DriverManager.getConnection(dburl, jdbcProps);
        } catch (SQLException e) {
            this.logger.log(Level.FATAL, e.getMessage(), e);
            System.exit(1);
        }

        try {
            metadata = cnx.getMetaData();
            this.logger.log(Level.DEBUG, "Db name = %s", this.getDatabaseProductName());
            this.logger.log(Level.DEBUG, "Max table name length = %s", this.getMaxTableNameLength());

            /*ResultSet rs = metadata.getTypeInfo();
            while (rs.next()) {
                int type = rs.getInt("DATA_TYPE");
                System.out.println("SQL Data Type = " + type + " Data type = " + rs.getString("TYPE_NAME") + " Local Data Type = " + rs.getString("LOCAL_TYPE_NAME") + " Precision = " + rs.getLong("PRECISION") + " Min Scale = " + rs.getShort("MINIMUM_SCALE") + " Max Scale = " + rs.getShort("MAXIMUM_SCALE"));
            }
            */
        } catch (SQLException e) {
            this.logger.log(Level.FATAL, e.getMessage(), e);
            System.exit(1);
        }

    }

    public void close() {
        try {
            cnx.close();
        } catch (SQLException e1) {
            this.logger.log(Level.FATAL, e1.getMessage(), e1);
        }
    }

    protected DBResource executeSQL(String sql) throws SQLException {
        this.logger.log(Level.DEBUG, "Executing SQL:\n" + sql);
        Statement stmt = cnx.createStatement();
        stmt.execute(sql);
        return new DBResource(stmt, null);
    }

    protected DBResource executePreparedStatement(PreparedStatement stmt) throws SQLException {
        this.logger.log(Level.DEBUG, "Executing SQL:\n" + stmt.toString());
        stmt.executeUpdate();
        return new DBResource(stmt, null);
    }

    protected DBResource executeQuery(String sql) throws SQLException {
        this.logger.log(Level.DEBUG, "Executing SQL:\n" + sql);
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return new DBResource(stmt, rs);
    }

    protected Connection getConnection() {
        return cnx;
    }

    protected DatabaseMetaData getMetaData() throws SQLException {
        return metadata;
    }

    protected String getDatabaseProductName() throws SQLException {
        return metadata.getDatabaseProductName();
    }

    protected String getBlobType() throws SQLException {
        ResultSet rs = metadata.getTypeInfo();
        while(rs.next()) {
            if(rs.getInt("DATA_TYPE") == Types.BLOB)
                return rs.getString("TYPE_NAME");
        }

        return "CHAR VARYING(4096)"; // No Blob type found. Try to use a big string.
    }

    protected int getMaxTableNameLength() throws SQLException {
        return metadata.getMaxTableNameLength();
    }

    public static void main(String[] args) throws SQLException {
        //beprops.setProperty("be.dbutils.jdbc.url", "jdbc:oracle:oci8:@bedb");
        //beprops.setProperty("be.dbutils.jdbc.url", "jdbc:postgresql:BEdb");
       
        final Logger logger = LogManagerFactory.getLogManager().getLogger(SQLConnection.class);
        logger.setLevel(Level.DEBUG);
        SQLConnection sqlcnx = new SQLConnection(logger);
        DatabaseMetaData metadata = sqlcnx.getMetaData();
        ResultSet rs;
        logger.log(Level.DEBUG, "Search String Escape = " + metadata.getSearchStringEscape());
        rs = metadata.getTables(null, "BE", "%", null);
        while(rs.next()) {
            String catalogname = rs.getString(1);
            String schemaname = rs.getString(2);
            String tablename = rs.getString(3);
            String tabletype = rs.getString(4);
            logger.log(Level.DEBUG, catalogname + " " + schemaname + " " + tablename + " " + tabletype);
            ResultSet colrs = metadata.getColumns(null, schemaname, tablename, "%");
            while(colrs.next()) {
                logger.log(Level.DEBUG,
                        "\t\t- " + colrs.getString(4) + " " + colrs.getString(6) + " " + colrs.getString(7));
            }
            colrs.next();
        }
        rs.close();

    }

    class DBResource {

        private Statement stmt;
        private ResultSet rs;

        public DBResource(Statement stmt, ResultSet rs) {
            this.stmt = stmt;
            this.rs = rs;
        }

        public void close() throws SQLException {
            closeResultSet();
            closeStatment();
        }

        public Statement getStatement() {
            return stmt;
        }

        public ResultSet getResultSet() {
            return rs;
        }

        public void closeStatment() throws SQLException {
            if(stmt != null)
                stmt.close();
        }

        public void closeResultSet() throws SQLException {
            if(rs != null)
                rs.close();
        }
    }
}
