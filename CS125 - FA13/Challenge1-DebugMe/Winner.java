import sun.net.www.content.audio.x_aiff;

//UIUC CS125 FALL 2013 MP. File: Winner.java, CS125 Project: Challenge1-DebugMe, Version: 2013-09-10T09:38:46-0500.901340225

/**
 * This program prints "a","b","c" depending on who has the highest score. The
 * given code may not be correct. Fix it and additional code to pass the unit
 * tests.
 * 
 * @see Winner-ReadMe.txt for details on how to complete this program.
 * @author dwoskin2
 * 
 */
public class Winner {
	public static void main(String[] args) {
		System.out.println("Enter three unique integer scores.");
		int a = TextIO.getlnInt(), b = TextIO.getlnInt(), c = TextIO.getlnInt();
		if (a > b && a > c) System.out.print("1st Place:a");
		if (b > a && b > c) System.out.print("1st Place:b");
		if (c > a && c > b) System.out.print("1st Place:c");
	}
}
