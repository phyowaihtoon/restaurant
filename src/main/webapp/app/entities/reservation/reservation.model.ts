import dayjs from 'dayjs/esm';
import { IDiningTable } from 'app/entities/dining-table/dining-table.model';

export interface IReservation {
  id: number;
  name?: string | null;
  phone?: string | null;
  reservedDate?: dayjs.Dayjs | null;
  diningTables?: Pick<IDiningTable, 'id'>[] | null;
}

export type NewReservation = Omit<IReservation, 'id'> & { id: null };
