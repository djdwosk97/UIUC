//UIUC CS125 FALL 2013 MP. File: GridCounting.java, CS125 Project: Challenge7-RecursiveKnight, Version: 2013-11-12T09:42:21-0600.264411210
/**
 * 
 * @author Dwoskin2
 *
 */
public class GridCounting {
	/** Returns the total number of possible routes (paths) from
	 * (x,y) to (tx,ty).
	 * There are only three valid kinds of moves:
	 *  Increment x by one.
	 *  Increment x by two.
	 *  Increment y by one.
	 *  
	 *  Hint: You'll need to test two base cases.
	 */
	public static int count(int x,int y, int tx, int ty) {
		int paths = 0;
		if (x == tx && y == ty){return  1;}
		if (tx < x && ty < y){return 0;}
		if (x + 1 <= tx)
			paths += count(x + 1, y, tx, ty);
		if (x + 2 <= tx)
			paths += count(x + 2, y, tx, ty);
		if (y + 1 <= ty)
			paths += count(x, y + 1, tx, ty);
		return paths;
	}
}
