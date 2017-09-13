//UIUC CS125 FALL 2013 MP. File: MyScriptPrinter.java, CS125 Project: Challenge2-Hollywood, Version: 2013-09-17T09:01:56-0500.998484625
/**
 * A program to print one actor's lines. 
 * See ScriptPrinter.txt for more information.
 * TODO: add your netid to the line below
 * @author dwoskin2
 */
public class MyScriptPrinter {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean output = false; //Set to true when we find the desired character
		String name=""; // Only print lines for this character
	
		TextIO.putln("Which character's lines would you like? (NEO,MORPHEUS,ORACLE)");
		name = TextIO.getln();
		name = name.toUpperCase();
					
		System.out.print(name +"'s lines:");
		TextIO.readFile("thematrix.txt"); // stop reading from the keyboard- use the script
		
		output = false; // initially don't print anything

		// This loop will read one line at a time from the script until it
		// reaches the end of the file and then TextIO.eof() will return true.
		// eof means end-of-file
		while (!TextIO.eof()) {
			String line = TextIO.getln(); // Read the next line
			if (line.indexOf(name) != -1){
				output = true;
				line = line.trim();
				System.out.print("\n" + line + ":"); //all subsequent prints
				while (output){
					String linea = TextIO.getln();
					linea = linea.trim();
					System.out.print("\"");
					System.out.print(linea);
					if (linea.length() == 0){
						break;}
				}
			}
		}
				
			//TODO: If it's a blank line set 'output' to false			
			//TODO: Correct the output format (see ScriptPrinter.txt example output)
			//TODO: Re-order the three if statements so the output is correct
		System.out.println("\n---");
		}
}
		//TODO: Print 3 dashes here to indicate processing is complete