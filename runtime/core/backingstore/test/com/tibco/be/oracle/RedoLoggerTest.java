package com.tibco.be.oracle;

import junit.framework.TestCase;
import java.io.*;
import java.util.*;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class RedoLoggerTest extends TestCase {

	private MockRuleServiceProvider m_rsp = null;
	private Properties m_props = null;
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected void setUp() throws Exception {
		super.setUp();
		Properties props = new Properties();
		m_props = props;
		m_rsp = new MockRuleServiceProvider();
		m_rsp.setProperties(props);
		m_rsp.setLogger(new MockLogger());
		
	}

	public void testConstruction() {
		try {
			// test that if property is not set then no logger will be created
			RedoLogger.createRedoLogger("TestCache",m_rsp);
			RedoLogger logger = RedoLogger.getRedoLogger("TestCache");
			assert(logger == null);

			//test creation of file using default path
			String fileName = "MyRedoLog.sql";
			m_props.setProperty("TestCache.be.bstore.redoLog.filePath", fileName);
			RedoLogger.createRedoLogger("TestCache",m_rsp);
			logger = RedoLogger.getRedoLogger("TestCache");
			StringBuffer m_sbuffer = new StringBuffer("Line id 0");
			logger.log(m_sbuffer);
			assertTrue(logger.m_curFilePath != null);
			assertTrue(logger.m_curFilePath.equals(logger.DEFAULT_DIR_PATH + File.separator + fileName));
			File f = new File(logger.m_curFilePath);
			assertTrue(f.exists());
			
			//test creation of file using full path (Windows format)
			fileName = "test-dir\\someDir\\MyRedoLog1.sql";
			m_props.setProperty("TestCache1.be.bstore.redoLog.filePath", fileName);
			RedoLogger.createRedoLogger("TestCache1",m_rsp);
			logger = RedoLogger.getRedoLogger("TestCache1");
			m_sbuffer = new StringBuffer("Line id 1");
			logger.log(m_sbuffer);
			assertTrue(logger.m_curFilePath != null);
			assertTrue(logger.m_curFilePath.equals(fileName));
			f = new File(logger.m_curFilePath);
			assertTrue(f.exists());
			
			
			//test creation of file using full path (Unix format)
			fileName = "c:/temp/test-dir/someDir/MyRedoLog2.sql";
			m_props.setProperty("TestCache2.be.bstore.redoLog.filePath", fileName);
			RedoLogger.createRedoLogger("TestCache2",m_rsp);
			logger = RedoLogger.getRedoLogger("TestCache2");
			m_sbuffer = new StringBuffer("Line id 2");
			logger.log(m_sbuffer);
			assertTrue(logger.m_curFilePath != null);
			assertTrue(logger.m_curFilePath.equals(fileName));
			f = new File(logger.m_curFilePath);
			assertTrue(f.exists());

			//test creation of existing file - should create new file with timestamp appended
			fileName = "test-dir/someDir/MyRedoLog2.sql";
			m_props.setProperty("TestCache3.be.bstore.redoLog.filePath", fileName);
			RedoLogger.createRedoLogger("TestCache3",m_rsp);
			logger = RedoLogger.getRedoLogger("TestCache3");
			m_sbuffer = new StringBuffer("Line id 3");
			logger.log(m_sbuffer);
			// if we get here without exceptions then we are golden
			assertTrue(true);
			
			
		}
		catch(IOException e){
			fail("IOException: " + e.getMessage());			
		}
	}
	
	public void testLog() {
		try {
			
			// GOOD CASES:
			String fileName = "test-dir/MyRedoLog.sql";
			m_props.setProperty("TestLogCache.be.bstore.redoLog.filePath", fileName);
			RedoLogger.createRedoLogger("TestLogCache", m_rsp);
			RedoLogger logger = RedoLogger.getRedoLogger("TestLogCache");
			//Test one long arg, no end colon
			logger.log(new StringBuffer("One arg: ? no end colon"), 12345);
			
			//Test one string arg, no end colon
			logger.log(new StringBuffer("One arg: ? no end colon"), "stringArg");
			
			//Test two long args, no end colon
			logger.log(new StringBuffer("Two args: ? ? no end colon"), 1234, 5678);

			//Test one long arg, w/ end colon
			logger.log(new StringBuffer("One arg: ? end colon;"), 12345);
			
			//Test one string arg, w/ end colon
			logger.log(new StringBuffer("One arg: ? end colon;"), "stringArg");
			
			//Test two long args, w/ end colon
			logger.log(new StringBuffer("Two args: ? ? end colon;"), 1234, 5678);
						
			try {
				//Test one long arg, no replacement char ?
				logger.log(new StringBuffer("One arg: end colon;"), 12345);
				fail("No replacement char ? should have caused IOException");
			}
			catch( IOException e ) {}
			
			try {
				//Test one string arg, no replacement char ?
				logger.log(new StringBuffer("One arg: end colon;"), "stringArg");
				fail("No replacement char ? should have caused IOException");
			}
			catch( IOException e ) {}

			try {
				//Test two long args, no replacement char ?
				logger.log(new StringBuffer("Two args: end colon;"), 1234, 5678);
				fail("No replacement char ? should have caused IOException");
			}
			catch( IOException e ) {}
						
			logger.close();
			//FileInputStream fin = new FileInputStream(RedoLogger.m_curFilePath);
		}
		catch(IOException e){
			fail("IOException: " + e.getMessage());
		}

	}

}
