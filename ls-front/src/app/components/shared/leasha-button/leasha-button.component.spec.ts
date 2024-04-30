import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeashaButtonComponent } from './leasha-button.component';

describe('LeashaButtonComponent', () => {
  let component: LeashaButtonComponent;
  let fixture: ComponentFixture<LeashaButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeashaButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeashaButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
