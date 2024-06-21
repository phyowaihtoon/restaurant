import { IAuthority } from './authority.model';
import { IMenuItem } from './menu-item.model';

export interface IAuthorityMenuItem {
  id: number;
  isAllow?: number | null;
  isRead?: number | null;
  isWrite?: number | null;
  isDelete?: number | null;
  menuItem?: Pick<IMenuItem, 'id'> | null;
  authority?: Pick<IAuthority, 'name'> | null;
}

export type NewAuthorityMenuItem = Omit<IAuthorityMenuItem, 'id'> & { id: null };
