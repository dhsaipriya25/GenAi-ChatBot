import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private formData: any;
  private apiUrl ='http://localhost:3000';

  constructor(private http: HttpClient) { }

  setFormData(data: any) {
    this.formData = data;
  }
 
  getFormData() {
    return this.formData;

  }
 // createMessage(): Observable<any> {
    //return this.http.get(`${this.url}`);
 // }

 getResponse(uniqueId:string):Observable<any> {  
  return this.http.post<any>(`${this.apiUrl}/`, { uniqueId }); 
 }
 
 
}
