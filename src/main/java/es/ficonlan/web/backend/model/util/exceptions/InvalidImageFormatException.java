package es.ficonlan.web.backend.model.util.exceptions;

public class InvalidImageFormatException extends Exception{
	private static final long serialVersionUID = 8127386715809896303L;
	
	private String msg;
	
	public InvalidImageFormatException(String imageUrl) {
		super(imageUrl+" has not a supported image format.");
		this.msg = imageUrl+" has not a supported image format.";
	}

	public String getMsg(){
		return this.msg;
	}
	
	

}
