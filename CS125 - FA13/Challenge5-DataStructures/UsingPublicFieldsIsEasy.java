//UIUC CS125 FALL 2013 MP. File: UsingPublicFieldsIsEasy.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428

/**
 * Complete the class method 'analyze' that takes a SimplePublicPair object as an argument
 * and returns a new SimplePublicTriple object.
 * The SimplePublicTriple needs to set up as follows:
 * x = the minimum value of 'a' and 'b'
 * y = the maximum value of 'a' and 'b'
 * description:a*b=M 
 *   where a,b, and M are replaced with the numerical values of a, b and the multiplication of a and b.
 * Your code will create a SimplePublicTriple, initializes the three fields and return a reference to the SimplePublicTriple object.
 *
 *@author Dwoskin2
 */
public class UsingPublicFieldsIsEasy {
	public static SimplePublicTriple analyze(SimplePublicPair in) {
		int max = Math.max(in.a, in.b);
		int min = Math.min(in.a, in.b);
		int description = in.a*in.b;
		String product = in.a +  "*" +  in.b +  "=" + description;  
		SimplePublicTriple m = new SimplePublicTriple();
		m.x = min;
		m.y = max;
		m.description = product;
		return m;
	}
}
