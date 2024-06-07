import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MenuTypeService } from '../service/menu-type.service';
import { IMenuType } from '../menu-type.model';
import { MenuTypeFormService } from './menu-type-form.service';

import { MenuTypeUpdateComponent } from './menu-type-update.component';

describe('MenuType Management Update Component', () => {
  let comp: MenuTypeUpdateComponent;
  let fixture: ComponentFixture<MenuTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let menuTypeFormService: MenuTypeFormService;
  let menuTypeService: MenuTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MenuTypeUpdateComponent],
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
      .overrideTemplate(MenuTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MenuTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    menuTypeFormService = TestBed.inject(MenuTypeFormService);
    menuTypeService = TestBed.inject(MenuTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const menuType: IMenuType = { id: 456 };

      activatedRoute.data = of({ menuType });
      comp.ngOnInit();

      expect(comp.menuType).toEqual(menuType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuType>>();
      const menuType = { id: 123 };
      jest.spyOn(menuTypeFormService, 'getMenuType').mockReturnValue(menuType);
      jest.spyOn(menuTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuType }));
      saveSubject.complete();

      // THEN
      expect(menuTypeFormService.getMenuType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(menuTypeService.update).toHaveBeenCalledWith(expect.objectContaining(menuType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuType>>();
      const menuType = { id: 123 };
      jest.spyOn(menuTypeFormService, 'getMenuType').mockReturnValue({ id: null });
      jest.spyOn(menuTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuType }));
      saveSubject.complete();

      // THEN
      expect(menuTypeFormService.getMenuType).toHaveBeenCalled();
      expect(menuTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuType>>();
      const menuType = { id: 123 };
      jest.spyOn(menuTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(menuTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
