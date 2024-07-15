import { TestBed } from '@angular/core/testing';

import { CardMemoService } from './card-memo.service';

describe('CardService', () => {
  let service: CardMemoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CardMemoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
