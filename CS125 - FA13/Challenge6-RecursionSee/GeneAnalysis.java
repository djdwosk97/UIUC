//UIUC CS125 FALL 2013 MP. File: GeneAnalysis.java, CS125 Project: Challenge6-RecursionSee, Version: 2013-11-05T16:32:01-0600.619512029
/**
 * @author dwoskin2
 *
 */
public class GeneAnalysis
{
	/** Wrapper method. Returns the length of the longest 
	 * common subsequence
	 */
	public static int score(String gene1, String gene2){
		char[] one = gene1.toCharArray();
		char[] two = gene2.toCharArray();
	
		return score(one, two, one.length -1, two.length -1);
		//throw new IllegalArgumentException("Not Yet Implemented");
		// Hint: Use toCharArray() to convert each string to a char array.
		 // call your recursive implementation here with
		// the genes as char arrays, starting at the end of each gene.
	
	}

	/** Implements longest common subsequence recursive search
The recursive case is defined as
					S(i-1, j)
S(i,j) = max {		S(i,j-1)
					S(i-1,j-1)
					S(i-1,j-1) +1 if gene1[i] = gene2[j]

NB  0<=i < gene1.length
    0<=j < gene2.length

You need to figure out the base case.
	 * */
//		define a private recursive Class method 'score' that 
//		returns an integer the score.
//		The method should take four parameters- 
//		two char arrays and two integers (gene1,gene2,i,j)
//		i and j are the indices into gene1 and gene2 respectively.
	private static int score(char gene1[], char gene2[], int i, int j){
		int scoreA = 0, scoreB = 0, a = 0, b = 0, c = 0, d = 0;
		if (i < 0 || j < 0) return 0;
		if (gene1[i] == gene2[j])
			return score(gene1, gene2, i - 1, j - 1) + 1;
		else {
		b = score(gene1, gene2, i, j-1); 
		c = score(gene1, gene2, i-1, j);
		d = score(gene1, gene2, i-1, j-1);
		scoreA = Math.max(b,c);
		scoreB = Math.max(scoreA,d);
		return Math.max(scoreA, scoreB); 
		}
	}
}
// Use local variables to store a recursive result so that you  do not need to calculate it again.

// Do not use a loops.
