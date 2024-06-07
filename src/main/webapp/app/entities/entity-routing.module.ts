import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        data: { pageTitle: 'restaurantApp.category.home.title' },
        loadChildren: () => import('./category/category.routes'),
      },
      {
        path: 'product',
        data: { pageTitle: 'restaurantApp.product.home.title' },
        loadChildren: () => import('./product/product.routes'),
      },
      {
        path: 'dining-table',
        data: { pageTitle: 'restaurantApp.diningTable.home.title' },
        loadChildren: () => import('./dining-table/dining-table.routes'),
      },
      {
        path: 'reservation',
        data: { pageTitle: 'restaurantApp.reservation.home.title' },
        loadChildren: () => import('./reservation/reservation.routes'),
      },
      {
        path: 'menu-type',
        data: { pageTitle: 'restaurantApp.menuType.home.title' },
        loadChildren: () => import('./menu-type/menu-type.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
