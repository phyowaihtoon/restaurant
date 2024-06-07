import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 29755,
  productCode: 'why',
  productName: 'upliftingly',
  status: 13555,
};

export const sampleWithPartialData: IProduct = {
  id: 2918,
  productCode: 'over educate',
  productName: 'patch',
  imageURL: 'sight aw mixed',
  description: 'shingle',
  status: 31568,
};

export const sampleWithFullData: IProduct = {
  id: 18909,
  productCode: 'whose',
  productName: 'leaflet feminine disestablish',
  imageURL: 'mmm loftily till',
  description: 'limited',
  status: 20809,
};

export const sampleWithNewData: NewProduct = {
  productCode: 'trail',
  productName: 'oof',
  status: 14673,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
