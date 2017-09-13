//UIUC CS125 FALL 2013 MP. File: KeyValueMap.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428
import java.awt.Color;
/**
 * 
 * @author Dwoskin2
 *
 */
public class KeyValueMap { // aka Dictionary or Map
	private String[] dictionary = new String[0];
	private Color[] definition = new Color[0];
	/**
	 * Adds a key and value. If the key already exists, it replaces the original
	 * entry.
	 */
	public void add(String key, Color value) {
		String[] newDictionary;
		Color[] newDefinition;
		for (int i = 0; i < dictionary.length; i++){
			if (dictionary[i].equals(key)){
				dictionary[i] = key;
				definition[i] = value;
				return;
			}
		}
		newDictionary = new String[dictionary.length + 1];
		newDefinition = new Color[definition.length + 1];
			for (int i = 0; i < dictionary.length; i++){
				newDictionary[i] = dictionary[i];
				newDefinition[i] = definition[i];
			}
		newDictionary[newDictionary.length - 1] = key;
		newDefinition[newDefinition.length - 1] = value;
		dictionary = newDictionary;
		definition = newDefinition; 
	}

	/**
	 * Returns particular Color object previously added to this map.
	 */
	public Color find(String key) {
		for (int i = 0; i < dictionary.length; i++){
			if (dictionary[i].equals(key)){
				dictionary[i] = key;
				return definition[i];
			}
		}
		return null;
	}

	/**
	 * Returns true if the key exists in this map.
	 */
	public boolean exists(String key) {
		for (int i = 0; i < dictionary.length; i++){
			if (dictionary[i].equals(key)){
				dictionary[i] = key;
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the key (and the color) from this map.
	 */
	public void remove(String key) {
		String[] newDictionary = new String[dictionary.length - 1];
		Color[] newDefinition = new Color[definition.length - 1];
		int i = 0;
	    int x = 0;
	    while(i < dictionary.length){
	        if(dictionary[i].equals(key)){
	        	i++;
	        }
	        else {
	        	newDictionary[x] = dictionary[i];
	        	newDefinition[x] = definition[i];
		        i++;
		        x++;
	        }
	    }
	    dictionary = newDictionary;
	    definition = newDefinition; 
	}

}
