import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAuthorityMenuItem, NewAuthorityMenuItem } from '../authority-menu-item.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAuthorityMenuItem for edit and NewAuthorityMenuItemFormGroupInput for create.
 */
type AuthorityMenuItemFormGroupInput = IAuthorityMenuItem | PartialWithRequiredKeyOf<NewAuthorityMenuItem>;

type AuthorityMenuItemFormDefaults = Pick<NewAuthorityMenuItem, 'id'>;

type AuthorityMenuItemFormGroupContent = {
  id: FormControl<IAuthorityMenuItem['id'] | NewAuthorityMenuItem['id']>;
  isAllow: FormControl<IAuthorityMenuItem['isAllow']>;
  isRead: FormControl<IAuthorityMenuItem['isRead']>;
  isWrite: FormControl<IAuthorityMenuItem['isWrite']>;
  isDelete: FormControl<IAuthorityMenuItem['isDelete']>;
  menuItem: FormControl<IAuthorityMenuItem['menuItem']>;
  authority: FormControl<IAuthorityMenuItem['authority']>;
};

export type AuthorityMenuItemFormGroup = FormGroup<AuthorityMenuItemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AuthorityMenuItemFormService {
  createAuthorityMenuItemFormGroup(authorityMenuItem: AuthorityMenuItemFormGroupInput = { id: null }): AuthorityMenuItemFormGroup {
    const authorityMenuItemRawValue = {
      ...this.getFormDefaults(),
      ...authorityMenuItem,
    };
    return new FormGroup<AuthorityMenuItemFormGroupContent>({
      id: new FormControl(
        { value: authorityMenuItemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      isAllow: new FormControl(authorityMenuItemRawValue.isAllow, {
        validators: [Validators.min(0), Validators.max(1)],
      }),
      isRead: new FormControl(authorityMenuItemRawValue.isRead, {
        validators: [Validators.min(0), Validators.max(1)],
      }),
      isWrite: new FormControl(authorityMenuItemRawValue.isWrite, {
        validators: [Validators.min(0), Validators.max(1)],
      }),
      isDelete: new FormControl(authorityMenuItemRawValue.isDelete, {
        validators: [Validators.min(0), Validators.max(1)],
      }),
      menuItem: new FormControl(authorityMenuItemRawValue.menuItem),
      authority: new FormControl(authorityMenuItemRawValue.authority),
    });
  }

  getAuthorityMenuItem(form: AuthorityMenuItemFormGroup): IAuthorityMenuItem | NewAuthorityMenuItem {
    return form.getRawValue() as IAuthorityMenuItem | NewAuthorityMenuItem;
  }

  resetForm(form: AuthorityMenuItemFormGroup, authorityMenuItem: AuthorityMenuItemFormGroupInput): void {
    const authorityMenuItemRawValue = { ...this.getFormDefaults(), ...authorityMenuItem };
    form.reset(
      {
        ...authorityMenuItemRawValue,
        id: { value: authorityMenuItemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AuthorityMenuItemFormDefaults {
    return {
      id: null,
    };
  }
}
