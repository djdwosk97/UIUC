//UIUC CS125 FALL 2013 MP. File: CaesarCipher.java, CS125 Project: Challenge3-TopSecret, Version: 2013-09-21T11:10:53-0500.511668125
/**
 * A program to search for to encrypt and decrypt lines of text. See
 * CaesarCipher.txt
 * Hints: line.charAt( int ) is useful.
 * You'll need loops, and conditions and variables
 * You'll need to read the Test cases to understand how your program should work.
 * Good Programming Hints: "DRY: Don't Repeat Yourself"
 * Try to make your program as short as possible.
 * TODO: add your netid to the line below
 * @author dwoskin2
 */
public class CaesarCipher {

	public static void main(String[] strings) {
		//TODO: Delete the following line and write your implementation here- 
		int shift = 0;
		String source = null, sourceCapital = null;
		int n, shiftedletter, currentletter;
		boolean done = true; //while there is text to encrypt
		boolean positionShift = false;
		boolean positionShiftDecode = false;
					
		while (shift == 0 || shift > 25 || shift < -25) {
			System.out.println("Please enter the shift value (between -25..-1 and 1..25)");
			shift = TextIO.getlnInt();
			if (shift == 999){
				System.out.print("Using position shift");
				positionShift = true;
				done = false;
			}
			else if (shift == -999){
				System.out.print("Using position shift");
				positionShiftDecode = true;
				done = false;
			}
			else if (shift == 0 || shift > 25 || shift < -25)
				System.out.println(shift + " is not a valid shift value.");
			else { 
					System.out.print("Using shift value of " + shift);
					done = false;
			}
		}
		
		while (!done){
			currentletter = 0;
			System.out.println("\nPlease enter the source text (empty line to quit)");
			source = TextIO.getln();
				if (source.length() == 0){ // empty line to quit
					System.out.println("Bye.");
					done = true;
				}
				else {
					System.out.println("Source   :" + source);
					n = source.length();
					System.out.print("Processed:");
					sourceCapital = source.toUpperCase();
					if (positionShift){
						shift = 0;
						currentletter = 0;
						while (currentletter < n) {
							if (sourceCapital.charAt(currentletter) > 91 || sourceCapital.charAt(currentletter) < 65)
								shiftedletter = source.charAt(currentletter);
							else //if a letter
								shiftedletter = (((((sourceCapital.charAt(currentletter)) - 65) + shift) % 26) +65); 
							System.out.print((char)shiftedletter);
							currentletter++;
							shift++;
						}
					}
					else if (positionShiftDecode){ //decodeshift
						shift = 0; 
						currentletter = 0;
						while (currentletter < n) {
							if (sourceCapital.charAt(currentletter) > 91 || sourceCapital.charAt(currentletter) < 65)
								shiftedletter = source.charAt(currentletter);
							else //if a letter
								shiftedletter = ((((((sourceCapital.charAt(currentletter)) - 65) - shift) % 26) +65));
							System.out.print((char)shiftedletter);
							currentletter++;
							shift++;
						}
					}
					else if (!positionShift) {
						while (currentletter < n) {
							if (sourceCapital.charAt(currentletter) > 91 || sourceCapital.charAt(currentletter) < 65)
								shiftedletter = source.charAt(currentletter);
							else {
								shiftedletter = ((((((sourceCapital.charAt(currentletter)) - 65) + shift) % 26) +65));
								System.out.print((char)shiftedletter);
								currentletter++;
							}
						}
					}
				} 
			}
		}
	}	
