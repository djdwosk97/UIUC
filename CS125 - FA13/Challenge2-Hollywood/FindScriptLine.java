//UIUC CS125 FALL 2013 MP. File: FindScriptLine.java, CS125 Project: Challenge2-Hollywood, Version: 2013-09-17T09:01:56-0500.998484625
/**
 * A program to search for specific lines and print their line number.
 * See FindScriptLine.txt for more details
 * TODO: add your netid to the line below
 * @author dwoskin2
 */
public class FindScriptLine {
	public static void main(String[] args) {
		System.out.println("Please enter the word(s) to search for");
		String line = TextIO.getln();
		String linelc = line.toLowerCase();
		int linenumber = 0;
		System.out.println("Searching for " + "'" + line + "'");
		TextIO.readFile("thematrix.txt");
		while (!TextIO.eof()) {
			String text = TextIO.getln(); 
			String textlc = text.toLowerCase();
			linenumber++;
			if (textlc.indexOf(linelc) != -1) {
				System.out.println(linenumber +" - " + text.trim());
			}
		}
		System.out.println("Done Searching for " + "'" + line + "'");
	}
}
 