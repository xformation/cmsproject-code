import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGeneralInfo } from 'app/shared/model/general-info.model';
import { GeneralInfoService } from './general-info.service';

@Component({
    selector: 'jhi-general-info-update',
    templateUrl: './general-info-update.component.html'
})
export class GeneralInfoUpdateComponent implements OnInit {
    private _generalInfo: IGeneralInfo;
    isSaving: boolean;

    constructor(private generalInfoService: GeneralInfoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ generalInfo }) => {
            this.generalInfo = generalInfo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.generalInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.generalInfoService.update(this.generalInfo));
        } else {
            this.subscribeToSaveResponse(this.generalInfoService.create(this.generalInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGeneralInfo>>) {
        result.subscribe((res: HttpResponse<IGeneralInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get generalInfo() {
        return this._generalInfo;
    }

    set generalInfo(generalInfo: IGeneralInfo) {
        this._generalInfo = generalInfo;
    }
}
