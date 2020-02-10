package com.tibco.cep.query.functions;

import com.tibco.cep.util.annotation.Optional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/*
* Author: Ashwin Jayaprakash / Date: 6/21/11 / Time: 11:13 AM
*/
public class SsQueryExecutionHelper {
	static final Logger logger  = LogManagerFactory.getLogManager().getLogger(SsQueryExecutionHelper.class);
    static String $md5Hash(Object object) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            String string = object.toString();
            byte buf[] = string.getBytes();
            byte[] md5 = md.digest(buf);

            BigInteger number = new BigInteger(1, md5);

            return number.toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sqlString
     * @param params
     * @param reuse     Does not {@link QueryFunctions#delete(String)} the query after execution.
     * @return
     */
    public static List execute(String sqlString, @Optional Map<String, Object> params, boolean reuse, long timeout, String querySessionName) {

        String uniqueId = System.nanoTime() + "." + (10000 * Math.random());
        String queryName;
        String statementName;

        if (reuse) {
            queryName = SsQueryExecutionHelper.class.getName() + "." + $md5Hash(sqlString);
            statementName = queryName + "." + uniqueId + ".SS";
        } else {
            queryName = SsQueryExecutionHelper.class.getName() + "." + $md5Hash(sqlString)
                    + "." + uniqueId;
            statementName = queryName + ".SS";
        }
        String resultSetName = statementName + ".RS";

        //Ideally this creation should be done once in the startup function.
        boolean created = QueryFunctions.createInternal(queryName, sqlString, true);

        //----------------

        StatementFunctions.open(queryName, statementName);

        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                StatementFunctions.setVar(statementName, entry.getKey(), entry.getValue());
            }
        }

        StatementFunctions.execute(statementName, resultSetName);
		TimeOutThread timeOutThread = null;
		AtomicBoolean executeClose = new AtomicBoolean(false);
		boolean timeoutSet = (timeout != -1);
		
		if (timeoutSet) {
			executeClose.set(true);
			timeOutThread = new TimeOutThread(statementName, resultSetName,
					timeout, Thread.currentThread().getName() + ".timeout",
					executeClose, querySessionName);
			timeOutThread.start();
		}
        //----------------

        Object resultList = QueryUtilFunctions.newList();

        try {
            int columnCount = ResultSetMetadataFunctions.getColumnCount(resultSetName);

            while (ResultSetFunctions.next(resultSetName)) {
                //Single column in the row.
                if (columnCount == 1) {
                    Object column = ResultSetFunctions.get(resultSetName, 0);

                    QueryUtilFunctions.addToList(resultList, column);
                }

                //----------------

                // Multiple columns in the row.
                // Delete query returns 2 columns - (id, ext_id) in each row.
                else {
                    Object[] columns = QueryUtilFunctions.newArray(columnCount);

                    for (int i = 0; i < columnCount; i = i + 1) {
                        columns[i] = ResultSetFunctions.get(resultSetName, i);
                    }

                    QueryUtilFunctions.addToList(resultList, columns);
                }
            }
        } catch (Exception ex) {
			if (timeoutSet && executeClose.get() == false) {
				logger.log(
						Level.DEBUG,
						"Expected exception because timeout thread closed the resultset and statement ",
						ex);
			} else {
				throw new RuntimeException(ex);
			}
		}finally {
			try {
				if (timeoutSet) {
					if (executeClose.compareAndSet(true, false)) {
						timeOutThread.cancel();
						ResultSetFunctions.close(resultSetName);
						StatementFunctions.close(statementName);
					} else {
						try {
							// Current thread will wait if timeout thread is
							// executing the close functions
							timeOutThread.join();
						} catch (InterruptedException e) {
							logger.log(Level.ERROR, e, e.getMessage());
							throw new RuntimeException(e);
						}
					}
				} else {
					ResultSetFunctions.close(resultSetName);
					StatementFunctions.close(statementName);
				}
            } finally {
                /*
                    Ideally this creation and deletion should be done once in the
                    startup and shutdown functions respectively.
                    */

                if (created && !reuse) {
                    QueryFunctions.delete(queryName);
                }
            }
        }

        return (List) resultList;
    }

	static class TimeOutThread extends Thread {
		private final String statementName;
		private final String resultSetName;
		private final long ttl;
		private final Logger logger = LogManagerFactory.getLogManager().getLogger(TimeOutThread.class);
		private final AtomicBoolean executeClose;
		private final String querySessionName;

		public TimeOutThread(final String statementName, final String resultSetName, final long ttl,
				final String threadName, final AtomicBoolean executeClose, final String querySessionName) {
			this.statementName = statementName;
			this.resultSetName = resultSetName;
			this.ttl = ttl;
			this.setName(threadName);
			this.executeClose = executeClose;
			this.querySessionName = querySessionName;
		}

		public void run() {
			logger.log(Level.DEBUG, "Timeleft to expire in seconds: " + ttl / 1000);
			try {
				Thread.sleep(ttl);
			} catch (InterruptedException e) {
				logger.log(Level.DEBUG, "Timeout thread interrupted");
			}
			
			RuleSession oldSession = null;

			if (executeClose.compareAndSet(true, false)) {
				logger.log(Level.INFO, "Timeout expired closing the resultset and statement");
				try {
					RuleSessionImpl queryRuleSession = QueryUtilFunctions.getQueryRuleSessionImpl(querySessionName);
					if (queryRuleSession == null) {
			            throw new IllegalArgumentException(
			                    "The query session name [" + querySessionName + "] is invalid." +
			                            " Valid names are " + QueryUtilFunctions.getQuerySessionNames());
			        }
					
					oldSession = RuleSessionManager.getCurrentRuleSession();
					
					RuleSessionManager.currentRuleSessions.set(queryRuleSession);
					
					ResultSetFunctions.close(resultSetName);
					StatementFunctions.close(statementName);
				} catch (Throwable t) {
					logger.log(Level.ERROR, "Exception in Timeout Thread: ", t);
				} finally {
					if (oldSession != null) RuleSessionManager.currentRuleSessions.set(oldSession);
				}
			}
		}

		public void cancel() {
			interrupt();
		}
	}
}