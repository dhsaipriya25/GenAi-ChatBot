import { Component, OnInit } from '@angular/core';
import { SharedService } from '../shared.service';
 
@Component({
    selector: 'app-submitted-details',
    templateUrl: './submitted-details.component.html',
    styleUrls: ['./submitted-details.component.css'],
    standalone: false
})
export class SubmittedDetailsComponent implements OnInit {
  formData: any;
  uniqueId: any;
  response: any;
  isRunning: boolean = false;
 
  constructor(private sharedService: SharedService) { }
 
  ngOnInit(): void {
    this.formData = this.sharedService.getFormData();
    this.uniqueId = this.formData.get("userName") + this.formData.get("applicationName");
  }
 
  onButtonClick(): void {
    this.isRunning = true;
    this.sharedService.getResponse(this.uniqueId).subscribe(
      (data: any) => {
        this.response = data;
        this.isRunning = false;
      },
      (error: any) => {
        console.error(error);
        this.isRunning = false;
      }
    );
  }
}