//UIUC CS125 FALL 2013 MP. File: CipherBreaker.java, CS125 Project: Challenge3-TopSecret, Version: 2013-09-21T11:10:53-0500.511668125
/**
 * See CipherBreaker.txt for instructions.
 * TODO: add your netid to the line below
 * 
 * @author dwoskin2
 */
public class CipherBreaker {

	public static void main(String[] args) {
		char letter = 0 , alphabet = 'A';
		String source;
		int i = 0 , c = 0 , n = 0 , digits = 0 , punctuation = 0;
		char[] validCharacters = new char[43];
		int[] charCounter = new int[43];
		
		while (alphabet >= 'A' && alphabet <= 'Z'){
			validCharacters[i] = alphabet;
			alphabet++;
			i++;}
		validCharacters[i] = '"';	
			i++;
		validCharacters[i] = '-';	
			i++;	
		validCharacters[i] = '\'';	
			i++;
		validCharacters[i] = '.';	
			i++;
		validCharacters[i] = '!';	
			i++;
		validCharacters[i] = ',';	
			i++;	
		validCharacters[i] = ' ';	
			i++;
		validCharacters[i] = '0';	
			i++;
		validCharacters[i] = '1';	
			i++;
		validCharacters[i] = '2';	
			i++;
		validCharacters[i] = '3';	
			i++;
		validCharacters[i] = '4';	
			i++;
		validCharacters[i] = '5';	
			i++;
		validCharacters[i] = '6';	
			i++;
		validCharacters[i] = '7';	
			i++;
		validCharacters[i] = '8';	
			i++;
		validCharacters[i] = '9';	
			i++;
			
		System.out.println("Text?");
		source = TextIO.getln();
		System.out.println(source);
		source = source.toUpperCase();
			
		while (c < source.length() && n < 43){ //counts characters and prints them to countArray
			letter = source.charAt(c);
			if (letter == validCharacters[n]){
				charCounter[n]++;
				c++;
				n = 0;}
			else if (letter != validCharacters[n]){
				n++;
				if (n == 43){ //if letter != any validCharacters, c++, n=0
					c++;
					n = 0;}}}	
		
		while (n < 26){ //Prints letters : Count
			if (charCounter[n] != 0){
				System.out.println(validCharacters[n] + ":" + charCounter[n]);
				n++;}
			else if (charCounter[n] == 0)
				n++;}	
		
		while (n > 25 && n < 32){ //counts punctuation
			if (charCounter[n] != 0){
				punctuation = punctuation + charCounter[n];
				n++;}
			else if (charCounter[n] == 0)
				n++;}
		
		if (n == 32){
			n++;}
		
		while (n > 32 && n < 43){ //counts and prints digits
			if (charCounter[n] != 0){
				digits = digits + charCounter[n];
				n++;}
			else if (charCounter[n] == 0)
				n++;}
		
		if (digits > 0)
			System.out.println("DIGITS:" + digits);
		if (charCounter[32] > 0)
			System.out.println("SPACES:" + charCounter[32]);
		if (punctuation > 0)
			System.out.println("PUNCTUATION:" + punctuation);
	}
}