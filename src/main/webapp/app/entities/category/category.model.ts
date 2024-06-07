export interface ICategory {
  id: number;
  code?: string | null;
  name?: string | null;
  status?: number | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
