import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAuthorityMenuItem } from '../authority-menu-item.model';
import { AuthorityMenuItemService } from '../service/authority-menu-item.service';

@Component({
  standalone: true,
  templateUrl: './authority-menu-item-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AuthorityMenuItemDeleteDialogComponent {
  authorityMenuItem?: IAuthorityMenuItem;

  constructor(
    protected authorityMenuItemService: AuthorityMenuItemService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authorityMenuItemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
