package com.tibco.cep.modules.db.service.jdbcquery;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.cep.modules.db.service.JDBCConnectionPoolManager;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Mar 27, 2005
 * Time: 11:00:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractJDBCChannel {

    //static JDBCConnectionPoolManager connPoolMgr = (JDBCConnectionPoolManager) EngineContext.getInstance().findComponent(ComponentNames.JDBC_CNX_MANAGER_COMPONENT_NAME, ComponentNames.JDBC_CNX_MANAGER_COMPONENT_VERSION);
    static com.tibco.cep.modules.db.service.JDBCConnectionPoolManager connPoolMgr = new JDBCConnectionPoolManager(null);

    public static Connection getConnection(String jdbcResourceName) throws SQLException {
        return connPoolMgr.getJDBCConnection(jdbcResourceName);
    }

    public static PreparedStatement getStatement(Connection cnx, String sql) throws SQLException {
        PreparedStatement stmt = cnx.prepareStatement(sql);
        return stmt;
    }

    public static PreparedStatement getStatement(String jdbcResourceName, String sql) throws SQLException {
        Connection cnx = connPoolMgr.getJDBCConnection(jdbcResourceName);
        PreparedStatement stmt = cnx.prepareStatement(sql);
        return stmt;
    }

    public static ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    public static Object getFirstResult(ResultSet rs) throws SQLException {
        Object ret = null;
        if(rs.next()) {
            ret = rs.getObject(1);
        }
        return ret;
    }

    public static void executeUpdate(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
    }

    public static void releaseConnection(String jdbcResourceName, Connection cnx) {
        connPoolMgr.relaseConnection(jdbcResourceName, cnx);
    }

    public static Calendar getDate(Date date) {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(tz);
        cal.setTime(date);
        return cal;
    }
}
