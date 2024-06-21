import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuthorityMenuItem, NewAuthorityMenuItem } from '../authority-menu-item.model';

export type PartialUpdateAuthorityMenuItem = Partial<IAuthorityMenuItem> & Pick<IAuthorityMenuItem, 'id'>;

export type EntityResponseType = HttpResponse<IAuthorityMenuItem>;
export type EntityArrayResponseType = HttpResponse<IAuthorityMenuItem[]>;

@Injectable({ providedIn: 'root' })
export class AuthorityMenuItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/authority-menu-items');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(authorityMenuItem: NewAuthorityMenuItem): Observable<EntityResponseType> {
    return this.http.post<IAuthorityMenuItem>(this.resourceUrl, authorityMenuItem, { observe: 'response' });
  }

  update(authorityMenuItem: IAuthorityMenuItem): Observable<EntityResponseType> {
    return this.http.put<IAuthorityMenuItem>(
      `${this.resourceUrl}/${this.getAuthorityMenuItemIdentifier(authorityMenuItem)}`,
      authorityMenuItem,
      { observe: 'response' },
    );
  }

  partialUpdate(authorityMenuItem: PartialUpdateAuthorityMenuItem): Observable<EntityResponseType> {
    return this.http.patch<IAuthorityMenuItem>(
      `${this.resourceUrl}/${this.getAuthorityMenuItemIdentifier(authorityMenuItem)}`,
      authorityMenuItem,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuthorityMenuItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthorityMenuItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAuthorityMenuItemIdentifier(authorityMenuItem: Pick<IAuthorityMenuItem, 'id'>): number {
    return authorityMenuItem.id;
  }

  compareAuthorityMenuItem(o1: Pick<IAuthorityMenuItem, 'id'> | null, o2: Pick<IAuthorityMenuItem, 'id'> | null): boolean {
    return o1 && o2 ? this.getAuthorityMenuItemIdentifier(o1) === this.getAuthorityMenuItemIdentifier(o2) : o1 === o2;
  }

  addAuthorityMenuItemToCollectionIfMissing<Type extends Pick<IAuthorityMenuItem, 'id'>>(
    authorityMenuItemCollection: Type[],
    ...authorityMenuItemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const authorityMenuItems: Type[] = authorityMenuItemsToCheck.filter(isPresent);
    if (authorityMenuItems.length > 0) {
      const authorityMenuItemCollectionIdentifiers = authorityMenuItemCollection.map(
        authorityMenuItemItem => this.getAuthorityMenuItemIdentifier(authorityMenuItemItem)!,
      );
      const authorityMenuItemsToAdd = authorityMenuItems.filter(authorityMenuItemItem => {
        const authorityMenuItemIdentifier = this.getAuthorityMenuItemIdentifier(authorityMenuItemItem);
        if (authorityMenuItemCollectionIdentifiers.includes(authorityMenuItemIdentifier)) {
          return false;
        }
        authorityMenuItemCollectionIdentifiers.push(authorityMenuItemIdentifier);
        return true;
      });
      return [...authorityMenuItemsToAdd, ...authorityMenuItemCollection];
    }
    return authorityMenuItemCollection;
  }
}
