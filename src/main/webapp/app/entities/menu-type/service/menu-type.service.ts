import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMenuType, NewMenuType } from '../menu-type.model';

export type PartialUpdateMenuType = Partial<IMenuType> & Pick<IMenuType, 'id'>;

export type EntityResponseType = HttpResponse<IMenuType>;
export type EntityArrayResponseType = HttpResponse<IMenuType[]>;

@Injectable({ providedIn: 'root' })
export class MenuTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/menu-types');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(menuType: NewMenuType): Observable<EntityResponseType> {
    return this.http.post<IMenuType>(this.resourceUrl, menuType, { observe: 'response' });
  }

  update(menuType: IMenuType): Observable<EntityResponseType> {
    return this.http.put<IMenuType>(`${this.resourceUrl}/${this.getMenuTypeIdentifier(menuType)}`, menuType, { observe: 'response' });
  }

  partialUpdate(menuType: PartialUpdateMenuType): Observable<EntityResponseType> {
    return this.http.patch<IMenuType>(`${this.resourceUrl}/${this.getMenuTypeIdentifier(menuType)}`, menuType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMenuTypeIdentifier(menuType: Pick<IMenuType, 'id'>): number {
    return menuType.id;
  }

  compareMenuType(o1: Pick<IMenuType, 'id'> | null, o2: Pick<IMenuType, 'id'> | null): boolean {
    return o1 && o2 ? this.getMenuTypeIdentifier(o1) === this.getMenuTypeIdentifier(o2) : o1 === o2;
  }

  addMenuTypeToCollectionIfMissing<Type extends Pick<IMenuType, 'id'>>(
    menuTypeCollection: Type[],
    ...menuTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const menuTypes: Type[] = menuTypesToCheck.filter(isPresent);
    if (menuTypes.length > 0) {
      const menuTypeCollectionIdentifiers = menuTypeCollection.map(menuTypeItem => this.getMenuTypeIdentifier(menuTypeItem)!);
      const menuTypesToAdd = menuTypes.filter(menuTypeItem => {
        const menuTypeIdentifier = this.getMenuTypeIdentifier(menuTypeItem);
        if (menuTypeCollectionIdentifiers.includes(menuTypeIdentifier)) {
          return false;
        }
        menuTypeCollectionIdentifiers.push(menuTypeIdentifier);
        return true;
      });
      return [...menuTypesToAdd, ...menuTypeCollection];
    }
    return menuTypeCollection;
  }
}
