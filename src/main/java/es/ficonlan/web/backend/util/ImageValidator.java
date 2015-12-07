package es.ficonlan.web.backend.util;

import java.util.ArrayList;
import java.util.Arrays;

import es.ficonlan.web.backend.model.util.exceptions.InvalidImageFormatException;

public class ImageValidator {
	private static ArrayList<String> validFormats = 
		new ArrayList<>(Arrays.asList(
			"ani","bmp", "cal","fax","gif","img",
			"jbg","jpe","jpeg","jpg","mac","pbm",
			"pcd","pcx","pct","pgm","png","ppm",
			"psd","ras","tga","tiff","wmf"));


	public static boolean isValidFormat(String format) {
		return validFormats.contains(format);
	}
	

	public static String getImageFormat(String imageUrl) throws InvalidImageFormatException {
		int pos = imageUrl.lastIndexOf(".");
		String extension;
		if ((pos<0) || (!isValidFormat(extension = imageUrl.substring(++pos)))){
			//This is not a valid image format.
			throw new InvalidImageFormatException(imageUrl);
		}
		return extension;
	}
}
