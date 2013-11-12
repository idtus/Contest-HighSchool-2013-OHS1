package org.ohs1.winter2013;

import java.text.DateFormat;

/**
 * This dummy class is an example of javadoc commenting. The idea is that we'd
 * just use this space for the description of the class below this. @author is
 * required and so is @version. For us, it'll be fine if you just increment the
 * version number by 1 every time you update it and also put the date in that
 * format.
 * <p>
 * Note that this goes right before the class declaration after the imports.
 * Also, remember to press ctrl+shift+f every so often. Another key combo that
 * is nice is alt+shift+j when you have a chunk of code (such as a method or a
 * field) highlighted. It'll auto-generate a segment of the javadoc comment for
 * you.
 * <p>
 * All of this code is technically in html format so you have to use breaks like
 * the one above to denote paragraphs and such.
 * <p>
 * To generate the javadoc, hover over project in the top bar and click generate
 * javadoc. If that doesn't work, you have to right click the project in the
 * package explorer on the left and click properties. Then click on javadoc
 * location. Then click browse and find
 * C:/users/YOURUSERNAME/git/IDT-OHS1-Winter-2013/IDT-OHS1-Winter-2013/doc/ and
 * click OK. Then back to project in the top bar and click generate javadoc.
 * You'll probably need to set up the javadoc command. Click Configure and
 * browse to C:\Program Files\Java\jdk1.7.0_40\bin\javadoc.exe the jdk version
 * should be whatever latest one you have. Make sure to then pick javadoc.exe
 * Then click finish. It should work. Then you can press shift+F2 and the
 * javadoc will pop up. Or go to navigate on the top bar and click open attached
 * javadoc.
 * <p>
 * Honestly we don't have to worry about flawless javadocs until the very end
 * but a commenting system should be in place to at least let each other know
 * what's going on. Keeping the @version up to date is probably the most
 * important part. When you open the file to edit, increment the version and the
 * date. Simple as that. And maybe write a quick description of what each method
 * does so I know and you don't forget. Don't worry about the @whatever tags
 * until the very end.
 * 
 * @author Kostyantyn Proskuryakov
 * @version 0.1, 7 Nov 2013
 */
public class Dummy {

	/**
	 * This here is the main method and the method summary will only show this
	 * first sentence. We have to describe every method that appears in our
	 * program that isn't obvious by the name. The @param is necessary with all
	 * of the parameters in there. It does not have a return so you don't need
	 * the @return tag.
	 * 
	 * @param args
	 *            Here's the description of the args parameter because it maybe
	 *            needs one
	 */
	public static void main(String[] args) {

		System.out.println("lol");

	}

	// The format of the date that is used throughout the class. Comments on
	// fields (like this one) do not show up on javadocs but if it isn't obvious
	// what it does, then you should write one.
	// Also these line comments don't show up on javadocs at all.
	private DateFormat format;

	/**
	 * Class constructor doing absolutely nothing.
	 * 
	 * @param df
	 *            Format of the date that will be used for who knows what
	 * @see DateFormat
	 */
	public Dummy(DateFormat df) {
		format = df;
	}

	/**
	 * @return The DateFormat that was passed initially to this object
	 */
	public DateFormat getDateFormat() {
		return format;
	}

	/**
	 * Adds the first number to the second number if the first number is greater
	 * than or equal to the second number. Make sure to specify any bounds that
	 * occur within the method.
	 * 
	 * @param a
	 *            The first number. Must be greater than or equal to
	 *            <code>b</code>
	 * @param b
	 *            The second number. Must be less than or equal to
	 *            <code>a</code>
	 * @return <code>a</code> plus <code>b</code> if <code>a</code> is greater
	 *         than or equal to <code>b</code>
	 * @throws ArithmeticException
	 *             If <code>a</code> is less than <code>b</code>
	 */
	public int boundedMethod(int a, int b) {
		if (a < b) {
			throw new ArithmeticException();
		} else {
			return a + b;
		}
	}
}
