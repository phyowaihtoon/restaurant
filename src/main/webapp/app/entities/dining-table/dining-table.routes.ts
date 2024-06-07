import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DiningTableComponent } from './list/dining-table.component';
import { DiningTableDetailComponent } from './detail/dining-table-detail.component';
import { DiningTableUpdateComponent } from './update/dining-table-update.component';
import DiningTableResolve from './route/dining-table-routing-resolve.service';

const diningTableRoute: Routes = [
  {
    path: '',
    component: DiningTableComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiningTableDetailComponent,
    resolve: {
      diningTable: DiningTableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiningTableUpdateComponent,
    resolve: {
      diningTable: DiningTableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiningTableUpdateComponent,
    resolve: {
      diningTable: DiningTableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default diningTableRoute;
