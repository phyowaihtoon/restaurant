import { IReservation } from 'app/entities/reservation/reservation.model';

export interface IDiningTable {
  id: number;
  tableNumber?: number | null;
  seatingCapacity?: number | null;
  tableStatus?: number | null;
  reservations?: Pick<IReservation, 'id'>[] | null;
}

export type NewDiningTable = Omit<IDiningTable, 'id'> & { id: null };
