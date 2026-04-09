import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmittedDetailsComponent } from './submitted-details.component';

describe('SubmittedDetailsComponent', () => {
  let component: SubmittedDetailsComponent;
  let fixture: ComponentFixture<SubmittedDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubmittedDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubmittedDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
