package com.blogapp.exception;



public class PostNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String message) {
		super(message);
	}
	String resourseName;
	String fieldName;
	long filedVale;
	public PostNotFoundException(String resourseName, String fieldName, long filedVale) {
		super(String.format("%s not found with %s:%s",resourseName, fieldName,filedVale));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.filedVale = filedVale;
	}
	
}