//UIUC CS125 FALL 2013 MP. File: ExampleBits.java, CS125 Project: Challenge4-Photoscoop, Version: 2013-10-05T04:05:02-0500.795237517

public class ExampleBits {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<16;i++) {
			TextIO.putln(i+ " "+Integer.toHexString(i)+ " : "+Integer.toBinaryString(i));
		}
	}
}
