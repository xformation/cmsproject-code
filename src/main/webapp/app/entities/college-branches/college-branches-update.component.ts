import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICollegeBranches } from 'app/shared/model/college-branches.model';
import { CollegeBranchesService } from './college-branches.service';

@Component({
    selector: 'jhi-college-branches-update',
    templateUrl: './college-branches-update.component.html'
})
export class CollegeBranchesUpdateComponent implements OnInit {
    collegeBranches: ICollegeBranches;
    isSaving: boolean;

    constructor(private collegeBranchesService: CollegeBranchesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ collegeBranches }) => {
            this.collegeBranches = collegeBranches;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.collegeBranches.id !== undefined) {
            this.subscribeToSaveResponse(this.collegeBranchesService.update(this.collegeBranches));
        } else {
            this.subscribeToSaveResponse(this.collegeBranchesService.create(this.collegeBranches));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICollegeBranches>>) {
        result.subscribe((res: HttpResponse<ICollegeBranches>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
