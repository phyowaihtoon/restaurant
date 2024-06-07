import { IDiningTable, NewDiningTable } from './dining-table.model';

export const sampleWithRequiredData: IDiningTable = {
  id: 15241,
  tableNumber: 27012,
  seatingCapacity: 13864,
  tableStatus: 19380,
};

export const sampleWithPartialData: IDiningTable = {
  id: 10877,
  tableNumber: 10777,
  seatingCapacity: 31947,
  tableStatus: 16363,
};

export const sampleWithFullData: IDiningTable = {
  id: 25072,
  tableNumber: 13243,
  seatingCapacity: 20390,
  tableStatus: 19418,
};

export const sampleWithNewData: NewDiningTable = {
  tableNumber: 31658,
  seatingCapacity: 1014,
  tableStatus: 1887,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
