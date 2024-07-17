package com.blogapp.exception;


public class ResourceNotFoundException extends RuntimeException{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourseName;
	String fieldName;
	long filedVale;
	public ResourceNotFoundException(String resourseName, String fieldName, long filedVale) {
		super(String.format("%s not found with %s:%s",resourseName, fieldName,filedVale));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.filedVale = filedVale;
	}
	public ResourceNotFoundException(String message) {
		super(message);
	}
	public ResourceNotFoundException(String string, String string2) {
		super(string+" not found with "+string2);
	}
	
	
	

}