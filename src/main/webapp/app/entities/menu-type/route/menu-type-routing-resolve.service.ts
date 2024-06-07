import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMenuType } from '../menu-type.model';
import { MenuTypeService } from '../service/menu-type.service';

export const menuTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IMenuType> => {
  const id = route.params['id'];
  if (id) {
    return inject(MenuTypeService)
      .find(id)
      .pipe(
        mergeMap((menuType: HttpResponse<IMenuType>) => {
          if (menuType.body) {
            return of(menuType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default menuTypeResolve;
