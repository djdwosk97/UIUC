//UIUC CS125 FALL 2013 MP. File: GeocacheList.java, CS125 Project: Challenge5-DataStructures, Version: 2013-10-14T14:22:11-0500.540422428
/**
 * Complete the following GeocacheList, to ensure all unit tests pass.
 * There are several errors in the code below
 *
 * Hint: Get the Geocache class working and passing its tests first.
 *
 *@author Dwoskin2
 */
public class GeocacheList {
	private Geocache[] data = new Geocache[0];
	private int size = 0;

	public Geocache getGeocache(int i) {
		return data[i];
	}

	public int getSize() {
		return data.length;
	}

	public GeocacheList() {
	}

	public GeocacheList(GeocacheList other, boolean deepCopy) {
		data = new Geocache[other.data.length];
		size = other.size;
		if (!deepCopy){
			for (int i = 0; i < data.length; i++){
				data[i]=other.data[i];
			}
		}
		else {
			for (int i = 0; i < data.length; i++){
				Geocache geo = new Geocache(other.data[i]);
				data[i] = new Geocache(other.data[i]);
			}
			//shallow = data; 
		}
	}

	public void add(Geocache p) {
		size++;
		if (size > data.length) {
			Geocache[] old = data;
			data = new Geocache[size];
			for (int i = 0; i < old.length; i++)
				data[i] = old[i];
		}
		data[size-1] = p;
	}

	public Geocache removeFromTop() {
		Geocache[] newData = new Geocache[data.length - 1];
		size--;
		Geocache temp = data[0];
		for (int i = 1; i < data.length; i++)
			newData[i-1]=data[i];
		data=newData;
		return temp;
	}

	public String toString() {
		StringBuffer s = new StringBuffer("GeocacheList:");
		for (int i = 0; i < size; i++) {
			if (i > 0)
				s.append(',');
			s.append(data[i]);
		}
		return s.toString();
}	}
