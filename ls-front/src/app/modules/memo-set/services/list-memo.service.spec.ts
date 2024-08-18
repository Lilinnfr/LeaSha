import { TestBed } from '@angular/core/testing';

import { ListMemoService } from './list-memo.service';

describe('ListMemoService', () => {
  let service: ListMemoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListMemoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
