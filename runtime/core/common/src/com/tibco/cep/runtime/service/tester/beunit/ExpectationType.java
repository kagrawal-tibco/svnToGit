package com.tibco.cep.runtime.service.tester.beunit;

/**
 * Represents a set of expectations to be used with an {@link Expecter} for BE Unit Test suites<br>
 * @.category public-api
 * @since 5.4.0
 */
public enum ExpectationType {
	
	/**
	 * Expect that a rule has been executed during the test run
	 * @deprecated - Use RULE_EXECUTION instead
	 * @.category public-api
	 * @since 5.4.0
	 */
	RULE_EXECTION,
	/**
	 * Expect that a rule has been executed during the test run
	 * @.category public-api
	 * @since 5.4.0
	 */
	RULE_EXECUTION,
	/**
	 * Expect that a concept has been modified during the test run
	 * @.category public-api
	 * @since 5.4.0
	 */
	CONCEPT_MODIFICATION,
	/**
	 * Expect that a concept has been asserted during the test run.<br>
	 * Note that this does not include the concepts asserted by the test suite itself
	 * @.category public-api
	 * @since 5.4.0
	 */
	CONCEPT_ASSERTED,
	/**
	 * Expect that an event has been asserted during the test run.<br>
	 * Note that this does not include the events asserted by the test suite itself
	 * @.category public-api
	 * @since 5.4.0
	 */
	EVENT_ASSERTED, 
	/**
	 * Expect that a time event has been scheduled during the test run
	 * @.category public-api
	 * @since 5.4.0
	 */
	TIME_EVENT_SCHEDULED,
	/**
	 * Expect that an event has been retracted from working memory during the test run
	 * @.category public-api
	 * @since 5.4.0
	 */
	EVENT_RETRACTED,
	/**
	 * Expect that work has been scheduled through the scheduler functions<br>
	 * {@see ClusterFunctions}
	 * @.category public-api
	 * @since 5.4.0
	 */
	WORK_SCHEDULED,
	/**
	 * Expect that scheduled work has been removed through the scheduler functions<br>
	 * {@see ClusterFunctions}
	 * @.category public-api
	 * @since 5.4.0
	 */
	WORK_REMOVED,
	/**
	 * Expect that an event has been sent on a destination.<br>
	 * Note that this does not require that the event is sent <i>successfully</i>, only that an attempt to send the event has been made
	 * @.category public-api
	 * @since 5.4.0
	 */
	EVENT_SENT

}
