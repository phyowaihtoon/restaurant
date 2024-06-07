import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 20666,
  code: 'below lest pfft',
  name: 'frost mid reek',
  status: 11712,
};

export const sampleWithPartialData: ICategory = {
  id: 5966,
  code: 'gadzooks how',
  name: 'kit surprisingly',
  status: 31384,
};

export const sampleWithFullData: ICategory = {
  id: 22778,
  code: 'since cultivated yes',
  name: 'yippee diligently grounded',
  status: 6669,
};

export const sampleWithNewData: NewCategory = {
  code: 'about',
  name: 'devoted tell cop',
  status: 25686,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
