import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuthorityMenuItem } from '../authority-menu-item.model';
import { AuthorityMenuItemService } from '../service/authority-menu-item.service';

export const authorityMenuItemResolve = (route: ActivatedRouteSnapshot): Observable<null | IAuthorityMenuItem> => {
  const id = route.params['id'];
  if (id) {
    return inject(AuthorityMenuItemService)
      .find(id)
      .pipe(
        mergeMap((authorityMenuItem: HttpResponse<IAuthorityMenuItem>) => {
          if (authorityMenuItem.body) {
            return of(authorityMenuItem.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default authorityMenuItemResolve;
