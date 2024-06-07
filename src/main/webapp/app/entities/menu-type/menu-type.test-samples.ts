import { IMenuType, NewMenuType } from './menu-type.model';

export const sampleWithRequiredData: IMenuType = {
  id: 16901,
  name: 'sprint break',
};

export const sampleWithPartialData: IMenuType = {
  id: 13988,
  name: 'where unused',
};

export const sampleWithFullData: IMenuType = {
  id: 5731,
  name: 'regal whose',
};

export const sampleWithNewData: NewMenuType = {
  name: 'stravaig naturally',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
