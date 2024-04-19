import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCardMemosComponent } from './all-card-memos.component';

describe('AllCardMemosComponent', () => {
  let component: AllCardMemosComponent;
  let fixture: ComponentFixture<AllCardMemosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllCardMemosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllCardMemosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
