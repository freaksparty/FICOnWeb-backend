package es.ficonlan.web.backend.model.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.ws.http.HTTPException;

import es.ficonlan.web.backend.model.util.exceptions.InvalidImageFormatException;
import es.ficonlan.web.backend.util.ImageValidator;

public class SaveImages {
	
	public static String saveImage(String imageUrl, String destinationPath, String name) throws IOException, InvalidImageFormatException {
		URL url = new URL(imageUrl);
		String extension;
		String destination;
		try {
			extension = ImageValidator.getImageFormat(imageUrl);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			int statusCode = http.getResponseCode();
			if (statusCode != 200){
				//Something went wrong when trying to connect.
				throw new IOException("Remote server returned "+statusCode+" error code.");
			}
			destination = destinationPath+"/"+name+"."+extension;
			OutputStream os = new FileOutputStream(destination);
			InputStream input = http.getInputStream();
			
			byte[] buffer = new byte[4096];
			int n = - 1;
			OutputStream output = new FileOutputStream(destination);
			while ( (n = input.read(buffer)) != -1){
			    output.write(buffer, 0, n);
			}
			output.close();
		} catch (InvalidImageFormatException e) {
			throw e;
		}
		return destination;
	}
}
