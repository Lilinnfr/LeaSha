import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeashaInputComponent } from './leasha-input.component';

describe('LeashaInputComponent', () => {
  let component: LeashaInputComponent;
  let fixture: ComponentFixture<LeashaInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeashaInputComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeashaInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
