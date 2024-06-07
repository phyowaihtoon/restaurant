import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReservation, NewReservation } from '../reservation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReservation for edit and NewReservationFormGroupInput for create.
 */
type ReservationFormGroupInput = IReservation | PartialWithRequiredKeyOf<NewReservation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReservation | NewReservation> = Omit<T, 'reservedDate'> & {
  reservedDate?: string | null;
};

type ReservationFormRawValue = FormValueOf<IReservation>;

type NewReservationFormRawValue = FormValueOf<NewReservation>;

type ReservationFormDefaults = Pick<NewReservation, 'id' | 'reservedDate' | 'diningTables'>;

type ReservationFormGroupContent = {
  id: FormControl<ReservationFormRawValue['id'] | NewReservation['id']>;
  name: FormControl<ReservationFormRawValue['name']>;
  phone: FormControl<ReservationFormRawValue['phone']>;
  reservedDate: FormControl<ReservationFormRawValue['reservedDate']>;
  diningTables: FormControl<ReservationFormRawValue['diningTables']>;
};

export type ReservationFormGroup = FormGroup<ReservationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReservationFormService {
  createReservationFormGroup(reservation: ReservationFormGroupInput = { id: null }): ReservationFormGroup {
    const reservationRawValue = this.convertReservationToReservationRawValue({
      ...this.getFormDefaults(),
      ...reservation,
    });
    return new FormGroup<ReservationFormGroupContent>({
      id: new FormControl(
        { value: reservationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(reservationRawValue.name, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      phone: new FormControl(reservationRawValue.phone, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      reservedDate: new FormControl(reservationRawValue.reservedDate),
      diningTables: new FormControl(reservationRawValue.diningTables ?? []),
    });
  }

  getReservation(form: ReservationFormGroup): IReservation | NewReservation {
    return this.convertReservationRawValueToReservation(form.getRawValue() as ReservationFormRawValue | NewReservationFormRawValue);
  }

  resetForm(form: ReservationFormGroup, reservation: ReservationFormGroupInput): void {
    const reservationRawValue = this.convertReservationToReservationRawValue({ ...this.getFormDefaults(), ...reservation });
    form.reset(
      {
        ...reservationRawValue,
        id: { value: reservationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReservationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      reservedDate: currentTime,
      diningTables: [],
    };
  }

  private convertReservationRawValueToReservation(
    rawReservation: ReservationFormRawValue | NewReservationFormRawValue,
  ): IReservation | NewReservation {
    return {
      ...rawReservation,
      reservedDate: dayjs(rawReservation.reservedDate, DATE_TIME_FORMAT),
    };
  }

  private convertReservationToReservationRawValue(
    reservation: IReservation | (Partial<NewReservation> & ReservationFormDefaults),
  ): ReservationFormRawValue | PartialWithRequiredKeyOf<NewReservationFormRawValue> {
    return {
      ...reservation,
      reservedDate: reservation.reservedDate ? reservation.reservedDate.format(DATE_TIME_FORMAT) : undefined,
      diningTables: reservation.diningTables ?? [],
    };
  }
}
