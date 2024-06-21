import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../authority-menu-item.test-samples';

import { AuthorityMenuItemFormService } from './authority-menu-item-form.service';

describe('AuthorityMenuItem Form Service', () => {
  let service: AuthorityMenuItemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthorityMenuItemFormService);
  });

  describe('Service methods', () => {
    describe('createAuthorityMenuItemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isAllow: expect.any(Object),
            isRead: expect.any(Object),
            isWrite: expect.any(Object),
            isDelete: expect.any(Object),
            menuItem: expect.any(Object),
            authority: expect.any(Object),
          }),
        );
      });

      it('passing IAuthorityMenuItem should create a new form with FormGroup', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            isAllow: expect.any(Object),
            isRead: expect.any(Object),
            isWrite: expect.any(Object),
            isDelete: expect.any(Object),
            menuItem: expect.any(Object),
            authority: expect.any(Object),
          }),
        );
      });
    });

    describe('getAuthorityMenuItem', () => {
      it('should return NewAuthorityMenuItem for default AuthorityMenuItem initial value', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup(sampleWithNewData);

        const authorityMenuItem = service.getAuthorityMenuItem(formGroup) as any;

        expect(authorityMenuItem).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuthorityMenuItem for empty AuthorityMenuItem initial value', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup();

        const authorityMenuItem = service.getAuthorityMenuItem(formGroup) as any;

        expect(authorityMenuItem).toMatchObject({});
      });

      it('should return IAuthorityMenuItem', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup(sampleWithRequiredData);

        const authorityMenuItem = service.getAuthorityMenuItem(formGroup) as any;

        expect(authorityMenuItem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAuthorityMenuItem should not enable id FormControl', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAuthorityMenuItem should disable id FormControl', () => {
        const formGroup = service.createAuthorityMenuItemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
