//UIUC CS125 FALL 2013 MP. File: PixelEffects.java, CS125 Project: Challenge4-Photoscoop, Version: 2013-10-05T04:05:02-0500.795237517

/* A class to implement the various pixel effects.
 *
 * Todo: Put your netid (i.e. username) in the line below
 * 
 * @author dwoskin2
 */
public class PixelEffects {

	/** Copies the source image to a new 2D integer image */
	public static int[][] copy(int[][] source) {
		// Create a NEW 2D integer array and copy the colors across
		// See redeye code below
		int width = source.length, height = source[0].length;
		int[][] copy = new int[width][height];
		for (int x = 0; x < source.length ; x++)
			for (int y = 0; y < source[0].length ; y++)
				copy[x][y] = source[x][y];
		return copy; // Fix Me
	}
	/**
	 * Resize the array image to the new width and height
	 * You are going to need to figure out how to map between a pixel
	 * in the destination image to a pixel in the source image
	 * @param source
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static int[][] resize(int[][] source, int newWidth, int newHeight) {
		int width = source.length, height = source[0].length;
		int[][] resize = new int[newWidth][newHeight];
		for (int x = 0; x < newWidth; x++){
			for (int y = 0; y < newHeight; y++){
				int srcX = (int)((x/(double)(newWidth))*width);
				int srcY = (int)((y/(double)(newHeight))*height);
				resize[x][y] = source[srcX][srcY];}}
		return resize; // Fix Me
		// Hints: Use two nested for loops between 0... newWidth-1 and 0.. newHeight-1 inclusive.
		// Hint: You can just use relative proportion to calculate the x (or y coordinate)  in the original image.
		// For example if you're setting a pixel halfway across the image, you should be reading half way across the original image too.
	}

	/**
	 * Half the size of the image. This method should be just one line! Just
	 * delegate the work to resize()!
	 */
	public static int[][] half(int[][] source) {
		return resize(source, source.length/2, source[0].length/2); // Fix Me
	}
	
	/**
	 * Create a new image array that is the same dimensions of the reference
	 * array. The array may be larger or smaller than the source. Hint -
	 * this methods should be just one line - delegate the work to resize()!
	 * 
	 * @param source
	 *            the source image
	 * @param reference
	 * @return the resized image
	 */
	public static int[][] resize(int[][] source, int[][] reference) {
		return resize(source, reference.length, reference[0].length); // Fix Me
	}

	/** Flip the image vertically */
	public static int[][] flip(int[][] source) {
		int width = source.length; 
		int height = source[0].length;
		int newWidth = width;
		int newHeight = height;
		int[][] flip = new int [newWidth][newHeight];
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				int srcY = height - y -1;
				int srcX = x;
				flip[x][y] = source[srcX][srcY];
			}
		}
		return flip;// Fix Me
	}

	/** Reverse the image horizontally */
	public static int[][] mirror(int[][] source) {
		int width = source.length; 
		int height = source[0].length;
		int newWidth = width;
		int newHeight = height;
		int[][] mirror = new int [newWidth][newHeight];
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				int srcY = y;
				int srcX = width - x - 1;
				mirror[x][y] = source[srcX][srcY];
			}
		}
		return mirror;// Fix Me
	}

	/** Rotate the image */
	public static int[][] rotateLeft(int[][] source) {
		int width = source.length; 
		int height = source[0].length;
		int newWidth = height;
		int newHeight = width;
		int[][] rotateLeft = new int [newWidth][newHeight];
			/*for (int x = 0; x < newWidth; x++){
				for (int y = 0; y < newHeight; y++){
					int srcY = newWidth - x - 1;
					int srcX = y;
					rotateLeft[x][y] = source[srcX][srcY];
				}
			}*/
			for(int x=0; x < newWidth; x++){
		        for(int y = newHeight-1; y>=0; y--){
		            rotateLeft[x][y] = source[y][x];
		        }
		    }
		return flip(rotateLeft);
	}

	/** Merge the red,blue,green components from two images */
	public static int[][] merge(int[][] sourceA, int[][] sourceB) {
		//int [][] sourceAtemp = copy(sourceA);
		int [][] merge = new int [sourceA.length][sourceA[0].length];
		for (int x = 0; x < sourceA.length; x++){
			for (int y = 0; y <sourceA[0].length; y++){
				int red = RGBUtilities.toRed(sourceA[x][y] + sourceB[x][y]);
				red = red/2;
				int green = RGBUtilities.toGreen(sourceA[x][y] + sourceB[x][y]);
				green = green/2;
				int blue = RGBUtilities.toBlue(sourceA[x][y] + sourceB[x][y]);
				blue = blue/2;
				merge[x][y] = RGBUtilities.toRGB(red, green, blue);
			}
		}	
		// The output should be the same size as the input. Scale (x,y) values
		// when reading the background
		// (e.g. so the far right pixel of the source is merged with the
		// far-right pixel of the background).
		return merge;
	}

	/**
	 * Replace the green areas of the foreground image with parts of the back
	 * image
	 */
	public static int[][] chromaKey(int[][] foreImage, int[][] backImage) {
		int [][] chromaImage = new int[backImage.length][backImage[0].length];
		foreImage = resize(foreImage, backImage.length, backImage[0].length);
		for (int x = 0; x < backImage.length; x++){
			for (int y = 0; y < backImage[0].length; y++){
				int red = RGBUtilities.toRed(foreImage[x][y]);
				int green = RGBUtilities.toGreen(foreImage[x][y]);
				int blue = RGBUtilities.toBlue(foreImage[x][y]);
				if (green > (blue + red))
					chromaImage[x][y] = backImage[x][y];
				else
					chromaImage[x][y] = foreImage[x][y];
			}
		}	
		// If the image has a different size than the background use the
		// resize() method
		// create an image the same as the background size.
		return chromaImage;
	}

	/** Removes "redeye" caused by a camera flash. sourceB is not used */
	public static int[][] redeye(int[][] source, int[][] sourceB) {

		int width = source.length, height = source[0].length;
		int[][] result = new int[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				int rgb = source[i][j];
				int red = RGBUtilities.toRed(rgb);
				int green = RGBUtilities.toGreen(rgb);
				int blue = RGBUtilities.toBlue(rgb);
				if (red > 4 * Math.max(green, blue) && red > 64)
					red = green = blue = 0;
				result[i][j] = RGBUtilities.toRGB(red, green, blue);
			}

		return result;
	}

	/* Upto you! do something fun to the image */
	public static int[][] funky(int[][] source, int[][] sourceB) {
		int [][] funky = new int[sourceB.length][sourceB[0].length];
		source = resize(source, sourceB.length, sourceB[0].length);
		source = mirror(source);
		for (int x = 0; x < sourceB.length; x++){
			for (int y = 0; y < sourceB[0].length; y++){
				int red = RGBUtilities.toRed(source[x][y]);
				int green = RGBUtilities.toGreen(source[x][y]);
				int blue = RGBUtilities.toBlue(source[x][y]);
				if (blue > (green + red))
					funky[x][y] = sourceB[x][y];
				else
					funky[x][y] = source[x][y];
			}
		}
		// You need to invent your own image effect
		// Minimum boring requirements to pass autograder:
		
		// Does not ask for any user input and returns a new 2D array
		// Todo: remove this return null
		return funky;
		
	}
}
