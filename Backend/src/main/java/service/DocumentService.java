package com.bot.loginAndRegisterationApp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bot.loginAndRegisterationApp.model.Documents;
import com.bot.loginAndRegisterationApp.model.UserRegistration;
import com.bot.loginAndRegisterationApp.repo.DocumentRepository;
import com.bot.loginAndRegisterationApp.repo.UserRegistrationRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	public List<Documents> getDocumentsByUserName(String userName) {
		return documentRepository.findByUserUserName(userName);

	}

	public Documents uploadDocument(String userName,String promt,String applicationName, MultipartFile[] file, String url, String fileLink, String apiKey,
			String apiProvider) throws IOException {
		UserRegistration user = userRegistrationRepository.findByUserName(userName);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		
		Documents documents = new Documents();
		
		List<Documents> existingDocument = documentRepository.findByUserUserName(userName);
		System.out.println(userName);
		System.out.println(existingDocument);
		if(!existingDocument.isEmpty()) {
			System.out.println("User exists");
			for(Documents doc: existingDocument) {
				if(Objects.equals(applicationName, doc.getApplicationName())) {
					throw new RuntimeException("Application name already exists");
				}
			}
		}	
			documents.setUser(user);
			documents.setId(userName+"_"+applicationName);
			documents.setPrompt(promt);
			documents.setApplicationName(applicationName);
			documents.setUrl(url);
			documents.setFileLink(fileLink);
			documents.setApiKey(apiKey);
			documents.setApiProvider(apiProvider);
			
			List<String> names = new ArrayList<>();
			List<String> types = new ArrayList<>();
			List<byte[]> data = new ArrayList<>();
			
			for(MultipartFile y: file) {
				
				if(y!=null) {
					names.add(y.getOriginalFilename());
					types.add(y.getContentType());
					data.add(y.getBytes());
				}

			documents.setName(names);
			documents.setType(types);
			documents.setData(data);
			}
		return documentRepository.save(documents);
	}

	public Documents getDocumentById(String id) {
		return documentRepository.findById(id).orElse(null);
	}
	
	public void deleteDocumentById(String id) {
		documentRepository.deleteById(id);
	}
	
	 public Documents updateDocument(String id,Documents doc) {
		 
		 	Optional<Documents> d= documentRepository.findById(id);
		 	if(d.isPresent())
		 	{
		 		Documents documents=d.get();
//		 	   String id1= documents.getUser().getUserName() + "_"+ documents.getApplicationName();
//		 	  documents.setId(id1);
		 	 documents.setPrompt(doc.getPrompt());
//		 	documents.setApplicationName(doc.getApplicationName());
		 	documents.setUrl(doc.getUrl());
		 	documents.setApiKey(doc.getApiKey());
		 	documents.setApiProvider(doc.getApiProvider());
	        return documentRepository.save(documents);

		 	}
		 	else
		 		return null;
	        
	        


	    }

