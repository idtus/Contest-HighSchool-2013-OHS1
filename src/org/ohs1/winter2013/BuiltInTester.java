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
 * The <code>BuiltInTester</code> library suite is intended for testing the
 * logic of methods from inside the code and printing out meaningful and
 * readable output. If not enabled, it leaves as little of a footprint as
 * possible by using a sudo-singleton structure.
 * <p>
 * The <code>BuiltInTester</code> class is a container for the
 * <code>BuiltInTester</code> instance. The methods of the instance are all
 * called through static methods with similar names so that the instance of the
 * <code>BuiltInTester</code> class is never used if the testing system is not
 * enabled.
 * <p>
 * The <code>BuiltInTester</code> system is enabled by calling the
 * {@link #enable(String,String)} and passing to it the name of the program and
 * the name of the output file as parameters. The output file name will be the
 * parameter and three numbers appended to it such that they are sequential. The
 * program name is used as the directory in which the files are stored.
 * <p>
 * 
 * 
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 1.4, 30 Jan 2013
 */
public class BuiltInTester {

	// Singleton instance that is initialized only if {@link
	// #enable(String,String)} is called
	private static BuiltInTester instance;

	// Used as the file directory for all output logs
	private String programName;

	// Used as the template name for all output logs, will appear as
	// outputFileName000.html where 000 are incremented with each new log file
	private String outputFileName;

	// The expectations currently being expected
	private List<Expectation> expectations;

	// A queue storing the log entries in the order that they were created
	private Queue<LogEntry> logEntries;

	/**
	 * Enables the BuiltInTester API. All BuiltInTester methods after the enable point do their tasks. 
	 * 
	 * @param programName
	 *            Directory name of the output files
	 * @param outputFileName
	 *            Name of the output files
	 */
	public static void enable(String programName, String outputFileName) {
		instance = new BuiltInTester();
		instance.programName = programName;
		instance.outputFileName = outputFileName;
	}

	// Initializes the lists that store the logs and expected values of methods
	private BuiltInTester() {
		this.expectations = new ArrayList<>();
		this.logEntries = new LinkedList<>();
	}

	/**
	 * Sets the expectation that if a certain set of parameters are the wanted values, the log message specified will be the one that is obtained from {@link #log(String))}.
	 * 
	 * @param logMessage
	 *            The expected message given the parameters.
	 * @param parameters
	 * 			  A reference to the parameter itself followed by the value of the parameter to expect the log message if the reference equals the value.
	 */
	public static void expecting(String logMessage, Object... parameters) {
		if (instance != null) {
			instance.expectingInner(logMessage, parameters);
		}
	}

	// Called by the expecting method and is passed the same parameters only if
	// enabled
	private void expectingInner(String logMessage, Object... parameters) {
		if (parameters.length % 2 != 0)
			throw new IllegalArgumentException(
					"Parameters must be written in pairs");

		// Stops if the expectation should not be logged (if some parameter
		// condition is not met)
		// Checks parameters in pairs
		for (int i = 0; i < parameters.length - 1; i += 2) {
			// If parameters are not equal, exit the method before adding the
			// expectation
			if (!parameterEquals(parameters[i], parameters[i + 1]))
				return;
		}

		// Get method name from stack trace
		String methodName = Thread.currentThread().getStackTrace()[2]
				.getMethodName();

		// Construct a list of the parameter values
		List<Object> params = new ArrayList<>();
		for (int i = 0; i < parameters.length; i += 2) {
			params.add(parameters[i]);
		}

		// Since the parameters have their desired values, log the expectation
		expectations.add(new Expectation(methodName, logMessage, params));
	}

	/**
	 * Should be used right before any return statement. The logs will show the
	 * <code>message</code> when the method returns.
	 * 
	 * @param message
	 *            The message that gets logged upon return by that specific return statement.
	 */
	public static void log(String message) {
		if (instance != null) {
			instance.logInner(message);
		}
	}

	// Called by the log method only if enabled
	private void logInner(String message) {
		// Evaluate the correctness of all expectations and add the
		// appropriate log entries
		for (Expectation e : expectations) {
			logEntries.add(new LogEntry(message, e));
		}

		// Clear the list of expectations
		expectations.clear();
	}

	/**
	 * Should be called at the very end of execution. Creates the output log as
	 * an html page under the project directory in the folder designated by
	 * <code>programName</code> and the filename designated by
	 * <code>outputFileName</code> with three digits at the end that increment
	 * by 1 with every new output log created. File directory structure is
	 * cross-platform.
	 * <p>
	 * Example output log files: <br>
	 * Test_Program\output000.html <br>
	 * Test_Program\output001.html <br>
	 * Test_Program\output002.html
	 */
	public static void outputLog() {
		if (instance != null) {
			instance.outputLogInner();
		}
	}

	// Called by the outputLog method only if enabled
	private void outputLogInner() {
		try {
			File f = createFile();
			System.out.println(f.getAbsolutePath());
			// Somewhere in here we need to copy the files that I've added
			// myself to the folder
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			writeHeader(bw);
			writeBody(bw);
			writeFooter(bw);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Checks whether two parameters are equivalent to each other
	private boolean parameterEquals(Object param1, Object param2) {
		if (param1 == null || param2 == null)
			return false;

		// Return false if class types are not equal
		if (!param1.getClass().equals(param2.getClass()))
			return false;

		// Take into account arrays (only does int[] for now)
		if (param1 instanceof int[])
			return Arrays.equals((int[]) param1, (int[]) param2);
		else
			return param1.equals(param2);
	}

	// Creates a log file based on programName and outputFileName and appends 3
	// digits so that each log is unique
	private File createFile() throws IOException {
		File f;
		String path = programName + File.separator + outputFileName;
		DecimalFormat format = new DecimalFormat("000");
		int fileNum = 0;
		do {
			f = new File(path.substring(0, path.length() - 5)
					+ format.format(fileNum)
					+ path.substring(path.length() - 5));
			fileNum++;
		} while (f.exists());
		f.getParentFile().mkdirs();
		f.createNewFile();
		return f;
	}

	private void writeHeader(BufferedWriter bw) throws IOException {
		// First part of file
		bw.write("<html>\n<head>\n");
		// Stylesheet
		bw.write("<link rel=\"stylesheet\" href=\"../scripts/style.css\" type=\"text/css\" />\n");
		// External scripts
		bw.write("<script type=\"text/javascript\" src=\"../scripts/jquery-latest.js\"></script>\n");
		bw.write("<script type=\"text/javascript\" src=\"../scripts/jquery.tablesorter.min.js\"></script>\n");
		// Script for page load
		bw.write("<script type=\"text/javascript\">\n");
		bw.write("window.onload = function() {\n");
		bw.write("$(document).ready(function()\n");
		bw.write("{$(\"#all\").tablesorter();$(\"#failed\").tablesorter();}\n");
		bw.write(");}\n");
		bw.write("</script>\n");
		// End of header
		bw.write("</head>\n");
	}

	// Date, method name, input, expected, received, pass/fail
	private void writeBody(BufferedWriter bw) throws IOException {
		// Body tag
		bw.write("<body>\n");
		// Header for file (program name)
		bw.write("<h1>Test results for " + programName + "</h1>\n");

		// Header for all tests
		bw.write("<h2>All tests</h2>\n");
		// Beginning of table
		bw.write("<table id=\"all\" class=\"tablesorter\">\n");
		// Table header with names of columns
		bw.write("<thead>\n<tr>\n");
		for (String header : new String[] { "Date", "Method name", "Input",
				"Expected log", "Received log", "Pass/fail" })
			bw.write("    <th>" + header + "</th>\n");
		bw.write("</tr>\n</thead>\n");

		// Body of first (all entries) table
		// This is a queue of all the failed log entries so they can be added to
		// the other table as well
		Queue<LogEntry> failedEntries = new LinkedList<>();

		bw.write("<tbody>\n");
		while (!logEntries.isEmpty()) {
			LogEntry entry = logEntries.poll();
			// Add entry to failed queue if it failed
			if (!entry.didPass())
				failedEntries.add(entry);

			bw.write("    " + entry.toTRString(false) + "\n");
		}
		bw.write("</tbody>\n</table>\n");

		// Header for second table (failed tests)
		bw.write("<h2>Failed tests</h2>\n");
		// Beginning of table
		bw.write("<table id=\"failed\" class=\"tablesorter\">\n");
		// Table header with names of columns
		bw.write("<thead>\n<tr>\n");
		for (String header : new String[] { "Date", "Method name", "Input",
				"Expected log", "Received log" })
			bw.write("    <th>" + header + "</th>\n");
		bw.write("</tr>\n</thead>\n");
		// Body of second (failed entries) table
		bw.write("<tbody>\n");
		while (!failedEntries.isEmpty())
			bw.write("    " + failedEntries.poll().toTRString(true) + "\n");
		bw.write("</tbody>\n</table>\n");

		// End of body
		bw.write("</body>\n");
	}

	private void writeFooter(BufferedWriter bw) throws IOException {
		// This is sort of a pathetic method
		bw.write("</html>");
	}
}
