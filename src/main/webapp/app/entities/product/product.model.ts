import { IMenuType } from 'app/entities/menu-type/menu-type.model';
import { ICategory } from 'app/entities/category/category.model';

export interface IProduct {
  id: number;
  productCode?: string | null;
  productName?: string | null;
  imageURL?: string | null;
  description?: string | null;
  status?: number | null;
  menuType?: Pick<IMenuType, 'id'> | null;
  category?: Pick<ICategory, 'id'> | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
