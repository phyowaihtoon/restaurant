import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAuthorityMenuItem } from '../authority-menu-item.model';

@Component({
  standalone: true,
  selector: 'jhi-authority-menu-item-detail',
  templateUrl: './authority-menu-item-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AuthorityMenuItemDetailComponent {
  @Input() authorityMenuItem: IAuthorityMenuItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
