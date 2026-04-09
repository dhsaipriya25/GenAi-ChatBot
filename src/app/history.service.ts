import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  userName!: string;

  constructor(private http:HttpClient) { }
  
  private baseUrl = 'http://localhost:8090/userRegistration';
 
    getUserNameByEmail(email:string): Observable<any>
    {
      return this.http.get(`${this.baseUrl}/email/${email}`);
    }
 
    setUserName(userName:string)
    {
        this.userName=userName;
    }
 
    getUserName()
    {
        return this.userName;
    }
}
