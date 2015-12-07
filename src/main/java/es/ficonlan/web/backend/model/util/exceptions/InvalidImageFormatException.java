package es.ficonlan.web.backend.model.util.exceptions;

public class InvalidImageFormatException extends Exception{
	private String msg;
	
	public InvalidImageFormatException(String imageUrl) {
		super(imageUrl+" has not a supported image format.");
		this.msg = imageUrl+" has not a supported image format.";
	}

	public String getMsg(){
		return this.msg;
	}
	
	

}
