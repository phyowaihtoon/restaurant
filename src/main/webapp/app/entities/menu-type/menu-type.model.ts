export interface IMenuType {
  id: number;
  name?: string | null;
}

export type NewMenuType = Omit<IMenuType, 'id'> & { id: null };
