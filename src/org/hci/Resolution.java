package org.hci;

public class Resolution {
	
	int width;
	int height;
	
	// Width in pixels
	// Height in pixels
	public Resolution(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	// Convert 1111x2000 string to resolution
	public Resolution(String rawInput) {
			
		String[] arr = rawInput.split("x");
		this.width = Integer.parseInt(arr[0]);
		this.height = Integer.parseInt(arr[1]);
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.width) + "x" + String.valueOf(this.height);
	}
}
