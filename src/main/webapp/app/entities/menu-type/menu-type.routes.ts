import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MenuTypeComponent } from './list/menu-type.component';
import { MenuTypeDetailComponent } from './detail/menu-type-detail.component';
import { MenuTypeUpdateComponent } from './update/menu-type-update.component';
import MenuTypeResolve from './route/menu-type-routing-resolve.service';

const menuTypeRoute: Routes = [
  {
    path: '',
    component: MenuTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MenuTypeDetailComponent,
    resolve: {
      menuType: MenuTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MenuTypeUpdateComponent,
    resolve: {
      menuType: MenuTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MenuTypeUpdateComponent,
    resolve: {
      menuType: MenuTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default menuTypeRoute;
