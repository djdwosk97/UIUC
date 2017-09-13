//UIUC CS125 FALL 2013 MP. File: CallAStaticMethod.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428
/**
 * Prints out only lines that contain an email address Each printed line is
 * justified to right by prepending the text with '.' characters The minimum
 * width of the line including padding is 40 characters. See the test case for
 * example input and expected output.
 *
 *@author Dwoskin2
 */
class CallAStaticMethod {

	public static void main(String[] args) {
		while (!TextIO.eof()) {
			String line = TextIO.getln();
			if (isEmailAddress(line))
				System.out.println(createPadding(line) + line);
			}
			// Use ExampleClassMethods
			// 'isEmailAddress' and 'createPadding' to complete this method
		}
	public static String createPadding(String line){
		String padding = ".";
		for (int x = 40 - line.length() - 1; x>0; x--){
			padding = padding + ".";
		}
		return padding;
	}
	public static boolean isEmailAddress(String line){
		return line.indexOf("@") > 0;
	}
}
