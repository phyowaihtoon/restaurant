import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IPickList } from '../model/picklist.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoadSetupService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/setup');
  constructor(
    protected http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
  ) {}

  loadPicklist(): Observable<HttpResponse<IPickList>> {
    const childURL = '/picklist';
    return this.http.get<IPickList>(this.resourceUrl + childURL, { observe: 'response' });
  }
}
