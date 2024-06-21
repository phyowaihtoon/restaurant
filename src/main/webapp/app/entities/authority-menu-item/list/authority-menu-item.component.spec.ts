import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuthorityMenuItemService } from '../service/authority-menu-item.service';

import { AuthorityMenuItemComponent } from './authority-menu-item.component';

describe('AuthorityMenuItem Management Component', () => {
  let comp: AuthorityMenuItemComponent;
  let fixture: ComponentFixture<AuthorityMenuItemComponent>;
  let service: AuthorityMenuItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'authority-menu-item', component: AuthorityMenuItemComponent }]),
        HttpClientTestingModule,
        AuthorityMenuItemComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AuthorityMenuItemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthorityMenuItemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AuthorityMenuItemService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.authorityMenuItems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to authorityMenuItemService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAuthorityMenuItemIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAuthorityMenuItemIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
