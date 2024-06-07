import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMenuType } from '../menu-type.model';
import { MenuTypeService } from '../service/menu-type.service';

@Component({
  standalone: true,
  templateUrl: './menu-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MenuTypeDeleteDialogComponent {
  menuType?: IMenuType;

  constructor(
    protected menuTypeService: MenuTypeService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.menuTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
