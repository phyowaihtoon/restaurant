import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DiningTableDetailComponent } from './dining-table-detail.component';

describe('DiningTable Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiningTableDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DiningTableDetailComponent,
              resolve: { diningTable: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DiningTableDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load diningTable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DiningTableDetailComponent);

      // THEN
      expect(instance.diningTable).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
