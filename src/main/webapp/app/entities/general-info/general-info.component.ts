import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGeneralInfo } from 'app/shared/model/general-info.model';
import { Principal } from 'app/core';
import { GeneralInfoService } from './general-info.service';

@Component({
    selector: 'jhi-general-info',
    templateUrl: './general-info.component.html'
})
export class GeneralInfoComponent implements OnInit, OnDestroy {
    generalInfos: IGeneralInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private generalInfoService: GeneralInfoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.generalInfoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IGeneralInfo[]>) => (this.generalInfos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.generalInfoService.query().subscribe(
            (res: HttpResponse<IGeneralInfo[]>) => {
                this.generalInfos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGeneralInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGeneralInfo) {
        return item.id;
    }

    registerChangeInGeneralInfos() {
        this.eventSubscriber = this.eventManager.subscribe('generalInfoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
