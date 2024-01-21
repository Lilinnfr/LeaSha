import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardMemoComponent } from './card-memo.component';

describe('CardMemoComponent', () => {
  let component: CardMemoComponent;
  let fixture: ComponentFixture<CardMemoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CardMemoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CardMemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
