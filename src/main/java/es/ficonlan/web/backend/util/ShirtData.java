package es.ficonlan.web.backend.util;

public class ShirtData {
	
	private String size;
	
	private int number;
	
	public ShirtData(String size, int number) {
		this.size = size;
		this.number = number;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
