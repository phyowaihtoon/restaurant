import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiningTableService } from '../service/dining-table.service';
import { IDiningTable } from '../dining-table.model';
import { DiningTableFormService } from './dining-table-form.service';

import { DiningTableUpdateComponent } from './dining-table-update.component';

describe('DiningTable Management Update Component', () => {
  let comp: DiningTableUpdateComponent;
  let fixture: ComponentFixture<DiningTableUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diningTableFormService: DiningTableFormService;
  let diningTableService: DiningTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DiningTableUpdateComponent],
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
      .overrideTemplate(DiningTableUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiningTableUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diningTableFormService = TestBed.inject(DiningTableFormService);
    diningTableService = TestBed.inject(DiningTableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const diningTable: IDiningTable = { id: 456 };

      activatedRoute.data = of({ diningTable });
      comp.ngOnInit();

      expect(comp.diningTable).toEqual(diningTable);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiningTable>>();
      const diningTable = { id: 123 };
      jest.spyOn(diningTableFormService, 'getDiningTable').mockReturnValue(diningTable);
      jest.spyOn(diningTableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diningTable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diningTable }));
      saveSubject.complete();

      // THEN
      expect(diningTableFormService.getDiningTable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(diningTableService.update).toHaveBeenCalledWith(expect.objectContaining(diningTable));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiningTable>>();
      const diningTable = { id: 123 };
      jest.spyOn(diningTableFormService, 'getDiningTable').mockReturnValue({ id: null });
      jest.spyOn(diningTableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diningTable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diningTable }));
      saveSubject.complete();

      // THEN
      expect(diningTableFormService.getDiningTable).toHaveBeenCalled();
      expect(diningTableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDiningTable>>();
      const diningTable = { id: 123 };
      jest.spyOn(diningTableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diningTable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diningTableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
