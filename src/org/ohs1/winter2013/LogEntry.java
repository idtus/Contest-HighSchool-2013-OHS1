package org.ohs1.winter2013;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Basic log entry class, we'll still need to implement methods here to export data into proper
 * HTML format for whatever sort of table we use
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.1, 31 Dec 2013
 */
public class LogEntry {
	/**
	 * The date format to be used in formatting the timestamp
	 */
	private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy: hh:mm:ssa: ");
	
	private final Expectation expectation;
	/**
	 * The actual message that was logged (as opposed to the expected message)
	 */
	private final String actualMessage;
	private final Date logDate;
	
	public LogEntry(String message, Expectation expectation) {
		this.actualMessage = message;
		this.expectation = expectation;
		this.logDate = new Date();
	}
	
	//Not really going to be used in the final product, but it looks like the example in the
	//problem description so it's good for testing
	public String toString() {
		//If the test passed, output the sort of result from the problem description
		if (actualMessage.equals(expectation.getExpectedLog())) {
			return dateFormat.format(logDate) +
				   expectation.getMethodName() +
				   " with input " + expectation.getParameterString() +
				   " PASSED with \"" + actualMessage + "\"";
		} else {
			return dateFormat.format(logDate) +
					   expectation.getMethodName() +
					   " with input " + expectation.getParameterString() +
					   " FAILED with \"" + actualMessage + "\"," +
					   " expected message was \"" + expectation.getExpectedLog() + "\"";
		}
	}
}
