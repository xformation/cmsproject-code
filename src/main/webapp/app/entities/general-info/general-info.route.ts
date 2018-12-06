import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GeneralInfo } from 'app/shared/model/general-info.model';
import { GeneralInfoService } from './general-info.service';
import { GeneralInfoComponent } from './general-info.component';
import { GeneralInfoDetailComponent } from './general-info-detail.component';
import { GeneralInfoUpdateComponent } from './general-info-update.component';
import { GeneralInfoDeletePopupComponent } from './general-info-delete-dialog.component';
import { IGeneralInfo } from 'app/shared/model/general-info.model';

@Injectable({ providedIn: 'root' })
export class GeneralInfoResolve implements Resolve<IGeneralInfo> {
    constructor(private service: GeneralInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((generalInfo: HttpResponse<GeneralInfo>) => generalInfo.body));
        }
        return of(new GeneralInfo());
    }
}

export const generalInfoRoute: Routes = [
    {
        path: 'general-info',
        component: GeneralInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneralInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'general-info/:id/view',
        component: GeneralInfoDetailComponent,
        resolve: {
            generalInfo: GeneralInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneralInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'general-info/new',
        component: GeneralInfoUpdateComponent,
        resolve: {
            generalInfo: GeneralInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneralInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'general-info/:id/edit',
        component: GeneralInfoUpdateComponent,
        resolve: {
            generalInfo: GeneralInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneralInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const generalInfoPopupRoute: Routes = [
    {
        path: 'general-info/:id/delete',
        component: GeneralInfoDeletePopupComponent,
        resolve: {
            generalInfo: GeneralInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneralInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
