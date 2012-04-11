package com.mms.mcm.model;

import java.util.List;

public class BooksOnHoldResponse {
	
	String errorMessage;
	List<Books> booksList;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<Books> getBooksList() {
		return booksList;
	}
	public void setBooksList(List<Books> booksList) {
		this.booksList = booksList;
	}
	
	
	

}
