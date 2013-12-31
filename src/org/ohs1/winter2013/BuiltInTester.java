package org.ohs1.winter2013;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main tester class (very WIP, we may want to changed the name also)
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.1, 30 Dec 2013
 */
public class BuiltInTester {
	/**
	 * Whether testing is enabled (global control of testing)
	 */
	private static boolean enabled = false;
	/**
	 * The expectations currently being expected
	 */
	private List<Expectation> expectations;
	
	
	private BuiltInTester() {
		expectations = new ArrayList<>();
	}
	
	//Singleton design
	private static class ClassHolder {
		public static final BuiltInTester instance = new BuiltInTester();
	}
	
	/**
	 * This is currently private because I'm not sure when clients would actually need
	 * the instance directly, but it can be changed
	 * 
	 * @return the BuiltInTester instance
	 */
	private static BuiltInTester getInstance() {
		return ClassHolder.instance;
	}
	
	/**
	 * Enables the testing system
	 */
	public static void enable() {
		enabled = true;
	}
	
	/**
	 * Logs an expectation. I needed to support multiple conditions (some of the classes we have to test
	 * contain methods with more than one parameter), so I used varargs and the condition that
	 * the parameters must be written in pairs. This makes the syntax essentially the same as that given
	 * in the example in the problem description, except the log message comes first (which makes more
	 * sense, actually).
	 * 
	 * @param logMessage the message to be expecting
	 * @param parameters the conditions, written in pairs of parameter then value, for the expectation
	 */
	public static void expecting(String logMessage, Object... parameters) {
		//Make sure testing is enabled
		if (enabled) {
			if (parameters.length % 2 != 0)
				throw new IllegalArgumentException("Parameters must be written in pairs");
			
			//Stops if the expectation should not be logged (if some parameter condition is not met)
			//Checks parameters in pairs
			for (int i = 0; i < parameters.length - 1; i += 2) {
				//If parameters are not equal, exit the method before adding the expectation
				if (!parameterEquals(parameters[i], parameters[i+1]))
					return;
			}
			
			//Get method name from stack trace
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			
			//Construct a list of the parameter values
			List<Object> params = new ArrayList<>();
			for (int i = 0; i < parameters.length; i += 2)
				params.add(parameters[i]);
			
			//Since the parameters have their desired values, log the expectation
			getInstance().expectations.add(new Expectation(methodName, logMessage, params));
		}
	}
	
	/**
	 * Right now this does nothing but clear the list of expectations for testing purposes
	 * @param message
	 */
	public static void log(String message) {
		if (enabled) {
			getInstance().expectations.clear();
		}
	}
	
	/**
	 * Only for testing purposes, prints out all expectations logged in the system
	 * @return
	 */
	static String testingString() {
		if (enabled) {
			String s = "";
			for (Expectation e : getInstance().expectations)
				s += e.toString() + "\n";
			return s;
		} else {
			return "Not enabled\n";
		}
	}
	
	/**
	 * Checks if the two parameters are equal, taking into account the possibility of being an array
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	private static boolean parameterEquals(Object param1, Object param2) {
		if (param1 == null || param2 == null)
			return false;
		
		//Return false if class types are not equal
		if (!param1.getClass().equals(param2.getClass()))
			return false;
		
		//Take into account arrays (only does int[] for now)
		if (param1 instanceof int[])
			return Arrays.equals((int[])param1, (int[])param2);
		else
			return param1.equals(param2);
	}
}
