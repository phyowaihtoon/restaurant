import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IDiningTable } from 'app/entities/dining-table/dining-table.model';
import { DiningTableService } from 'app/entities/dining-table/service/dining-table.service';
import { ReservationService } from '../service/reservation.service';
import { IReservation } from '../reservation.model';
import { ReservationFormService } from './reservation-form.service';

import { ReservationUpdateComponent } from './reservation-update.component';

describe('Reservation Management Update Component', () => {
  let comp: ReservationUpdateComponent;
  let fixture: ComponentFixture<ReservationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reservationFormService: ReservationFormService;
  let reservationService: ReservationService;
  let diningTableService: DiningTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ReservationUpdateComponent],
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
      .overrideTemplate(ReservationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReservationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reservationFormService = TestBed.inject(ReservationFormService);
    reservationService = TestBed.inject(ReservationService);
    diningTableService = TestBed.inject(DiningTableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DiningTable query and add missing value', () => {
      const reservation: IReservation = { id: 456 };
      const diningTables: IDiningTable[] = [{ id: 5284 }];
      reservation.diningTables = diningTables;

      const diningTableCollection: IDiningTable[] = [{ id: 30593 }];
      jest.spyOn(diningTableService, 'query').mockReturnValue(of(new HttpResponse({ body: diningTableCollection })));
      const additionalDiningTables = [...diningTables];
      const expectedCollection: IDiningTable[] = [...additionalDiningTables, ...diningTableCollection];
      jest.spyOn(diningTableService, 'addDiningTableToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      expect(diningTableService.query).toHaveBeenCalled();
      expect(diningTableService.addDiningTableToCollectionIfMissing).toHaveBeenCalledWith(
        diningTableCollection,
        ...additionalDiningTables.map(expect.objectContaining),
      );
      expect(comp.diningTablesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const reservation: IReservation = { id: 456 };
      const diningTables: IDiningTable = { id: 21955 };
      reservation.diningTables = [diningTables];

      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      expect(comp.diningTablesSharedCollection).toContain(diningTables);
      expect(comp.reservation).toEqual(reservation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReservation>>();
      const reservation = { id: 123 };
      jest.spyOn(reservationFormService, 'getReservation').mockReturnValue(reservation);
      jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reservation }));
      saveSubject.complete();

      // THEN
      expect(reservationFormService.getReservation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reservationService.update).toHaveBeenCalledWith(expect.objectContaining(reservation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReservation>>();
      const reservation = { id: 123 };
      jest.spyOn(reservationFormService, 'getReservation').mockReturnValue({ id: null });
      jest.spyOn(reservationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reservation }));
      saveSubject.complete();

      // THEN
      expect(reservationFormService.getReservation).toHaveBeenCalled();
      expect(reservationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReservation>>();
      const reservation = { id: 123 };
      jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reservation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reservationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDiningTable', () => {
      it('Should forward to diningTableService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(diningTableService, 'compareDiningTable');
        comp.compareDiningTable(entity, entity2);
        expect(diningTableService.compareDiningTable).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
