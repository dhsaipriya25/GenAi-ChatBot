package com.bot.loginAndRegisterationApp.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DocumentDto {

	 private String id;
	    private List<String> name;
	    private List<String> type;
	    private List<byte[]> data;
	    private String url;
	    private String fileLink;
	    private String userName;
		public String getId() {
			return id;
		}
		public List<String> getName() {
			return name;
		}
		public List<String> getType() {
			return type;
		}
		public List<byte[]> getData() {
			return data;
		}
		public String getUrl() {
			return url;
		}
		public String getFileLink() {
			return fileLink;
		}
		public String getUserName() {
			return userName;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setName(List<String> name) {
			this.name = name;
		}
		public void setType(List<String> type) {
			this.type = type;
		}
		public void setData(List<byte[]> data) {
			this.data = data;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public void setFileLink(String fileLink) {
			this.fileLink = fileLink;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public DocumentDto(String id, List<String> name, List<String> type, List<byte[]> data, String url,
				String fileLink, String userName) {
			super();
			this.id = id;
			this.name = name;
			this.type = type;
			this.data = data;
			this.url = url;
			this.fileLink = fileLink;
			this.userName = userName;
		}
		
		public DocumentDto() {
			
		}
		
	    
	    
}
