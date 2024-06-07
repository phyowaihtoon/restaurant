import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDiningTable, NewDiningTable } from '../dining-table.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDiningTable for edit and NewDiningTableFormGroupInput for create.
 */
type DiningTableFormGroupInput = IDiningTable | PartialWithRequiredKeyOf<NewDiningTable>;

type DiningTableFormDefaults = Pick<NewDiningTable, 'id' | 'reservations'>;

type DiningTableFormGroupContent = {
  id: FormControl<IDiningTable['id'] | NewDiningTable['id']>;
  tableNumber: FormControl<IDiningTable['tableNumber']>;
  seatingCapacity: FormControl<IDiningTable['seatingCapacity']>;
  tableStatus: FormControl<IDiningTable['tableStatus']>;
  reservations: FormControl<IDiningTable['reservations']>;
};

export type DiningTableFormGroup = FormGroup<DiningTableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DiningTableFormService {
  createDiningTableFormGroup(diningTable: DiningTableFormGroupInput = { id: null }): DiningTableFormGroup {
    const diningTableRawValue = {
      ...this.getFormDefaults(),
      ...diningTable,
    };
    return new FormGroup<DiningTableFormGroupContent>({
      id: new FormControl(
        { value: diningTableRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tableNumber: new FormControl(diningTableRawValue.tableNumber, {
        validators: [Validators.required],
      }),
      seatingCapacity: new FormControl(diningTableRawValue.seatingCapacity, {
        validators: [Validators.required],
      }),
      tableStatus: new FormControl(diningTableRawValue.tableStatus, {
        validators: [Validators.required],
      }),
      reservations: new FormControl(diningTableRawValue.reservations ?? []),
    });
  }

  getDiningTable(form: DiningTableFormGroup): IDiningTable | NewDiningTable {
    return form.getRawValue() as IDiningTable | NewDiningTable;
  }

  resetForm(form: DiningTableFormGroup, diningTable: DiningTableFormGroupInput): void {
    const diningTableRawValue = { ...this.getFormDefaults(), ...diningTable };
    form.reset(
      {
        ...diningTableRawValue,
        id: { value: diningTableRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DiningTableFormDefaults {
    return {
      id: null,
      reservations: [],
    };
  }
}
