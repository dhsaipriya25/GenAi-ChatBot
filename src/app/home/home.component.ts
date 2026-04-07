import { Component, OnInit,OnDestroy } from '@angular/core';
import { Router } from '@angular/router';


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrl: './home.component.css',
    standalone: false
})
export class HomeComponent implements OnInit, OnDestroy {
  slides = [
    { img: 'assets/img3.jpeg', 
   },
    { 
      img: 'assets/img4.jpg',
     heading: 'INTRODUCING GEN AI CHAT BOT',
     paragraph:'AI Chat Bot provides visitors with instant access to sales or support teams,<br>thereby ensuring real-time communication and instant resolution of queries<br>with AI knowledge.'
     },
    { 
      img: 'assets/img1.jpg',
      heading: 'INTRODUCING GEN AI CHAT BOT',
      paragraph:'AI Chat Bot provides visitors with instant access to sales or support teams,<br>thereby ensuring real-time communication and instant resolution of queries<br>with AI knowledge.'
     }
  ];
  currentSlide = 0;
  slideInterval:any;

  

  ngOnInit(): void {
    this.startSlideShow();
   }

   ngOnDestroy(): void {
     this.stopSlideShow();
   }

   startSlideShow(){
    this.slideInterval=setInterval(()=>{
      this.nextSlide();
    },6000);
   }

   stopSlideShow(){
    if(this.slideInterval){
      clearInterval(this.slideInterval);
    }
   }

  nextSlide() {
    this.currentSlide = (this.currentSlide + 1) % this.slides.length;
  }

  prevSlide() {
    this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
  }




  userName: string = '';
 
  constructor(private router: Router) { }
 
  navigateToUpload() {
    this.router.navigate(['/upload-documents'], { queryParams: { userName: this.userName } });
  }
}


