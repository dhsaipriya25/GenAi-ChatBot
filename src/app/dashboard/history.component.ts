import { Component } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { UploadDocuments } from '../upload-documents';


@Component({
    selector: 'app-history',
    templateUrl: './history.component.html',
    styleUrl: './history.component.css',
    standalone: false
})
export class HistoryComponent {
    con: boolean=false;
    constructor(private uploadDocumentService:UploadDocumentService,private route: ActivatedRoute,private router:Router) {}
    userName:any;
    uploadDocuments : UploadDocuments[] =[];
    show : UploadDocuments | null | undefined;
    
    ngOnInit(): void {
      
      this.userName=sessionStorage.getItem("userName");
      console.log(this.userName);
      this.uploadDocumentService.getDocumentsByUserName(this.userName).subscribe(data =>{
        this.uploadDocuments=data;
        console.log(data);
        console.log(this.uploadDocuments);
      });
    }

    navigateToBack(){
      this.router.navigate(['/dashboard'])
    }

    display(id:string)
    {
      this.con=true;
      this.uploadDocumentService.getDocumentByDocId(id).subscribe(val =>{
        this.show=val;
        console.log(val);
        console.log(this.show);
    });
  }
    noDisplay(){
      this.con=false;
      this.show=null;
    }
    
    deleteId(id:string){
    
    if(confirm('Are you sure you want to delete this document')){
      this.uploadDocumentService.deleteDocumentByDocId(id).subscribe(
        response =>{
          console.log(response);
          this.uploadDocuments = this.uploadDocuments.filter(doc => doc.id !== id);
          Swal.fire("","Deleted Successfully","success");
        },
        error =>{
          console.error("Error deleting document" , error);
        }
      )
    }
  }

  showUpdateFormFlag: boolean = false;
  selectedDocument: UploadDocuments = {} as UploadDocuments;
  showUpdateForm(doc: UploadDocuments) {
    this.selectedDocument = { ...doc };
    this.showUpdateFormFlag = true;
  }
  closeUpdateForm() {
    this.showUpdateFormFlag = false;
  }
  updateDocument() {
    this.uploadDocumentService.updateDocument(this.selectedDocument).subscribe(response => {
      Swal.fire('Success', 'Document updated successfully', 'success');
      this.ngOnInit();  
      this.showUpdateFormFlag = false;

    }, error => {
      Swal.fire('Error', 'Failed to update document', 'error');
    });
  }
}
