import dayjs from 'dayjs/esm';

import { IReservation, NewReservation } from './reservation.model';

export const sampleWithRequiredData: IReservation = {
  id: 3189,
  name: 'whoa truthful quarrelsome',
  phone: '788.478.9301 x7101',
};

export const sampleWithPartialData: IReservation = {
  id: 6245,
  name: 'pfft staff',
  phone: '524.917.8376 x7705',
  reservedDate: dayjs('2024-06-03T14:25'),
};

export const sampleWithFullData: IReservation = {
  id: 21719,
  name: 'excepting single simple',
  phone: '222-340-2158 x56009',
  reservedDate: dayjs('2024-06-03T06:14'),
};

export const sampleWithNewData: NewReservation = {
  name: 'vacantly across boohoo',
  phone: '(834) 877-4080 x5410',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
