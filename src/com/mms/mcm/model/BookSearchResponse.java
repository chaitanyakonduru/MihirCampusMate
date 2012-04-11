package com.mms.mcm.model;

import java.util.List;

public class BookSearchResponse {

	private String errorMsg;
	private List<Books> booksList;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<Books> getBooksList() {
		return booksList;
	}
	public void setBooksList(List<Books> booksList) {
		this.booksList = booksList;
	}
	
	
}
