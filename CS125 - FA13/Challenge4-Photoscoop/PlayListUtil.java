//UIUC CS125 FALL 2013 MP. File: PlayListUtil.java, CS125 Project: Challenge4-Photoscoop, Version: 2013-10-05T04:05:02-0500.795237517
/**
 * Add you netid here..
 * 
 * @author dwoskin2
 * 
 */
public class PlayListUtil {

	/**
	 * Debug ME! Use the unit tests to reverse engineer how this method should
	 * work. Hint: Fix the formatting (shift-cmd-F) to help debug the following
	 * code
	 * 
	 * @param list
	 * @param maximum
	 */
	public static void list(String[] list, int maximum) {
		int i;
		if (maximum == -1){
			for (i = 0; i<list.length;i++){ 
			TextIO.putln("" + (i+1) + ". " + list[i]);}
		}
		else{
			for (i = 0; i < maximum; i++){
				TextIO.putln("" + (i+1) + ". " + list[i]);
			}
		}	
	}

	/**
	 * Appends or prepends the title
	 * 
	 * @param list
	 * @param title
	 * @param prepend
	 *            if true, prepend the title otherwise append the title
	 * @return a new list with the title prepended or appended to the original
	 *         list
	 */
	public static String[] add(String[] list, String title, boolean prepend) {
		String[] newList = new String[list.length+1];
		if (prepend){
			for (int i = 1; i < newList.length; i++){
				newList[0]=title;
				newList[i]=list[i-1];}}
		else{
			for (int i = 0; i < list.length; i++){
				newList[i]=list[i];
				newList[newList.length-1]=title;}
		}
		return newList;
	}

	/**
	 * Returns a new list with the element at index removed.
	 * 
	 * @param list
	 *            the original list
	 * @param index
	 *            the array index to remove.
	 * @return a new list with the String at position 'index', absent.
	 */
	public static String[] discard(String[] list, int index) {
		String[] newList = new String[list.length - 1]; 
	    int i = 0;
	    int x = 0;
	    while(i < list.length){
	        if(list[i] == list[index]){
	        	i++;
	        }
	        else {
		        newList[x] = list[i];
		        i++;
		        x++;
	        }
	    }
		return newList;
	}

}
