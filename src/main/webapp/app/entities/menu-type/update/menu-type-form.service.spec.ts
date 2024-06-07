import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../menu-type.test-samples';

import { MenuTypeFormService } from './menu-type-form.service';

describe('MenuType Form Service', () => {
  let service: MenuTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuTypeFormService);
  });

  describe('Service methods', () => {
    describe('createMenuTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMenuTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IMenuType should create a new form with FormGroup', () => {
        const formGroup = service.createMenuTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getMenuType', () => {
      it('should return NewMenuType for default MenuType initial value', () => {
        const formGroup = service.createMenuTypeFormGroup(sampleWithNewData);

        const menuType = service.getMenuType(formGroup) as any;

        expect(menuType).toMatchObject(sampleWithNewData);
      });

      it('should return NewMenuType for empty MenuType initial value', () => {
        const formGroup = service.createMenuTypeFormGroup();

        const menuType = service.getMenuType(formGroup) as any;

        expect(menuType).toMatchObject({});
      });

      it('should return IMenuType', () => {
        const formGroup = service.createMenuTypeFormGroup(sampleWithRequiredData);

        const menuType = service.getMenuType(formGroup) as any;

        expect(menuType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMenuType should not enable id FormControl', () => {
        const formGroup = service.createMenuTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMenuType should disable id FormControl', () => {
        const formGroup = service.createMenuTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
