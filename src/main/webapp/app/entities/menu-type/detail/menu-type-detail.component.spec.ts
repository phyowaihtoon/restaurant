import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MenuTypeDetailComponent } from './menu-type-detail.component';

describe('MenuType Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuTypeDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MenuTypeDetailComponent,
              resolve: { menuType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MenuTypeDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load menuType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MenuTypeDetailComponent);

      // THEN
      expect(instance.menuType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
