//UIUC CS125 FALL 2013 MP. File: MolecularSort.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2013-11-12T09:42:21-0600.264411210
/**
 * 
 * @author Dwoskin2
 *
 */
public class MolecularSort {

	/** Sorts each xyz coordinate using it's Z value (coord[i][2] <= coord[j][2] for i<j). */
	static void sortCoordsByZ(double[][] coords) {
		// TODO: Implement this wrapper method.
		//All the work is performed by recursiveSort
		recursiveSort(coords, 0, coords.length-1);
	}

	/**
	 * recursively sorts coordinates entries by their z value. Entries between
	 * lo and hi inclusive are considered.
	 */
	static void recursiveSort(double[][] coords, int lo, int hi) {
		if (hi == lo){return;}
		int temp =findIndexOfZMinimum(coords, lo, hi);
		swap(coords, temp,lo );
		recursiveSort(coords, lo + 1, hi);
		// TODO: write the four lines of a recursive selection sort here.
	}

	/**
	 * returns the index of the entry with the lowest Z value. Entries between
	 * lo and hi inclusive are considered.
	 */
	static int findIndexOfZMinimum(double[][] coords, int lo, int hi) {
		if (lo == hi){return lo;}
		if (coords[lo][2] < coords[findIndexOfZMinimum(coords, lo+1, hi)][2])
			return lo;
		return findIndexOfZMinimum(coords, lo + 1, hi);
		 // TODO: Replace this with your three lines of recursive code
	}
	

	/* Swaps the (x,y and z) values of the i-th and j-th coordinates.*/
	static void swap(double[][] coords, int i, int j) {
		double[] temp = new double[1];
		temp = coords[i];
		coords[i] = coords[j];
		coords[j] = temp;
		// TODO: write your swap implementation here
	}
}
