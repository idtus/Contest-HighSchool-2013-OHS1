package org.ohs1.winter2013;

import java.util.Arrays;

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
 * @author Kostyantyn Proskuryakov, Ian Johnson
 * @version 0.3, 31 Dec 2013
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
		//Method call before enabling
		testMethod(4, "No", new int[4]);
		
		//Enable testing
		BuiltInTester bit = BuiltInTester.INSTANCE;
		bit.enable("DummyProgram", "DummyLog.html");
		
		//Some tests:
		//Shouldn't log anything
		testMethod(2, "fff", new int[1]);
		//Should log the first two tests, but they both fail
		testMethod(1, "Hello world", new int[]{1, 2, 3});
		
		//Should log the last test
		testMethod(42, "Goodbye", new int[2]);
		//Should log the first and last tests, but only the latter passes
		testMethod(1, "Goodbye", new int[3]);
		
		//Output log
		bit.outputLog();
	}
	
	//Method for testing stuff
	private static void testMethod(int a, String b, int[] c) {
		BuiltInTester bit = BuiltInTester.INSTANCE;
		
		bit.expecting("Message 1", a, 1);
		bit.expecting("Message 1", a, 1, b, "Hello world", c, new int[]{1, 2, 3});
		bit.expecting("Message 2", b, "Goodbye");
		
		//Print out expectations
		System.out.println("Parameters: a = " + a + " b = " + b + " c = " + Arrays.toString(c));
		System.out.println(bit.testingString());
		
		//Bug: Hello world spelled incorrectly
		if (a == 1 && b.equals("Hello worllld"))
			bit.log("Message 1");
		else if (b.equals("Goodbye"))
			bit.log("Message 2");
		
		bit.log("fail");
	}
}
