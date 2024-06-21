import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';
import ActiveMenuDirective from '../navbar/active-menu.directive';

@Component({
  standalone: true,
  selector: 'jhi-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss'],
  imports: [RouterModule, SharedModule, ActiveMenuDirective],
})
export class SideMenuComponent {
  private openSubMenus: { [key: string]: boolean } = {};

  toggleSubMenu(menu: string) {
    this.openSubMenus[menu] = !this.openSubMenus[menu];
  }

  isMenuCollapsed(menu: string): boolean {
    if (this.openSubMenus[menu] == undefined) {
      this.openSubMenus[menu] = true;
      return this.openSubMenus[menu];
    } else {
      return this.openSubMenus[menu];
    }
  }
}
