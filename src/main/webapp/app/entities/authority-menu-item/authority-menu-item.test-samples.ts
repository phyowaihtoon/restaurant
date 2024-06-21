import { IAuthorityMenuItem, NewAuthorityMenuItem } from './authority-menu-item.model';

export const sampleWithRequiredData: IAuthorityMenuItem = {
  id: 14442,
};

export const sampleWithPartialData: IAuthorityMenuItem = {
  id: 15598,
  isRead: 1,
  isDelete: 0,
};

export const sampleWithFullData: IAuthorityMenuItem = {
  id: 29533,
  isAllow: 1,
  isRead: 0,
  isWrite: 0,
  isDelete: 1,
};

export const sampleWithNewData: NewAuthorityMenuItem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
