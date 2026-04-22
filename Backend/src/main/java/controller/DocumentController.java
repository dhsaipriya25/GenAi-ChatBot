package com.bot.loginAndRegisterationApp.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.Document;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bot.loginAndRegisterationApp.model.Documents;
import com.bot.loginAndRegisterationApp.service.DocumentService;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {
	
	@Autowired
    private DocumentService documentService;
	 private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
 
   @GetMapping("/user/{userName}")
   public ResponseEntity<List<Documents>> getDocumentsByUserName(@PathVariable String userName) {
       List<Documents> documents = documentService.getDocumentsByUserName(userName);
       return new ResponseEntity<>(documents, HttpStatus.OK);
  }
 
   @GetMapping("/display/{id}")
   public ResponseEntity<Documents> viewDocument(@PathVariable String id) {
       Documents document = documentService.getDocumentById(id);
//      System.out.println(document.getName());
       return new ResponseEntity<>(document, HttpStatus.OK);
   }
   
   @DeleteMapping("/delete/{id}")
   public void deleteDocumentById(@PathVariable String id) {
	   documentService.deleteDocumentById(id);
   }
   
   @PutMapping("/update/{id}")
   public ResponseEntity<Documents> updateDocument(
           @PathVariable String id,@RequestBody  Documents doc)
   {

           
           Documents updated = documentService.updateDocument(id,doc);

           if(updated!=null)
        	   return new ResponseEntity<>(updated,HttpStatus.OK);
           else
        	   return new ResponseEntity<>(HttpStatus.NOT_FOUND);

   }



   
   
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("userName") String userName,
            @RequestParam("prompt") String prompt,
            @RequestParam("applicationName") String applicationName,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "fileLink", required = false) String fileLink,
            @RequestParam(value = "apiKey", required=false)String apiKey,
            @RequestParam(value = "apiProvider", required=false)String apiProvider){
    	
   System.out.println(apiKey + apiProvider );

   if (file != null) {
	   for(MultipartFile x: file) {
		   if(x!=null && x.getSize()>MAX_FILE_SIZE) {
			   return new ResponseEntity<>("File size exceeds the maximum limit of 5MB", HttpStatus.BAD_REQUEST);
		   }
	   }
       
       
   }
        try {
            documentService.uploadDocument(userName, prompt ,applicationName, file, url, fileLink,apiKey,apiProvider);
            return new ResponseEntity<>("Document uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Document upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 
    @GetMapping("/view/{userName}")
    public ResponseEntity<?> viewDocumentsByUserName(@PathVariable String userName) {
        List<Documents> documents = documentService.getDocumentsByUserName(userName);
     
        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
     
        StringBuilder responseBuilder = new StringBuilder();
     
     
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBuilder.toString());
    }
     
    private String extractTextFromPdf(byte[] pdfData) {
        try (PDDocument document = PDDocument.load(pdfData)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            return "Error extracting text from PDF";
        }
    }
     
    private String extractTextFromWord(byte[] wordData, String contentType) {
        try {
            if (contentType.equals("application/msword")) { // DOC files
                try (ByteArrayInputStream bis = new ByteArrayInputStream(wordData);
                     HWPFDocument document = new HWPFDocument(bis)) {
                    WordExtractor extractor = new WordExtractor(document);
                    return extractor.getText();
                }
            } else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) { // DOCX files
                try (ByteArrayInputStream bis = new ByteArrayInputStream(wordData);
                     XWPFDocument document = new XWPFDocument(bis)) {
                    XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                    return extractor.getText();
                }
            }
        } catch (IOException e) {
            return "Error extracting text from Word document";
        }
        return "Unsupported Word document format";
    }
    
}
    


   
