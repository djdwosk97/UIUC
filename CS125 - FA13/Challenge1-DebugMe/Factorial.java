//UIUC CS125 FALL 2013 MP. File: Factorial.java, CS125 Project: Challenge1-DebugMe, Version: 2013-09-10T09:38:46-0500.901340225

/**
 * A program to calculate a factorial. The given code may contain errors. Fix the
 * given code and add additional code to calculate a factorial and pass the unit
 * tests. Hint sometimes an 'int' just 'aint big enough.
 * 
 * @see Factorial-ReadMe.txt for details on how to complete this program.
 * @author dwoskin2
 */
public class Factorial {
//	public static void main(String[] args) {
//		long max = 0l;
//		while (max < 1 || max > 20) {
//			System.out.println("Enter a number between 1 and 20 inclusive.");
//			max = TextIO.getlnInt();
//		}
//		long fact = max;
//		for(int i=1; i<fact; i++) max*=i;
//		System.out.print(fact + "! =" + max);
//	}
	public static void main(String[] args){
		double a = 2; 
		double b = 0;
		double power = b;
		double base = a; 
		if (b==0) System.out.print(base + " " + power + " " + 1 + "\n");
		else {
			while (b>1){
			     a *= base; 
			     b--; 
			}
			System.out.print(base + " " + power + " " + a + "\n");
		}
	}
}