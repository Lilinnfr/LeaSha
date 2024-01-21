import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListMemoComponent } from './list-memo.component';

describe('ListMemoComponent', () => {
  let component: ListMemoComponent;
  let fixture: ComponentFixture<ListMemoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListMemoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListMemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
