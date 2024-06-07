import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiningTable, NewDiningTable } from '../dining-table.model';

export type PartialUpdateDiningTable = Partial<IDiningTable> & Pick<IDiningTable, 'id'>;

export type EntityResponseType = HttpResponse<IDiningTable>;
export type EntityArrayResponseType = HttpResponse<IDiningTable[]>;

@Injectable({ providedIn: 'root' })
export class DiningTableService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dining-tables');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(diningTable: NewDiningTable): Observable<EntityResponseType> {
    return this.http.post<IDiningTable>(this.resourceUrl, diningTable, { observe: 'response' });
  }

  update(diningTable: IDiningTable): Observable<EntityResponseType> {
    return this.http.put<IDiningTable>(`${this.resourceUrl}/${this.getDiningTableIdentifier(diningTable)}`, diningTable, {
      observe: 'response',
    });
  }

  partialUpdate(diningTable: PartialUpdateDiningTable): Observable<EntityResponseType> {
    return this.http.patch<IDiningTable>(`${this.resourceUrl}/${this.getDiningTableIdentifier(diningTable)}`, diningTable, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiningTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiningTable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDiningTableIdentifier(diningTable: Pick<IDiningTable, 'id'>): number {
    return diningTable.id;
  }

  compareDiningTable(o1: Pick<IDiningTable, 'id'> | null, o2: Pick<IDiningTable, 'id'> | null): boolean {
    return o1 && o2 ? this.getDiningTableIdentifier(o1) === this.getDiningTableIdentifier(o2) : o1 === o2;
  }

  addDiningTableToCollectionIfMissing<Type extends Pick<IDiningTable, 'id'>>(
    diningTableCollection: Type[],
    ...diningTablesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const diningTables: Type[] = diningTablesToCheck.filter(isPresent);
    if (diningTables.length > 0) {
      const diningTableCollectionIdentifiers = diningTableCollection.map(
        diningTableItem => this.getDiningTableIdentifier(diningTableItem)!,
      );
      const diningTablesToAdd = diningTables.filter(diningTableItem => {
        const diningTableIdentifier = this.getDiningTableIdentifier(diningTableItem);
        if (diningTableCollectionIdentifiers.includes(diningTableIdentifier)) {
          return false;
        }
        diningTableCollectionIdentifiers.push(diningTableIdentifier);
        return true;
      });
      return [...diningTablesToAdd, ...diningTableCollection];
    }
    return diningTableCollection;
  }
}
