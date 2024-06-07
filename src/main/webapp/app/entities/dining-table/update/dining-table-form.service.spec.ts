import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dining-table.test-samples';

import { DiningTableFormService } from './dining-table-form.service';

describe('DiningTable Form Service', () => {
  let service: DiningTableFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiningTableFormService);
  });

  describe('Service methods', () => {
    describe('createDiningTableFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDiningTableFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tableNumber: expect.any(Object),
            seatingCapacity: expect.any(Object),
            tableStatus: expect.any(Object),
            reservations: expect.any(Object),
          }),
        );
      });

      it('passing IDiningTable should create a new form with FormGroup', () => {
        const formGroup = service.createDiningTableFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tableNumber: expect.any(Object),
            seatingCapacity: expect.any(Object),
            tableStatus: expect.any(Object),
            reservations: expect.any(Object),
          }),
        );
      });
    });

    describe('getDiningTable', () => {
      it('should return NewDiningTable for default DiningTable initial value', () => {
        const formGroup = service.createDiningTableFormGroup(sampleWithNewData);

        const diningTable = service.getDiningTable(formGroup) as any;

        expect(diningTable).toMatchObject(sampleWithNewData);
      });

      it('should return NewDiningTable for empty DiningTable initial value', () => {
        const formGroup = service.createDiningTableFormGroup();

        const diningTable = service.getDiningTable(formGroup) as any;

        expect(diningTable).toMatchObject({});
      });

      it('should return IDiningTable', () => {
        const formGroup = service.createDiningTableFormGroup(sampleWithRequiredData);

        const diningTable = service.getDiningTable(formGroup) as any;

        expect(diningTable).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDiningTable should not enable id FormControl', () => {
        const formGroup = service.createDiningTableFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDiningTable should disable id FormControl', () => {
        const formGroup = service.createDiningTableFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
