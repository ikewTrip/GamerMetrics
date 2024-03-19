import { TestBed } from '@angular/core/testing';

import { UserPlayService } from './user-play.service';

describe('UserPlayService', () => {
  let service: UserPlayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserPlayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
