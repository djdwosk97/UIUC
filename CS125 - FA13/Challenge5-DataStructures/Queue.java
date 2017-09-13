//UIUC CS125 FALL 2013 MP. File: Queue.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428
/**
 * @author Dwoskin2
*/
public class Queue {
	private double[] list = new double[0];

	/** Adds the value to the front of the queue.
	 * Note the queue automatically resizes as more items are added. */
	
	public void add(double value) {
		double[] newList = new double[list.length + 1];
		newList[0] = value;
		for (int i = 0, x = 1; i < list.length && x < newList.length; i++, x++)
			newList[x] = list[i];
		list = newList;
	}
	/** Removes the value from the end of the queue. If the queue is empty, returns 0 */
	public double remove() {
		double temp = 0;
		double[] newList;
		if (list.length > 1) {
			newList = new double[list.length - 1];
			for (int i = 0; i < newList.length; i++)
				newList[i] = list[i];
			temp = list[list.length - 1];
			list = newList;
		}
		else if (list.length == 1){
			temp = list[list.length - 1];
			list = new double[0];
		}
		return temp;
			
	}
	
	/** Returns the number of items in the queue. */
	public int length() {
		return list.length;	
	}
	
	/** Returns true iff the queue is empty */
	public boolean isEmpty() {
		if (list.length == 0)
			return true;
		return false;
	}
	
	/** Returns a comma separated string representation of the queue. */
	public String toString() {
		String s = "";
		for (int i = list.length - 1; i > - 1; i--){
			if (!s.equals(""))
				s = s + "," + list[i];
			else if (s.equals(""))
				s = list[i] + "";
		}
		return s;
	}
}
