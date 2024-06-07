import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IReservation } from '../reservation.model';
import { ReservationService } from '../service/reservation.service';

@Component({
  standalone: true,
  templateUrl: './reservation-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ReservationDeleteDialogComponent {
  reservation?: IReservation;

  constructor(
    protected reservationService: ReservationService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reservationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
