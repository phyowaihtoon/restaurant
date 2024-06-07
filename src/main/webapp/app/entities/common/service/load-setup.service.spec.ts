import { TestBed } from '@angular/core/testing';

import { LoadSetupService } from './load-setup.service';

describe('LoadSetupService', () => {
  let service: LoadSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
