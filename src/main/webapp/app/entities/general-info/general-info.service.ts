import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGeneralInfo } from 'app/shared/model/general-info.model';

type EntityResponseType = HttpResponse<IGeneralInfo>;
type EntityArrayResponseType = HttpResponse<IGeneralInfo[]>;

@Injectable({ providedIn: 'root' })
export class GeneralInfoService {
    private resourceUrl = SERVER_API_URL + 'api/general-infos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/general-infos';

    constructor(private http: HttpClient) {}

    create(generalInfo: IGeneralInfo): Observable<EntityResponseType> {
        return this.http.post<IGeneralInfo>(this.resourceUrl, generalInfo, { observe: 'response' });
    }

    update(generalInfo: IGeneralInfo): Observable<EntityResponseType> {
        return this.http.put<IGeneralInfo>(this.resourceUrl, generalInfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGeneralInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGeneralInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGeneralInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
