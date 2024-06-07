import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDiningTable } from '../dining-table.model';
import { DiningTableService } from '../service/dining-table.service';

@Component({
  standalone: true,
  templateUrl: './dining-table-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DiningTableDeleteDialogComponent {
  diningTable?: IDiningTable;

  constructor(
    protected diningTableService: DiningTableService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diningTableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
