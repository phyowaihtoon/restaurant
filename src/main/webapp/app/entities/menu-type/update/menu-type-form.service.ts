import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMenuType, NewMenuType } from '../menu-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMenuType for edit and NewMenuTypeFormGroupInput for create.
 */
type MenuTypeFormGroupInput = IMenuType | PartialWithRequiredKeyOf<NewMenuType>;

type MenuTypeFormDefaults = Pick<NewMenuType, 'id'>;

type MenuTypeFormGroupContent = {
  id: FormControl<IMenuType['id'] | NewMenuType['id']>;
  name: FormControl<IMenuType['name']>;
};

export type MenuTypeFormGroup = FormGroup<MenuTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MenuTypeFormService {
  createMenuTypeFormGroup(menuType: MenuTypeFormGroupInput = { id: null }): MenuTypeFormGroup {
    const menuTypeRawValue = {
      ...this.getFormDefaults(),
      ...menuType,
    };
    return new FormGroup<MenuTypeFormGroupContent>({
      id: new FormControl(
        { value: menuTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(menuTypeRawValue.name, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
    });
  }

  getMenuType(form: MenuTypeFormGroup): IMenuType | NewMenuType {
    return form.getRawValue() as IMenuType | NewMenuType;
  }

  resetForm(form: MenuTypeFormGroup, menuType: MenuTypeFormGroupInput): void {
    const menuTypeRawValue = { ...this.getFormDefaults(), ...menuType };
    form.reset(
      {
        ...menuTypeRawValue,
        id: { value: menuTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MenuTypeFormDefaults {
    return {
      id: null,
    };
  }
}
