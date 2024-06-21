import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AuthorityMenuItemComponent } from './list/authority-menu-item.component';
import { AuthorityMenuItemDetailComponent } from './detail/authority-menu-item-detail.component';
import { AuthorityMenuItemUpdateComponent } from './update/authority-menu-item-update.component';
import AuthorityMenuItemResolve from './route/authority-menu-item-routing-resolve.service';

const authorityMenuItemRoute: Routes = [
  {
    path: '',
    component: AuthorityMenuItemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuthorityMenuItemDetailComponent,
    resolve: {
      authorityMenuItem: AuthorityMenuItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuthorityMenuItemUpdateComponent,
    resolve: {
      authorityMenuItem: AuthorityMenuItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuthorityMenuItemUpdateComponent,
    resolve: {
      authorityMenuItem: AuthorityMenuItemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default authorityMenuItemRoute;
