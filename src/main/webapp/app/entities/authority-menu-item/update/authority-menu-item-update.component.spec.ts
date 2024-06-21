import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMenuItem } from 'app/entities/menu-item/menu-item.model';
import { MenuItemService } from 'app/entities/menu-item/service/menu-item.service';
import { IAuthority } from 'app/entities/authority/authority.model';
import { AuthorityService } from 'app/entities/authority/service/authority.service';
import { IAuthorityMenuItem } from '../authority-menu-item.model';
import { AuthorityMenuItemService } from '../service/authority-menu-item.service';
import { AuthorityMenuItemFormService } from './authority-menu-item-form.service';

import { AuthorityMenuItemUpdateComponent } from './authority-menu-item-update.component';

describe('AuthorityMenuItem Management Update Component', () => {
  let comp: AuthorityMenuItemUpdateComponent;
  let fixture: ComponentFixture<AuthorityMenuItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let authorityMenuItemFormService: AuthorityMenuItemFormService;
  let authorityMenuItemService: AuthorityMenuItemService;
  let menuItemService: MenuItemService;
  let authorityService: AuthorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), AuthorityMenuItemUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AuthorityMenuItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthorityMenuItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    authorityMenuItemFormService = TestBed.inject(AuthorityMenuItemFormService);
    authorityMenuItemService = TestBed.inject(AuthorityMenuItemService);
    menuItemService = TestBed.inject(MenuItemService);
    authorityService = TestBed.inject(AuthorityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MenuItem query and add missing value', () => {
      const authorityMenuItem: IAuthorityMenuItem = { id: 456 };
      const menuItem: IMenuItem = { id: 17709 };
      authorityMenuItem.menuItem = menuItem;

      const menuItemCollection: IMenuItem[] = [{ id: 5555 }];
      jest.spyOn(menuItemService, 'query').mockReturnValue(of(new HttpResponse({ body: menuItemCollection })));
      const additionalMenuItems = [menuItem];
      const expectedCollection: IMenuItem[] = [...additionalMenuItems, ...menuItemCollection];
      jest.spyOn(menuItemService, 'addMenuItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ authorityMenuItem });
      comp.ngOnInit();

      expect(menuItemService.query).toHaveBeenCalled();
      expect(menuItemService.addMenuItemToCollectionIfMissing).toHaveBeenCalledWith(
        menuItemCollection,
        ...additionalMenuItems.map(expect.objectContaining),
      );
      expect(comp.menuItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Authority query and add missing value', () => {
      const authorityMenuItem: IAuthorityMenuItem = { id: 456 };
      const authority: IAuthority = { name: '1ba43fb2-c311-4b7a-aa40-be83547d32fc' };
      authorityMenuItem.authority = authority;

      const authorityCollection: IAuthority[] = [{ name: 'c80d0b0d-318c-401e-b4a5-7312102db4ac' }];
      jest.spyOn(authorityService, 'query').mockReturnValue(of(new HttpResponse({ body: authorityCollection })));
      const additionalAuthorities = [authority];
      const expectedCollection: IAuthority[] = [...additionalAuthorities, ...authorityCollection];
      jest.spyOn(authorityService, 'addAuthorityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ authorityMenuItem });
      comp.ngOnInit();

      expect(authorityService.query).toHaveBeenCalled();
      expect(authorityService.addAuthorityToCollectionIfMissing).toHaveBeenCalledWith(
        authorityCollection,
        ...additionalAuthorities.map(expect.objectContaining),
      );
      expect(comp.authoritiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const authorityMenuItem: IAuthorityMenuItem = { id: 456 };
      const menuItem: IMenuItem = { id: 4721 };
      authorityMenuItem.menuItem = menuItem;
      const authority: IAuthority = { name: 'e357e616-8482-4567-a3a2-289c18c0b249' };
      authorityMenuItem.authority = authority;

      activatedRoute.data = of({ authorityMenuItem });
      comp.ngOnInit();

      expect(comp.menuItemsSharedCollection).toContain(menuItem);
      expect(comp.authoritiesSharedCollection).toContain(authority);
      expect(comp.authorityMenuItem).toEqual(authorityMenuItem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthorityMenuItem>>();
      const authorityMenuItem = { id: 123 };
      jest.spyOn(authorityMenuItemFormService, 'getAuthorityMenuItem').mockReturnValue(authorityMenuItem);
      jest.spyOn(authorityMenuItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorityMenuItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authorityMenuItem }));
      saveSubject.complete();

      // THEN
      expect(authorityMenuItemFormService.getAuthorityMenuItem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(authorityMenuItemService.update).toHaveBeenCalledWith(expect.objectContaining(authorityMenuItem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthorityMenuItem>>();
      const authorityMenuItem = { id: 123 };
      jest.spyOn(authorityMenuItemFormService, 'getAuthorityMenuItem').mockReturnValue({ id: null });
      jest.spyOn(authorityMenuItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorityMenuItem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authorityMenuItem }));
      saveSubject.complete();

      // THEN
      expect(authorityMenuItemFormService.getAuthorityMenuItem).toHaveBeenCalled();
      expect(authorityMenuItemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthorityMenuItem>>();
      const authorityMenuItem = { id: 123 };
      jest.spyOn(authorityMenuItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorityMenuItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(authorityMenuItemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMenuItem', () => {
      it('Should forward to menuItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(menuItemService, 'compareMenuItem');
        comp.compareMenuItem(entity, entity2);
        expect(menuItemService.compareMenuItem).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAuthority', () => {
      it('Should forward to authorityService', () => {
        const entity = { name: 'ABC' };
        const entity2 = { name: 'CBA' };
        jest.spyOn(authorityService, 'compareAuthority');
        comp.compareAuthority(entity, entity2);
        expect(authorityService.compareAuthority).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
