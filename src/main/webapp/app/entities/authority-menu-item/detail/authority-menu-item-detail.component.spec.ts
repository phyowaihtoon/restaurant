import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuthorityMenuItemDetailComponent } from './authority-menu-item-detail.component';

describe('AuthorityMenuItem Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthorityMenuItemDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AuthorityMenuItemDetailComponent,
              resolve: { authorityMenuItem: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AuthorityMenuItemDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load authorityMenuItem on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AuthorityMenuItemDetailComponent);

      // THEN
      expect(instance.authorityMenuItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
