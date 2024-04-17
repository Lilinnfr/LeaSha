import { TestBed } from '@angular/core/testing';

import { MemoCardService } from './memo-card.service';

describe('CardService', () => {
  let service: MemoCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemoCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
