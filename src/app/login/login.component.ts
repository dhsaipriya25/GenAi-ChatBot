
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
  import { NgModule } from '@angular/core';
  import { ActivatedRoute, Router } from '@angular/router';
  import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
  import { AuthService } from '../auth.service';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import Swal from 'sweetalert2';
import { HistoryService } from '../history.service';
import { UserReg } from '../user-reg';

 
  @Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    standalone: false
})
  export class LoginComponent implements OnInit {
    email: string = '';
    password: string = '';
    name: string = '';
    user!:UserReg;
    isLoading: boolean = false;
 
    @ViewChild('emailInput') emailInput!: ElementRef;
    @ViewChild('passwordInput') passwordInput!: ElementRef;
    @ViewChild('monkeyFace') monkeyFace!: ElementRef;
    @ViewChild('monkeyHand') monkeyHand!: ElementRef;
    @ViewChild('monkeyThought') monkeyThought!: ElementRef;
    @ViewChild('monkeyEyesBrows') monkeyEyesBrows!: ElementRef[];
    @ViewChild('monkeyComment') monkeyComment!: ElementRef;
 
    private mailformat: RegExp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    private degree: number = 13;
    private inputPrevLength: number[] = [];
 
    constructor(
      private http: HttpClient,
      private router: ActivatedRoute,
      private route: Router,
      private authService: AuthService,
      private historyService:HistoryService
 
    ) {}
 
    ngOnInit(): void {
      this.name = this.router.snapshot.params['name'];
      document.addEventListener('click', (e) => this.handleDocumentClick(e));
    }
 
    login() {
      this.isLoading = true;
      let bodyData = {
        email: this.email,
        password: this.password,
      };
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      };
      this.http.post("http://localhost:8090/login", bodyData, httpOptions).subscribe(
        (resultData: any) => {
          console.log(resultData);
          this.historyService.getUserNameByEmail(this.email).subscribe(data=>{
            this.user=data;
            sessionStorage.setItem("userName",this.user.userName);
            this.historyService.setUserName(this.user.userName);
            this.email = '';
            this.password = '';
            this.isLoading = false;
            Swal.fire("success","Successfully Logged In","success");
            this.route.navigate(['/dashboard']);
          });
        },
        (error) => {
          console.error("Error during Login", error);
          this.isLoading = false;
          Swal.fire("warning","Login failed. Please check the details.","warning");
        }
      );
    }
    showMonkeyHand():void{
      this.monkeyHand.nativeElement.style.transform='translateY(30%)';
    }
 
    handleDocumentClick(e: any) {
      if (e.target.type !== 'password') {
        this.monkeyHand.nativeElement.style.transform = 'translateY(300%)';
      }
      if (e.target.type !== 'email') {
        this.monkeyFace.nativeElement.style.transform = `perspective(800px) rotateZ(0deg)`;
        this.monkeyEyesBrows.forEach((eyeBrow) => {
          eyeBrow.nativeElement.style.transform = 'translateY(-2px)';
        });
      }
    }
 
    handleEmailInput(e: any) {
      let currentInputLength = String(e.target.value).length;
      let decrementInInputValue = this.inputPrevLength.includes(currentInputLength);
      if (!decrementInInputValue && this.degree >= -10) {
        this.degree -= 1;
        this.inputPrevLength.push(currentInputLength);
      }
      if (decrementInInputValue && this.degree < 13) {
        this.degree += 1;
      }
      if (!this.email.match(this.mailformat)) {
        this.monkeyThought.nativeElement.style.opacity = '1';
        this.monkeyEyesBrows.forEach((eyeBrow) => {
          eyeBrow.nativeElement.style.transform = 'translateY(3px)';
        });
      }
      if (this.email.match(this.mailformat)) {
        this.monkeyThought.nativeElement.style.opacity = '0';
        this.monkeyEyesBrows.forEach((eyeBrow) => {
          eyeBrow.nativeElement.style.transform = 'translateY(-3px)';
        });
      }
      this.monkeyFace.nativeElement.style.transform = `perspective(800px) rotateZ(${this.degree}deg)`;
    }
 
    handleSubmit(event: any) {
      event.preventDefault(); // Prevent form submission
      if (this.email === '' || this.password === '') {
        this.monkeyFace.nativeElement.classList.add('red-face');
        this.monkeyThought.nativeElement.classList.add('error');
        this.monkeyComment.nativeElement.innerHTML = '<p>Please enter email</p>';
      } else {
        // Reset styles if validation passes
        this.monkeyFace.nativeElement.classList.remove('red-face');
        this.monkeyThought.nativeElement.classList.remove('error');
        this.monkeyComment.nativeElement.innerHTML = '<p>Is it an email?</p>';
        this.login();
      }
    }
  }