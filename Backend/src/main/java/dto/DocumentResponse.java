package com.bot.loginAndRegisterationApp.dto;


	public class DocumentResponse {
	    private String contentType;
	    private String content;
	 
	    public DocumentResponse(String contentType, String content) {
	        this.contentType = contentType;
	        this.content = content;
	    }
	 
	    // Getters and setters
	    public String getContentType() {
	        return contentType;
	    }
	 
	    public void setContentType(String contentType) {
	        this.contentType = contentType;
	    }
	 
	    public String getContent() {
	        return content;
	    }
	 
	    public void setContent(String content) {
	        this.content = content;
	    }
	}

