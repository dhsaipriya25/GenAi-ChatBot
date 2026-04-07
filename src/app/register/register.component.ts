import { Component } from '@angular/core';
import { NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { AuthService } from '../auth.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    standalone: false
})
export class RegisterComponent {
  name: string = '';
  password: string = '';
  confirmPassword: string = '';
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  phoneNumber: string = '';
  errorMessage: string = '';
  errorResponse: Boolean = false;
  isLoading: boolean = false;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}
  register() {
    this.errorResponse = true;
    if (this.validateForm()) {
      this.errorResponse = false;
      this.saveData();
      console.log('registration done');
      
    } else {
      this.errorResponse = true;
      console.log('validation failed');
    }
  }

  saveData() {
    this.isLoading = true;
    let bodyData = {
      userName: this.name,
      password: this.password,
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      phoneNumber: this.phoneNumber,
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    this.http
      .post(
        'http://localhost:8090/userRegistration/register',
        bodyData,
        httpOptions
      )
      .subscribe(
        (resultData: any) => {
          console.log('result', resultData);
          // alert('Registration was succesful');
          this.isLoading = false;
          Swal.fire({
            title: "Success!",
            text: "Registration successful!",
            icon: "success"
          });
          this.authService.setname(resultData.username);
          this.name = '';
          this.password = '';
          this.firstName = '';
          this.lastName = '';
          this.email = '';
          this.phoneNumber = '';
          this.confirmPassword = '';
          this.router.navigate(['/login']);
        },
        (error) => {
          console.error('Error during registration', error);
          this.isLoading = false;
          Swal.fire({
            title: "Error",
            text: "Registration failed. Please check the details.",
            icon: "warning"
          });
        }
      );
  }

  validateForm(): boolean {
    if (!/^[a-zA-Z]+\d+$/.test(this.name)) {
      this.errorMessage = 'Username must be combination of letters and numbers';
      return false;
    }
    if (this.firstName.length < 4 && this.lastName.length < 4) {
      this.errorMessage =
        'FirstName and Lastname must be letters and atleast 4 characters each';
      return false;
    }
    if (!/^\d{10}$/.test(this.phoneNumber)) {
      this.errorMessage = 'Phone number must be 10 digits';
      return false;
    }
    if (!this.email) {
      this.errorMessage = 'Email address must not be empty';
      return false;
    }
    if (!this.password || !this.confirmPassword) {
      this.errorMessage = 'Password must not be empty';
      return false;
    }
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Password must be same';
      return false;
    }
    return true;
  }
}
