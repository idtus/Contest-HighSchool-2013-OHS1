package org.ohs1.winter2013;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The main tester class (very WIP, we may want to changed the name also)
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.2, 31 Dec 2013
 */
public enum BuiltInTester {
	INSTANCE;
	
	// Whether testing is enabled (global control of testing)
	private boolean enabled;
	
	// Used as the file directory for all output logs
	private String programName;
	
	// Used as the template name for all output logs, will appear as outputFileName000.html where 000 are incremented with each new log file
	private String outputFileName;

	// The expectations currently being expected
	private List<Expectation> expectations;
	
	// A queue storing the log entries in the order that they were created
	private Queue<LogEntry> logEntries;
	
	
	private BuiltInTester() {
		this.expectations = new ArrayList<>();
		this.logEntries = new LinkedList<>();
	}
	
	/**
	 * Enables the testing system
	 */
	public void enable(String programName, String outputFileName) {
		enabled = true;
		this.programName = programName;
		this.outputFileName = outputFileName;
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
	public void expecting(String logMessage, Object... parameters) {
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
			expectations.add(new Expectation(methodName, logMessage, params));
		}
	}
	
	/**
	 * Right now this does nothing but clear the list of expectations for testing purposes
	 * 
	 * @param message
	 */
	public void log(String message) {
		if (enabled) {
			//Evaluate the correctness of all expectations and add the appropriate log entries
			for (Expectation e : expectations) {
				logEntries.add(new LogEntry(message, e));
			}
			
			//Clear the list of expectations
			expectations.clear();
		}
	}
	
	/**
	 * This will eventually be the method that outputs the HTML formatted log, but for now it
	 * just prints out a normal, boring summary of the log entries
	 */
	public void outputLog() {
		if (enabled) {
			String output = "*****Testing results for " + programName + "*****\n\n";
			
			//Add all log entries, in order, to the output string
			while (!logEntries.isEmpty())
				output += logEntries.poll() + "\n";
			
			System.out.println(output);
			
			try {
				File f = createFile();
				System.out.println(f.getAbsolutePath());
				//Somewhere in here we need to copy the files that I've added myself to the folder
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				writeHeader(bw);
				writeBody(bw);
				writeFooter(bw);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Only for testing purposes, returns all expectations logged in the system
	 * @return
	 */
	String testingString() {
		if (enabled) {
			String s = "";
			for (Expectation e : expectations)
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
	private boolean parameterEquals(Object param1, Object param2) {
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
	
	/**
	 * Creates the html file to store log data.
	 * 
	 * @return The created empty log file in File object format
	 * @throws IOException
	 */
	private File createFile() throws IOException{
		File f;
		String path = programName + File.separator + outputFileName;
		DecimalFormat format = new DecimalFormat("000");
		int fileNum = 0;
		do {
			f = new File(path.substring(0, path.length() - 5) + format.format(fileNum) + path.substring(path.length() - 5));
			fileNum++;
		} while (f.exists());
		f.getParentFile().mkdirs();
		f.createNewFile();
		return f;
	}
	
	private void writeHeader(BufferedWriter bw) throws IOException {
		//First part of file
		bw.write("<html>\n<head>\n");
		//Stylesheet
		bw.write("<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\" />\n");
		//External scripts
		bw.write("<script type=\"text/javascript\" src=\"jquery-latest.js\"></script>\n");
		bw.write("<script type=\"text/javascript\" src=\"jquery.tablesorter.min.js\"></script>\n");
		//Script for page load
		bw.write("<script type=\"text/javascript\">\n");
		bw.write("window.onload = function() {\n");
		bw.write("$(document).ready(function()\n"); 
    	bw.write("{$(\"#all\").tablesorter();$(\"#failed\").tablesorter();}\n"); 
    	bw.write(");}\n");
    	bw.write("</script>\n");
    	bw.write("</head>\n");
	}
	
	private void writeBody(BufferedWriter bw) throws IOException {
		
	}
	
	private void writeFooter(BufferedWriter bw) throws IOException {
		//WIP
		bw.write("</html>");
	}
}
