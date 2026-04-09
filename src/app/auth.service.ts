import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private name: string=' ';
  setname(id:string):void{
    this.name;
  }
  getname():string {
    return this.name;
  }
}
