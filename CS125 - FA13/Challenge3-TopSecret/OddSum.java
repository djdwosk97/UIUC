//UIUC CS125 FALL 2013 MP. File: OddSum.java, CS125 Project: Challenge3-TopSecret, Version: 2013-09-21T11:10:53-0500.511668125
/**
 * Prints sum of odd numbers in a specific format.
 * TODO: add your netid to the line below
 * @author dwoskin2
 */
public class OddSum { 
/**
Example output if user enters 10:
Max?
1 + 3 + 5 + 7 + 9 = 25
25 = 9 + 7 + 5 + 3 + 1

Example output if user enters 11:
Max?
1 + 3 + 5 + 7 + 9 + 11 = 36
36 = 11 + 9 + 7 + 5 + 3 + 1

 */
 public static void main(String[] args) { 
	 System.out.println("Max?");
	 int number = TextIO.getlnInt();
	 int permNumber = 1;
	 int sum = 0;
	 //array of length number.length (w/ the last address storing the sum) 
	 //store variables from below into array
	 //print contents of array from a[0]-a[array.lenth-1] (sum = 11 + 9 + 7 +....)
	 //print contents of array from a[array.lenght-1]-a[0] (1 + 3 + 5 + ... = sum
	 if (number % 2 ==0) number--;
	 while (permNumber <= number){
		if (permNumber == number){
			sum += permNumber;
	 		System.out.print(permNumber + " = " + sum);
	 		permNumber++;
	 	}
		else {
			System.out.print(permNumber + " + ");
	 		sum += permNumber;
	 		permNumber += 2;
	 	}
	 }
	 System.out.print("\n" + sum + " = ");
	 while (number > 0){
	 	if (number == 1){
	 		System.out.print(number);
	 		number--;
	 	}
	 	else {
			System.out.print(number + " + ");
	 		number -= 2;
	 	}
	 }
  } // end of main method
} // end of class 
