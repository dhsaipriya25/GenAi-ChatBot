import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocuments } from './upload-documents';
@Injectable({
  providedIn: 'root'
})
export class UploadDocumentService {
 
  private baseUrl = 'http://localhost:8090/api'; // Adjust the base URL as needed
 
  constructor(private http: HttpClient) { }
 
  uploadDocument(formData: FormData): Observable<any> {
return this.http.post(`${this.baseUrl}/documents/upload`, formData, {responseType: 'text'});
  }
 
  getDocumentsByUserName(userName: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/documents/user/${userName}`);
  }

  getDocumentByDocId(id:string): Observable<any> {
    return this.http.get(`${this.baseUrl}/documents/display/${id}`);
  }

  deleteDocumentByDocId(id:string): Observable<any>{
    return this.http.delete(`${this.baseUrl}/documents/delete/${id}`);
  }

  updateDocument(document: UploadDocuments): Observable<any> {
    const url = `${this.baseUrl}/documents/update/${document.id}`;
    const body = {
      prompt: document.prompt,
      url: document.url,
      apiKey: document.apiKey,
      apiProvider: document.apiProvider
    };
    return this.http.put(url, body);
  }
}


