// import { HttpClient } from '@angular/common/http';
// import { Component,OnInit} from '@angular/core';
// import { UploadDocumentService } from '../upload-document.service';
// import { Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import Swal from 'sweetalert2';
// @Component({
//   selector: 'app-upload-documents',
//   templateUrl: './upload-documents.component.html',
//   styleUrl: './upload-documents.component.css'
// })




// export class UploadDocumentsComponent implements OnInit{

//   userName: string = '';
//   appilicationName: string='';
//   prompt: string='';
//   file: File | null = null;
//   url: string = '';
//   fileLink: string = '';
//   apiKey: string ='';
//   apiProvider: string='';
//   message: string = '';
//   // router: any;
//   uploadForm!: FormGroup;
//   constructor(private fb: FormBuilder,private uploadDocumentService: UploadDocumentService,private router:Router) {
    
//    }
//   ngOnInit(): void {
//     // throw new Error('Method not implemented.');
//     this.uploadForm = this.fb.group({

//       userName: ['', Validators.required],

//       applicationName: ['', Validators.required],

//       url: ['', Validators.required],

//       file: [null, Validators.required],

//       prompt: ['', Validators.required],

//       apiKey: ['', Validators.required],

//       apiProvider: ['gemini', Validators.required]

//     });

//     if(this.uploadForm.invalid)
//     {
//       Swal.fire("warning","Please fill all required fields.","warning");
//     }
//   }
 
//   onFileChange(event: any) {
//     this.file = event.target.files[0];
//     // this.file = event.target.files;
//     // console.log(this.file);

//   }

  
 
//   onSubmit() {

//     // if (!this.uploadForm.valid) {

//     //   Swal.fire("warning","Please fill all required fields.","warning");

//     //   // return;

//     // }
    

//     const formData = new FormData();
//     formData.append('userName', this.userName);
//     formData.append('applicationName',this.appilicationName);
//     formData.append('prompt',this.prompt);
//     formData.append('apiKey',this.apiKey);
//     formData.append('apiProvider',this.apiProvider);
//     if (this.file) {
//       formData.append('file', this.file);
//     }
//     if (this.url) {
//       formData.append('url', this.url);
//     }
//     if (this.fileLink) {
//       formData.append('fileLink', this.fileLink);
//     }
 
//     this.uploadDocumentService.uploadDocument(formData).subscribe(
//       response => {
//         console.log(response);
//         this.message = 'Document uploaded successfully';
//       },
//       error => {
//       console.error(error);
//       Swal.fire("warning","Document upload failed","warning");
//     }
//     );
//     this.router.navigate(['/dashboard',this.userName])
  
//   }
  
//   navigateToBack(){
//     this.router.navigate(['/dashboard'])
//   }
// }




import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { UploadDocumentService } from '../upload-document.service';
import { SharedService } from '../shared.service';
import { Router } from '@angular/router';
import  Swal  from 'sweetalert2'
@Component({
    selector: 'app-upload-documents',
    templateUrl: './upload-documents.component.html',
    styleUrls: ['./upload-documents.component.css'],
    standalone: false
})
export class UploadDocumentsComponent {

  uploadForm: FormGroup;
  message: string = '';
  success: boolean | null = null;

  constructor(
    private fb: FormBuilder, 
    private uploadDocumentService: UploadDocumentService,
    private sharedService: SharedService,
    private router:Router
    ) {
    this.uploadForm = this.fb.group({
      userName: ['', Validators.required],
      applicationName: ['', Validators.required],
      url: ['', [Validators.required, Validators.pattern('https?://.+')]],
      apiKey: ['', Validators.required],
      prompt: ['', Validators.required],
      apiProvider: ['Gemini'],
      files: this.fb.array([this.createFileInput()])
    });
  }

  get files(): FormArray {
    return this.uploadForm.get('files') as FormArray;
  }

  createFileInput(): FormGroup {
    return this.fb.group({
      file: [null, Validators.required]
    });
  }

  addFileInput(): void {
    this.files.push(this.createFileInput());
  }

  removeFileInput(index: number): void {
    this.files.removeAt(index);
  }

  onFileChange(event: any, index: number): void {
    const file = event.target.files[0];
    if (file) {
      this.files.at(index).get('file')?.setValue(file);
    }
  }

  onSubmit(): void {
    if (this.uploadForm.invalid) {
      this.uploadForm.markAllAsTouched();
      return;
    }

    const formData = new FormData();
    formData.append('userName', this.uploadForm.get('userName')?.value);
    formData.append('applicationName', this.uploadForm.get('applicationName')?.value);
    formData.append('url', this.uploadForm.get('url')?.value);
    formData.append('apiKey', this.uploadForm.get('apiKey')?.value);
    formData.append('prompt', this.uploadForm.get('prompt')?.value);
    formData.append('apiProvider', this.uploadForm.get('apiProvider')?.value);

    this.files.controls.forEach(control => {
      if (control.get('file')?.value) {
        formData.append('file', control.get('file')?.value);
      }
    });

    this.uploadDocumentService.uploadDocument(formData).subscribe(
      response => {
        console.log(response);
        this.message = 'Document uploaded successfully';
        Swal.fire("success","Document uploaded successfully","success");
        this.success = true;
        /*setTimeout(() => {
          window.location.reload();
        }, 9000);*/
        this.sharedService.setFormData(formData);
        this.router.navigate(['/submitted-details']);
      },
      error => {
        console.error(error);
        this.message = 'Document upload failed';
        Swal.fire("Warning" , "Document upload failed" , "warning");
        this.success = false;
      }
    );
  }

  navigateToBack(){
        this.router.navigate(['/dashboard'])
      }
}