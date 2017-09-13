//UIUC CS125 FALL 2013 MP. File: Stack.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428
/**
 * @author Dwoskin2
 */
public class Stack {
	String[] stack = new String[0];
	
	/** Adds a value to the top of the stack.*/
	public void push(String value){
		String[] newStack = new String[stack.length + 1];
		for (int i = 0; i < stack.length; i++)
			newStack[i] = stack[i];
		newStack[newStack.length - 1] = value;
		stack = newStack;
	}
	
	/** Removes the topmost string. If the stack is empty, returns null. */
	public String pop() {
		String temp = null;
		String[] newStack;
		if (stack.length > 0){
			newStack = new String[stack.length - 1];
			temp = stack[stack.length - 1];
			for (int i = 0; i < newStack.length; i++)
				newStack[i] = stack[i];
			stack = newStack;
		} 
		return temp;
	}
	
	/** Returns the topmost string but does not remove it. If the stack is empty, returns null. */
	public String peek() {
		if (stack.length == 0)
			return null;
		return stack[stack.length - 1];
	}
	
	/** Returns true iff the stack is empty */
	public boolean isEmpty() {
		if (stack.length == 0)
			return true;
		return false;
	}

	/** Returns the number of items in the stack. */
	public int length() {
		return stack.length;
	}
	
	/** Returns a string representation of the stack. Each string is separated by a newline. Returns an empty string if the stack is empty. */
	public String toString() {
		String s = "";
		if (stack.length == 0)
			return "";
		else {
			for (int i = 0; i < stack.length; i++)
				s = s + stack[i] + "\n";
			return s;
		}
	}

	private boolean isempty(String[] stack2) {
		// TODO Auto-generated method stub
		return false;
	}
}
